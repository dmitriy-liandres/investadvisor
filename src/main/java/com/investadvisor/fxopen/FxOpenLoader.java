package com.investadvisor.fxopen;

import com.fasterxml.jackson.core.type.TypeReference;
import com.investadvisor.Pamm;
import com.investadvisor.PammBroker;
import com.investadvisor.PammLoader;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

/**
 * Loads Pamms for fxopen.ru
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class FxOpenLoader extends PammLoader {
    private static final Logger logger = LoggerFactory.getLogger(FxOpenLoader.class);

    private static FxOpenLoader instance = new FxOpenLoader();

    Pattern DATA_PATTERN = Pattern.compile("(\"Data\":.*?\\])", Pattern.DOTALL);

    //the way to make it singleton. When we ad Guice, we should update this code to remove instance
    public static FxOpenLoader getInstance() {
        return instance;
    }

    private FxOpenLoader() {
    }

    public List<Pamm> load() throws IOException {
        logger.info("Download all fxopen managers");

        List<Pamm> pamms = new ArrayList<>();
        //sometimes we get not full page, so we have to wait in this case and load again (up to 5 times
        int maxTries = 5;
        int tryNumber = 0;
        String dataJson = null;
        while (tryNumber < maxTries) {
            try {
                URL urlBase = new URL("https://pamm.fxopen.ru/ru/PAMMAccountsRating");
                HttpURLConnection conBase = (HttpURLConnection) urlBase.openConnection();
                String responseBase = IOUtils.toString(conBase.getInputStream(), "UTF-8");
                dataJson = "{" + getMatchValue(DATA_PATTERN, responseBase) + "}";
                break;
            } catch (Exception e) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ie) {
                    logger.error("Impossible to run timer", e);
                }
                tryNumber++;
            }
        }

        FXOpenManagerGeneralList allValues = objectMapper.readValue(dataJson, FXOpenManagerGeneralList.class);

        //let's load maangers one by one
        for (FXOpenManagerGeneral manager : allValues.getData()) {
            logger.info("Start load FxOpen manager details, pammId = {}", manager.getId());
            Pamm pamm = new Pamm();
            pamm.setPammBroker(PammBroker.FXOPEN);
            pamm.setAgeInDays(manager.getDays());
            pamm.setManagerMoney(manager.getMasterCapital());
            pamm.setTotalMoney(manager.getBalance());
            pamm.setName(manager.getName());
            pamm.setId(manager.getId());
            pamm.setCurrency(manager.getCurrency());

            //load changes
            URL urlManager = new URL("https://datastore5.soft-fx.com/ru/Chart/GainMini?id=" + manager.getId());
            HttpURLConnection conBase = (HttpURLConnection) urlManager.openConnection();
            conBase.setRequestMethod("GET");
            conBase.setRequestProperty("Origin", "https://pamm.fxopen.ru");
            conBase.setRequestProperty("Referer", "https://pamm.fxopen.ru/ru/PAMMAccountsRating");
            conBase.setRequestProperty("Content-Type", "application/json");

            InputStreamReader reader;
            reader = new InputStreamReader(new GZIPInputStream(conBase.getInputStream()));
            List<PammFxOpenManagerMoney> dailyGains = objectMapper.readValue(reader, new TypeReference<List<PammFxOpenManagerMoney>>() {
            });
            if (dailyGains.size() > 0) {
                List<Double> changes = dailyGains.stream().map(PammFxOpenManagerMoney::getDailyGain).collect(Collectors.toList());
                //all days are returned
                addChangesToPamm(changes, pamm);
                pamms.add(pamm);
            }
            logger.info("Finish load FxOpen manager details, pammId = {}", manager.getId());
        }

        logger.info("Downloaded all fxopen managers");
        filterUselessPamms(pamms);
        return pamms;
    }
}
