package com.investadvisor.pamm.alpari;

import com.investadvisor.Currency;
import com.investadvisor.model.pamm.*;
import com.investadvisor.pamm.alpari.model.AlpariPamm;
import com.investadvisor.pamm.alpari.model.PAMMAlpariGeneral;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads Pamms for alpari.ru
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
public class AlpariLoader extends PammLoader {
    private static final Logger logger = LoggerFactory.getLogger(AlpariLoader.class);

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //the way to make it singleton. When we ad Guice, we should update this code to remove instance
    private static AlpariLoader instance = new AlpariLoader();

    public static AlpariLoader getInstance() {
        return instance;
    }

    private AlpariLoader() {
    }

    /**
     * Loads all pamms for one broker
     *
     * @return list of pamms
     * @throws IOException
     */
    public List<Pamm> load() throws IOException {
        logger.info("Start download all Alpari pamm managers");
        List<Pamm> pamms = new ArrayList<>();

        //this url returns all pamms for alpari
        URL urlAll = new URL("http://www.alpari.ru/ru/investor/pamm/rating.json");

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
            pamm.setAgeInDays(Integer.valueOf(String.valueOf(onePammElements.get(27))));
            pamm.setManagerMoney(Double.valueOf(String.valueOf(onePammElements.get(12))));
            pamm.setTotalMoney(Double.valueOf(String.valueOf(onePammElements.get(10))));

            String currency = String.valueOf(onePammElements.get(15)).replace("RUR", "RUB");
            if (!Currency.isRelevant(currency)) {
                //alpari has their own currency GLD, maybe we will add it in future, not now
                return;
            }

            currencyPerPamm.put(pamm.getId(), Currency.valueOf(currency));
            namePerPamm.put(pamm.getId(), name);

            pamms.add(pamm);

        });

        logger.info("Finish download all Alpari pamm managers");
        LocalDate now = LocalDate.now();
        String nowFormatted = now.format(dateFormatter);
        //load data per each pamm
        for (Pamm pamm : pamms) {
            logger.info("Start overwork pamm {}", pamm);

            URL url = new URL("http://www.alpari.ru/ru/investor/pamm/" + pamm.getId() + "/monitoring/daily_all_candle.csv");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));

            Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(in);

            //only work days are returned
            List<Double> changes = new ArrayList<>();

            Double totalIncreaseInPercents = 0.;
            for (CSVRecord record : records) {
                String date = record.get(0);
                if (date.equals(nowFormatted)) {
                    break;

                }
                Double open = Double.valueOf(record.get(1));
                Double close = Double.valueOf(record.get(4));
                Double depositLoad = Double.valueOf(record.get(6));
                if (depositLoad == 0.) {
                    //no operations, not need to calculate
                    continue;
                }

                //if open == -100, it means, that account was closed, but let's calculate next way
                Double change = open == -100 ? -100 : (close - open) * 100 / (open + 100);

                changes.add(change);
                totalIncreaseInPercents = close;
            }
            Double avgChange = addChangesToPamm(changes, totalIncreaseInPercents, pamm);

            //let's load commissions
            Document doc = Jsoup.connect("http://www.alpari.ru/ru/investor/pamm/" + pamm.getId()).timeout(30000).get();
            Elements linesWithCommission = doc.select(".pamm-info_offer table tr");
            Elements minBalances = linesWithCommission.get(0).children();
            Elements commissions = linesWithCommission.get(1).children();
            char[] spaceChar = {160};
            String spaceCharStr = new String(spaceChar);
            for (int i = 1; i < minBalances.size(); i++) {
                Double minBalance = Double.valueOf(minBalances.get(i).text().replace(spaceCharStr, "").trim());
                Double commission = Double.valueOf(commissions.get(i).text().replace("%", "").trim());
                InvestmentTargetOffer investmentTargetOffer = new InvestmentTargetOffer(namePerPamm.get(pamm.getId()), minBalance, null, 1., null, commission, "http://www.alpari.ru/ru/investor/pamm/" + pamm.getId() + "/?partner_id=1231285", currencyPerPamm.get(pamm.getId()), avgChange, new PammOfferRisk(), new PammOfferProfit());
                pamm.addOffer(investmentTargetOffer);
            }

            logger.info("Finish overwork pamm {}", pamm);
        }
        logger.info("Finish download all Alpari pamm managers");
        filterUselessPamms(pamms);
        return pamms;
    }
}
