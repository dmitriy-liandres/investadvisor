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
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    // VIP1 (25/0/0 - 0/10 - 50000/1000/50000 - 5)
    //https://pamm.fxopen.com/en/HowItWorks/General/Overview
    Pattern offerPattern = Pattern.compile(".* \\((\\d+)/(\\d+)/(\\d+) - (\\d+)/(\\d+) - (\\d+)/(\\d+)/(\\d+) - (\\d+)\\)");
    //<br/> Trading Interval: Month</td>
    Pattern intervalPattern = Pattern.compile(".*<br/> Trading Interval: (.*)</td>");

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
                if (!Currency.isRelevant(manager.getCurrency())) {
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
                conGetCookie.disconnect();

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
                readerLoadGain.close();
                if (dailyGains.size() > 0) {
                    //get deposit load
                    URL urlDepositLoad = new URL("https://datastore5.soft-fx.com/Chart/AccountDepositLoad?BrokerId=D5CBE825-CC28-4DF8-A52A-12F20165C794&id=" + manager.getId() + "&from=" + theFirstWorkingDateStr + "&to=" + nowFormatted);
                    InputStreamReader readerDepositLoad = new InputStreamReader(urlDepositLoad.openStream());
                    List<FXOpenDepositLoad> allDepositLoads = objectMapper.readValue(readerDepositLoad, new TypeReference<List<FXOpenDepositLoad>>() {
                    });
                    readerDepositLoad.close();


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


                //
                Document doc = Jsoup.connect("https://pamm.fxopen.com/en/Pamm/" + manager.getName())

                        .userAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Mobile Safari/537.36")
                        .timeout(20000)
                        .get();

                Elements offers = doc.getElementsByAttributeValue("name", "offerCheckbox");
                if (offers == null || offers.size() == 0) {
                    //no offers
                    continue;
                }


                for (int i = 0; i < offers.size(); i++) {
                    String text = offers.get(i).parent().text();
                    Matcher offerMatcher = offerPattern.matcher(text);
                    if (offerMatcher.find()) {
                        Double performanceFee = Double.valueOf(offerMatcher.group(1));
                        Double masterComission = Double.valueOf(offerMatcher.group(2));
                        Double minimumPerformanceConstraint = Double.valueOf(offerMatcher.group(3));
                        Double initialAssignment = Double.valueOf(offerMatcher.group(6));
                        Double assignemntCommission = Double.valueOf(offerMatcher.group(4));
                        //my commission Double agentComission = Double.valueOf(offerMatcher.group(9));


                        String onmouseover = offers.get(i).parent().getElementsByTag("label").get(0).attr("onmouseover");
                        Matcher intervalMatcher = intervalPattern.matcher(onmouseover);
                        boolean isIntervalFound = intervalMatcher.find();
                        if (isIntervalFound) {
                            Double minPeriodInDays = null;
                            String interval = intervalMatcher.group(1);
                            switch (interval) {
                                case "Quarter":
                                    minPeriodInDays = 122.;
                                    break;
                                case "Month":
                                    minPeriodInDays = 30.;
                                    break;
                                case "Week":
                                    minPeriodInDays = 7.;
                                    break;
                                case "Day":
                                    minPeriodInDays = 1.;
                                    break;
                                default:
                                    logger.warn("Incorrect interval id = " + interval);
                            }
                            if (minPeriodInDays != null) {
                                PammOfferFxOpen pammOfferFxOpen = new PammOfferFxOpen(manager.getName(), initialAssignment, minPeriodInDays, performanceFee, masterComission,
                                        minimumPerformanceConstraint, assignemntCommission,
                                        Currency.valueOf(manager.getCurrency()), avgChange, "https://pamm.fxopen.ru/ru/Pamm/" + manager.getName() + "?agent=9008256");
                                pamm.addOffer(pammOfferFxOpen);
                            }
                        }


                    }

                }
                if (CollectionUtils.isNotEmpty(pamm.getInvestmentTargetOffers())) {
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

    private String sendRequest(String url, String referer) throws IOException {
        URL urlManagerMoney = new URL(url);
        HttpURLConnection conManagerMoney = (HttpURLConnection) urlManagerMoney.openConnection();

        conManagerMoney.setRequestProperty("authority", "pamm.fxopen.com");
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
        return InvestmentTypeName.FX_OPEN;
    }
}
