package com.investadvisor.pamm.fxopen;

import com.fasterxml.jackson.core.type.TypeReference;
import com.investadvisor.model.Pamm;
import com.investadvisor.model.PammBroker;
import com.investadvisor.model.PammLoader;
import com.investadvisor.pamm.fxopen.model.*;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Loads Pamms for fxopen.ru
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class FxOpenLoader extends PammLoader {
    private static final Logger logger = LoggerFactory.getLogger(FxOpenLoader.class);

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static FxOpenLoader instance = new FxOpenLoader();

    //the way to make it singleton. When we add Guice, we should update this code to remove instance
    public static FxOpenLoader getInstance() {
        return instance;
    }

    private FxOpenLoader() {
    }

    public List<Pamm> load() throws IOException {
        logger.info("Download all fxopen managers");

        List<Pamm> pamms = new ArrayList<>();

        URL urlBase = new URL("https://datastore5.soft-fx.com/Pamm/GetRating?BrokerId=D5CBE825-CC28-4DF8-A52A-12F20165C794&Count=500&WithOffers=false");

        List<FXOpenManagerGeneral> allValues = objectMapper.readValue(urlBase, new TypeReference<List<FXOpenManagerGeneral>>() {
        });

        //let's load managers one by one
        for (FXOpenManagerGeneral manager : allValues) {
            logger.info("Start load FxOpen manager details, pammId = {}", manager.getId());
            Pamm pamm = new Pamm();
            pamm.setPammBroker(PammBroker.FXOPEN);
            pamm.setAgeInDays(manager.getDays());
            pamm.setManagerMoney(manager.getMasterCapital());
            pamm.setTotalMoney(manager.getBalance());
            pamm.setName(manager.getName());
            pamm.setId(manager.getId());
            pamm.setCurrency(manager.getCurrency());

            //load commissions
            //1. get reqiered cookie

            URL urlGetCookie = new URL("https://pamm.fxopen.ru/login/authenticate?email=fsDeJgBRPkFI0xfA541l3wFHQ9DI1zQFSrZzCOb2cdXiZwU9mjRwgOQUuJXbxmQeRTTrz7Nby43HKXgWtFCjSOjeqVDynu0lsgmWIiBjKk3ikr+/SvzaDKCL9G0uItNwg2F2iObwPh2+CgU7aJ/TFhC6qU/OOP/A20qnf8sHBuc=");
            HttpURLConnection conGetCookie = (HttpURLConnection) urlGetCookie.openConnection();
            List<String> cookies = conGetCookie.getHeaderFields().get("Set-Cookie");
            String aspAuthCookie = null;
            for (String cookie : cookies) {
                if (cookie.startsWith(".ASPXAUTH=")) {
                    aspAuthCookie = cookie;
                    break;
                }
            }
            //2. get offers
            URL urlGetOffers = new URL("https://pamm.fxopen.ru/ru/Slave/Account/Offers_Read/" + manager.getId() + "?accountId=00000000-0000-0000-0000-000000000000");
            HttpURLConnection conGetOffer = (HttpURLConnection) urlGetOffers.openConnection();
            conGetOffer.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            conGetOffer.setRequestProperty("cookie", aspAuthCookie);
            conGetOffer.setReadTimeout(30000);
            conGetOffer.setConnectTimeout(30000);
            conGetOffer.setRequestMethod("POST");
            conGetOffer.setDoInput(true);
            conGetOffer.setDoOutput(true);


            DataOutputStream wr = new DataOutputStream(conGetOffer.getOutputStream());
            wr.writeBytes("sort=&page=1&pageSize=1000&group=&filter=");
            wr.flush();
            wr.close();

            FxOpenOffersResult fxOpenOffersResult = objectMapper.readValue(conGetOffer.getInputStream(), FxOpenOffersResult.class);
            if (CollectionUtils.isEmpty(fxOpenOffersResult.getData())) {
                //no offers
                continue;
            }


            Double minInvestment = null;
            for (FxOpenOffersResultData fxOpenOffersResultData : fxOpenOffersResult.getData()) {
                if (fxOpenOffersResultData.getOfferTypeStr().equals("Active")) {
                    if (minInvestment == null || minInvestment > fxOpenOffersResultData.getInitialDeposit()) {
                        minInvestment = fxOpenOffersResultData.getInitialDeposit();
                    }
                    Integer minPeriodInDays = null;
                    switch (fxOpenOffersResultData.getInterval()) {
                        case 3:
                            minPeriodInDays = 122;
                            break;
                        case 2:
                            minPeriodInDays = 30;
                            break;
                        case 1:
                            minPeriodInDays = 7;
                            break;
                        default:
                            throw new RuntimeException("Incorrect interval id = " + fxOpenOffersResultData.getInterval());
                    }


                    PammCommissionFxOpen pammCommissionFxOpen = new PammCommissionFxOpen(fxOpenOffersResultData.getInitialDeposit(), minPeriodInDays, fxOpenOffersResultData.getPerformanceFee(), fxOpenOffersResultData.getManagementFee(), fxOpenOffersResultData.getMinimumPerformanceConstraint(), fxOpenOffersResultData.getDepositCommision());
                    pamm.addCommission(pammCommissionFxOpen);
                }
            }
            if (CollectionUtils.isEmpty(pamm.getCommissions())) {
                //no active offers
                continue;
            }


            //load changes
            LocalDate now = LocalDate.now();
            String nowFormatted = now.format(dateFormatter);

            LocalDate theFirstWorkingDate = now.minusDays(pamm.getAgeInDays());
            String theFirstWorkingDateStr = theFirstWorkingDate.format(dateFormatter);

            URL urlLoadGain = new URL("https://datastore5.soft-fx.com/Chart/Gain?BrokerId=D5CBE825-CC28-4DF8-A52A-12F20165C794&id=" + manager.getId() + "&from=" + theFirstWorkingDateStr + "&to=" + nowFormatted);
            InputStreamReader readerLoadGain = new InputStreamReader(new GZIPInputStream(urlLoadGain.openStream()));
            List<PammFxOpenManagerMoney> dailyGains = objectMapper.readValue(readerLoadGain, new TypeReference<List<PammFxOpenManagerMoney>>() {
            });

            if (dailyGains.size() > 0) {
                //get deposit load
                URL urlDepositLoad = new URL("https://datastore5.soft-fx.com/Chart/AccountDepositLoad?BrokerId=D5CBE825-CC28-4DF8-A52A-12F20165C794&id=" + manager.getId() + "&from=" + theFirstWorkingDateStr + "&to=" + nowFormatted);
                InputStreamReader readerDepositLoad = new InputStreamReader(new GZIPInputStream(urlDepositLoad.openStream()));
                List<FXOpenDepositLoad> allDepositLoads = objectMapper.readValue(readerDepositLoad, new TypeReference<List<FXOpenDepositLoad>>() {
                });


                Map<String, Double> depositLoadPerDate = new HashMap<>();
                allDepositLoads.forEach(depositLoad -> depositLoadPerDate.put(depositLoad.getDate(), depositLoad.getValue()));
                List<Double> changes = new ArrayList<>();
                dailyGains.forEach(dailyGain -> {
                    if (depositLoadPerDate.get(dailyGain.getDate()) > 0.) {
                        changes.add(dailyGain.getDailyGain());
                    }
                });
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
