package com.toolformoney.investarget.pamm.fxopen;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.investarget.pamm.fxopen.model.*;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.forex.ForexLoader;
import com.toolformoney.model.pamm.Pamm;
import com.toolformoney.model.pamm.PammBroker;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Loads Pamms for fxopen.ru
 * https://pamm.fxopen.com/ru/HowItWorks/Slave/BecomeSlave
 * https://pamm.fxopen.com/HowItWorks/General#OfferParameters
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
@Singleton
public class FxOpenLoader extends ForexLoader {
    private static final Logger logger = LoggerFactory.getLogger(FxOpenLoader.class);

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public List<Pamm> load() throws IOException {
        logger.info("Download all fxopen managers");

        List<Pamm> pamms = new ArrayList<>();

        //load all pamms
        URL urlBase = new URL("https://datastore5.soft-fx.com/Pamm/GetRating?BrokerId=D5CBE825-CC28-4DF8-A52A-12F20165C794&Count=500&WithOffers=true");

        List<FXOpenManagerGeneral> allValues = objectMapper.readValue(urlBase, new TypeReference<List<FXOpenManagerGeneral>>() {
        });

        //let's load managers one by one
        for (FXOpenManagerGeneral manager : allValues) {
            try {
                logger.info("Start load FxOpen manager details, pammId = {}", manager.getId());
                if(!Currency.isRelevant(manager.getCurrency())){
                    logger.info("Currency {} is not relevant", manager.getCurrency());
                    continue;
                }
                Pamm pamm = new FxOpenPamm();
                pamm.setPammBroker(PammBroker.FXOPEN);
                pamm.setAgeInDays(manager.getDays());
                pamm.setManagerMoney(manager.getMastercapital());
                pamm.setTotalMoney(manager.getBalance());
                pamm.setId(manager.getId());

                Double avgChange = null;

                //load commissions
                //1. get required cookie

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


                //2. load changes
                LocalDate now = LocalDate.now();
                String nowFormatted = now.format(dateFormatter);

                LocalDate theFirstWorkingDate = now.minusDays(pamm.getAgeInDays());
                String theFirstWorkingDateStr = theFirstWorkingDate.format(dateFormatter);

                URL urlLoadGain = new URL("https://datastore5.soft-fx.com/Chart/Gain?BrokerId=D5CBE825-CC28-4DF8-A52A-12F20165C794&id=" + manager.getId() + "&from=" + theFirstWorkingDateStr + "&to=" + nowFormatted);
                HttpURLConnection urlLoadGainConnection = (HttpURLConnection) urlLoadGain.openConnection();
                urlLoadGainConnection.setRequestProperty("Accept-Encoding", "gzip");

                InputStreamReader readerLoadGain = new InputStreamReader(new GZIPInputStream(urlLoadGainConnection.getInputStream()));
                List<PammFxOpenManagerMoney> dailyGains = objectMapper.readValue(readerLoadGain, new TypeReference<List<PammFxOpenManagerMoney>>() {
                });
                if (dailyGains.size() > 0) {
                    //get deposit load
                    URL urlDepositLoad = new URL("https://datastore5.soft-fx.com/Chart/AccountDepositLoad?BrokerId=D5CBE825-CC28-4DF8-A52A-12F20165C794&id=" + manager.getId() + "&from=" + theFirstWorkingDateStr + "&to=" + nowFormatted);
                    InputStreamReader readerDepositLoad = new InputStreamReader(urlDepositLoad.openStream());
                    List<FXOpenDepositLoad> allDepositLoads = objectMapper.readValue(readerDepositLoad, new TypeReference<List<FXOpenDepositLoad>>() {
                    });


                    Map<String, Double> depositLoadPerDate = new HashMap<>();
                    allDepositLoads.forEach(depositLoad -> depositLoadPerDate.put(depositLoad.getDate(), depositLoad.getValue()));
                    List<DailyChange> changes = new ArrayList<>();

                    for (PammFxOpenManagerMoney dailyGain : dailyGains) {
                        if (depositLoadPerDate.get(dailyGain.getDate()) > 0.) {

                            LocalDate localDate = Instant.ofEpochMilli(Double.valueOf(dailyGain.getDate()).longValue()).atZone(ZoneId.systemDefault()).toLocalDate();

                            changes.add(new DailyChange(localDate, dailyGain.getDailyGain()));
                        }
                    }
                    //all days are returned
                    avgChange = addChangesToForexTrades(changes, pamm);
                }


                //3. get offers
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

                for (FxOpenOffersResultData fxOpenOffersResultData : fxOpenOffersResult.getData()) {
                    if (fxOpenOffersResultData.getOfferTypeStr().equals("Active")) {

                        Double minPeriodInDays = null;
                        switch (fxOpenOffersResultData.getInterval()) {
                            case 3:
                                minPeriodInDays = 122.;
                                break;
                            case 2:
                                minPeriodInDays = 30.;
                                break;
                            case 1:
                                minPeriodInDays = 7.;
                                break;
                            default:
                                throw new RuntimeException("Incorrect interval id = " + fxOpenOffersResultData.getInterval());
                        }



                        PammOfferFxOpen pammOfferFxOpen = new PammOfferFxOpen(manager.getName(), fxOpenOffersResultData.getInitialDeposit(), minPeriodInDays, fxOpenOffersResultData.getPerformanceFee(), fxOpenOffersResultData.getManagementFee(),
                                fxOpenOffersResultData.getMinimumPerformanceConstraint(), fxOpenOffersResultData.getDepositCommision(),
                                Currency.valueOf(manager.getCurrency()), avgChange, "https://pamm.fxopen.ru/ru/Pamm/" + manager.getName() + "?agent=691142");
                        pamm.addOffer(pammOfferFxOpen);

                    }
                }
                if(CollectionUtils.isNotEmpty(pamm.getInvestmentTargetOffers())){
                    pamms.add(pamm);
                }

                if (CollectionUtils.isEmpty(pamm.getInvestmentTargetOffers())) {
                    //no active offers
                    continue;
                }


                logger.info("Finish load FxOpen manager details, pammId = {}", manager.getId());
            } catch (Exception e) {
                logger.error("Impossible to load FXOpen manager = {}", manager, e);
            }
        }

        logger.info("Downloaded all fxopen managers");
        filterUselessForexAccounts(pamms);
        return pamms;
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.FX_OPEN;
    }
}
