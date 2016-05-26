package com.investadvisor.alpari;

import com.investadvisor.Currency;
import com.investadvisor.Pamm;
import com.investadvisor.PammBroker;
import com.investadvisor.PammLoader;
import com.investadvisor.alpari.model.PAMMAlpariGeneral;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        logger.info("Download all Alpari pamm managers");
        List<Pamm> pamms = new ArrayList<>();

        //this url returns all pamms for alpari
        URL urlAll = new URL("http://www.alpari.ru/ru/investor/pamm/rating.json");

        //maps returned json to the object
        PAMMAlpariGeneral allValue = objectMapper.readValue(urlAll.openStream(), PAMMAlpariGeneral.class);

        allValue.getElements().forEach(onePammElements -> {
            Pamm pamm = new Pamm();
            pamm.setPammBroker(PammBroker.ALPARI);
            pamm.setId(String.valueOf(onePammElements.get(0)));
            pamm.setName(String.valueOf(onePammElements.get(1)));
            pamm.setAgeInDays(Integer.valueOf(String.valueOf(onePammElements.get(27))));
            pamm.setManagerMoney(Double.valueOf(String.valueOf(onePammElements.get(12))));
            pamm.setTotalMoney(Double.valueOf(String.valueOf(onePammElements.get(10))));
            pamm.setCurrency(Currency.valueOf(String.valueOf(onePammElements.get(15)).replace("RUR", "RUB").replace("GLD", "USD")));
            pamms.add(pamm);

        });

        logger.info("Finish download all Alpari pamm managers");
        LocalDate now = LocalDate.now();
        String nowFormatted = now.format(dateFormatter);
        //load data per each pamm
        pamms.forEach(pamm -> {
            logger.info("Start overwork pamm {}", pamm);

            URL url = null;
            Iterable<CSVRecord> records;
            try {
                url = new URL("http://www.alpari.ru/ru/investor/pamm/" + pamm.getId() + "/monitoring/daily_all_candle.csv");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));

                records = CSVFormat.EXCEL.withDelimiter(';').parse(in);
            } catch (IOException e) {
                logger.error("Impossible to load pamm data for {}", pamm);
                return;
            }

            //only work days are returned
            List<Double> changes = new ArrayList<>();

            for (CSVRecord record : records) {
                String date = record.get(0);
                if (date.equals(nowFormatted)) {
                    break;

                }
                Double open = Double.valueOf(record.get(1));
                Double close = Double.valueOf(record.get(4));

                //if open == -100, it means, that account was closed, but let's calculate next way
                Double change = open == -100 ? -100 : (close - open) * 100 / (open + 100);

                changes.add(change);

            }

            addChangesToPamm(changes, pamm);
            logger.info("Finish overwork pamm {}", pamm);
        });
        filterUselessPamms(pamms);
        return pamms;
    }
}
