package com.toolformoney.investarget.pamm.alpari;

import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.investarget.pamm.alpari.model.AlpariPamm;
import com.toolformoney.investarget.pamm.alpari.model.PAMMAlpariGeneral;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.forex.ForexLoader;
import com.toolformoney.model.forex.ForexOfferProfit;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.pamm.Pamm;
import com.toolformoney.model.pamm.PammBroker;
import com.toolformoney.model.pamm.PammOfferRisk;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        List<Pamm> pamms = new ArrayList<>();

        //this url returns all pamms for alpari
        URL urlAll = new URL("https://www.alpari.com/ru/investor/pamm/rating.json");

        //maps returned json to the object
        PAMMAlpariGeneral allValue = objectMapper.readValue(urlAll.openStream(), PAMMAlpariGeneral.class);

        //pamm id->curreny
        Map<String, Currency> currencyPerPamm = new HashMap<>();
        //pamm id->name
        Map<String, String> namePerPamm = new HashMap<>();
        allValue.getElements().forEach(onePammElements -> {
            Pamm pamm = new AlpariPamm();
            pamm.setPammBroker(PammBroker.ALPARI);
            pamm.setId(String.valueOf(onePammElements.get(0)));
            String name = String.valueOf(onePammElements.get(1));
            pamm.setAgeInDays(Integer.valueOf(String.valueOf(onePammElements.get(37))));
            pamm.setManagerMoney(Double.valueOf(String.valueOf(onePammElements.get(12))));
            pamm.setTotalMoney(Double.valueOf(String.valueOf(onePammElements.get(10))));

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
        for (Pamm pamm : pamms) {
            try {
                logger.info("Start overwork pamm {}", pamm);

                URL url = new URL("https://www.alpari.com/ru/investor/pamm/" + pamm.getId() + "/monitoring/daily_all_candle.csv");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));

                Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(in);

                //only work days are returned
                List<DailyChange> changes = new ArrayList<>();

                for (CSVRecord record : records) {
                    String date = record.get(0);
                    LocalDate localDate = LocalDate.parse(date, dateFormatter);

                    Double open = Double.valueOf(record.get(1));
                    Double close = Double.valueOf(record.get(4));
                    Double depositLoad = Double.valueOf(record.get(6));
                    if (depositLoad == 0.) {
                        //no operations, not need to calculate
                        continue;
                    }

                    //if open == -100, it means, that account was closed, but let's calculate next way
                    Double change = open == -100 ? -100 : (close - open) * 100 / (open + 100);

                    changes.add(new DailyChange(localDate, change));
                }
                Double avgChange = addChangesToForexTrades(changes, pamm);

                //let's load commissions
                Document doc = Jsoup.connect("https://www.alpari.com/ru/investor/pamm/" + pamm.getId()).timeout(30000).get();
                Elements linesWithCommission = doc.select(".pamm-info_offer table tr");
                if (linesWithCommission.size() == 0) {
                    //pamm is not available
                    continue;
                }
                Elements minBalances = linesWithCommission.get(0).children();
                Elements commissions = linesWithCommission.get(1).children();
                char[] spaceChar = {160};
                String spaceCharStr = new String(spaceChar);

                List<Map.Entry<Double, Double>> minBalanceCommissionPairs = new ArrayList<>();

                for (int i = 1; i < minBalances.size(); i++) {
                    Double minBalance = Double.valueOf(minBalances.get(i).text().replace(spaceCharStr, "").trim());
                    Double commission = Double.valueOf(commissions.get(i).text().replace("%", "").trim());
                    minBalanceCommissionPairs.add(new AbstractMap.SimpleEntry<>(minBalance, commission));
                }
                for (int i = 0; i < minBalanceCommissionPairs.size(); i++) {
                    Double minBalance = minBalanceCommissionPairs.get(i).getKey();
                    Double commission = minBalanceCommissionPairs.get(i).getValue();
                    Double maxBalance = i == minBalanceCommissionPairs.size() - 1 ? null : minBalanceCommissionPairs.get(i + 1).getKey();
                    InvestmentTargetOffer investmentTargetOffer = new InvestmentTargetOffer(namePerPamm.get(pamm.getId()), minBalance, maxBalance, 1., null, commission, "https://www.alpari.com/ru/investor/pamm/" + pamm.getId() + "/?partner_id=1231285", currencyPerPamm.get(pamm.getId()), avgChange, new PammOfferRisk(), new ForexOfferProfit());
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

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.ALPARI;
    }
}
