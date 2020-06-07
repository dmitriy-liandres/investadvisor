package com.toolformoney.investarget.pamm.alpari;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.investarget.pamm.alpari.model.AlpariPamm;
import com.toolformoney.investarget.pamm.alpari.model.PAMMAlpariGeneral;
import com.toolformoney.investarget.pamm.alpari.model.PammDaily;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.forex.ForexLoader;
import com.toolformoney.model.forex.ForexOfferProfit;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.pamm.Pamm;
import com.toolformoney.model.pamm.PammBroker;
import com.toolformoney.model.pamm.PammOfferRisk;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Loads Pamms for alpari.ru
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
@Singleton
public class AlpariLoader extends ForexLoader {
    private static final Logger logger = LoggerFactory.getLogger(AlpariLoader.class);

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Loads all pamms for one broker
     *
     * @return list of pamms
     * @throws IOException
     */
    public List<Pamm> load() throws IOException {


        logger.info("Start download all Alpari pamm managers");
        List<Pamm> filledPamms = new ArrayList<>();
        List<AlpariPamm> pamms = new ArrayList<>();

        //this url returns all pamms for alpari
        String ratings = sendRequest("https://alpari.com/ru/invest/pamm/rating.json", "https://alpari.com");
        //maps returned json to the object
        PAMMAlpariGeneral allValue = objectMapper.readValue(ratings, PAMMAlpariGeneral.class);

        //pamm id->curreny
        Map<String, Currency> currencyPerPamm = new HashMap<>();
        //pamm id->name
        Map<String, String> namePerPamm = new HashMap<>();
        allValue.getElements().forEach(onePammElements -> {
            AlpariPamm pamm = new AlpariPamm();
            pamm.setPammBroker(PammBroker.ALPARI);
            pamm.setId(String.valueOf(onePammElements.get(0)));
            String name = String.valueOf(onePammElements.get(1));
            pamm.setAgeInDays(Integer.valueOf(String.valueOf(onePammElements.get(38))));
            pamm.setManagerMoney(Double.valueOf(String.valueOf(onePammElements.get(17))));
            pamm.setTotalMoney(Double.valueOf(String.valueOf(onePammElements.get(9))));
            pamm.setOffersId(String.valueOf(((ArrayList) (onePammElements.get(23))).get(2)));

            String currency = String.valueOf(onePammElements.get(16)).replace("RUR", "RUB");
            if (!Currency.isRelevant(currency)) {
                //alpari has their own currency GLD, maybe we will add it in future, not now
                return;
            }

            currencyPerPamm.put(pamm.getId(), Currency.valueOf(currency));
            namePerPamm.put(pamm.getId(), name);

            pamms.add(pamm);

        });

        logger.info("Finish download all Alpari pamm managers");
        //load data per each pamm
        for (AlpariPamm pamm : pamms) {
            try {
                logger.info("Start overwork pamm {}", pamm);


                String pammData = sendRequest("https://alpari.com/chart/pamm/" + pamm.getId() + "/return/daily.json", "https://alpari.com/ru/invest/pamm/" + pamm.getId() + "/");
                PammDaily pammDaily = objectMapper.readValue(pammData, PammDaily.class);


                //only work days are returned
                List<DailyChange> changes = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(pammDaily.getData())) {
                    double previous = 0;

                    for (List<String> pammDate : pammDaily.getData()) {

                        String date = pammDate.get(0);
                        LocalDate localDate = LocalDate.parse(date, dateFormatter);

                        Double close = Double.valueOf(pammDate.get(1));
                        if (close == previous) {
                            //no operations, not need to calculate
                            continue;
                        }

                        //if open == -100, it means, that account was closed, but let's calculate next way
                        Double change = close == -100 ? -100 : (close - previous) * 100 / (previous + 100);

                        changes.add(new DailyChange(localDate, change));
                        previous = close;
                    }
                }
                Double avgChange = addChangesToForexTrades(changes, pamm);

                //let's load commissions
                List<Map.Entry<Double, Double>> minBalanceCommissionPairs = new ArrayList<>();

                String pammCommissionData = sendRequest("https://alpari.com/api/ru/pamm/other/" + pamm.getOffersId() + "/active/", "https://alpari.com/ru/invest/pamm/" + pamm.getId() + "/");
                JsonNode activePammsJson = objectMapper.readTree(pammCommissionData);
                JsonNode activeElements = activePammsJson.path("data").path("return").path("elements");
                if (!activeElements.isMissingNode()) {
                    activeElements.forEach(activeElement -> {
                        String id = activeElement.get("id").asText();
                        if (!pamm.getId().equals(id)) {
                            return;
                        }
                        JsonNode publicOfferJson = activeElement.path("publicOffer");
                        if (publicOfferJson == null || publicOfferJson.isMissingNode()) {
                            logger.info("no offers");
                        } else {
                            JsonNode levelsJson = publicOfferJson.path("levels");
                            if (levelsJson == null || levelsJson.isMissingNode()) {
                                logger.info("no levels");
                            } else {
                                levelsJson.forEach(levelJson -> {
                                    minBalanceCommissionPairs.add(new AbstractMap.SimpleEntry<>(levelJson.get("balance").asDouble(), levelJson.get("fee").asDouble()));
                                });
                            }
                        }

                    });
                }
                if (minBalanceCommissionPairs.size() == 0) {
                    continue;
                }
                for (int i = 0; i < minBalanceCommissionPairs.size(); i++) {
                    Double minBalance = minBalanceCommissionPairs.get(i).getKey();
                    Double commission = minBalanceCommissionPairs.get(i).getValue();
                    Double maxBalance = i == minBalanceCommissionPairs.size() - 1 ? null : minBalanceCommissionPairs.get(i + 1).getKey();
                    InvestmentTargetOffer investmentTargetOffer = new InvestmentTargetOffer(namePerPamm.get(pamm.getId()), minBalance, maxBalance, 1., null, commission, "https://www.alpari.com/ru/investor/pamm/" + pamm.getId() + "/?agent=18167", currencyPerPamm.get(pamm.getId()), avgChange, new PammOfferRisk(), new ForexOfferProfit());
                    pamm.addOffer(investmentTargetOffer);
                }


                logger.info("Finish overwork pamm {}", pamm);
                filledPamms.add(pamm);
            } catch (Exception e) {
                logger.error("Impossible to overwork pamm = {}", pamm, e);
            }
        }
        logger.info("Finish download all Alpari pamm managers");
        filterUselessForexAccounts(filledPamms);
        return filledPamms;
    }

    private String sendRequest(String url, String referer) throws IOException {
        URL urlManagerMoney = new URL(url);
        HttpURLConnection conManagerMoney = (HttpURLConnection) urlManagerMoney.openConnection();

        conManagerMoney.setRequestProperty("authority", "alpari.com");
        conManagerMoney.setRequestProperty("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Mobile Safari/537.36");
        conManagerMoney.setRequestProperty("referer", referer);


        //add request header
        conManagerMoney.setReadTimeout(30000);
        conManagerMoney.setConnectTimeout(30000);
        String response = null;
        try (InputStream io = conManagerMoney.getInputStream()) {
            response = IOUtils.toString(io, "UTF-8");
        }
        return response;
    }


    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.ALPARI;
    }
}
