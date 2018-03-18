package com.toolformoney.investarget.tradesignals.mql5;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.exchangerates.FixerIOExchangeRates;
import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.investarget.tradesignals.mql5.model.*;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.forex.ForexLoader;
import com.toolformoney.model.forex.ForexOfferProfit;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.tradesignals.TradeSignal;
import com.toolformoney.utils.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Author Dmitriy Liandres
 * Date 11.12.2016
 */
@Singleton
public class MetaTrader4Loader extends ForexLoader<TradeSignal> {
    private static final Logger logger = LoggerFactory.getLogger(MetaTrader4Loader.class);

    private static final String RETURN_CHAR_DATA = "Signals.ReturnChartData = [";
    private static final String RETURN_CHAR_INDEX = "Signals.ReturnChartIndex = ";
    private static final String EQUITY_CHAR_DATA = "Signals.EquityChartData = [";
    private static final String EQUITY_CHAR_BALANCES = "Signals.EquityChartBalances = [";

    private static final DateTimeFormatter dateTimeFormatterLong = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:SS");
    private static final DateTimeFormatter dateTimeFormatterShort = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    private static final String ANONIMIZER = "http://0s.o53xo.nvywynjomnxw2.nblz.ru/"; //0s.o53xo.nvywynjomnxw2.cmle.ru

    @Override
    public List<TradeSignal> load() throws IOException {

        logger.info("Download all mql5 metatrader 4 signals");
        LocalDate now = LocalDate.now();
        YearMonth yearMonthNow = YearMonth.of(now.getYear(), now.getMonth());
        String authCookie = getAuthCookie();
        logger.info("authCookie = {}", authCookie);
        List<TradeSignal> tradeSignals = new ArrayList<>();
        int page = 1;
        while (true) {
            Document doc = null;
            try {
                doc = Jsoup.connect(ANONIMIZER + "ru/signals/mt4/page" + page).timeout(30000).get();
            } catch (HttpStatusException e) {
                break;
            }
            try {
                Elements signals = doc.getElementsByClass("signal");
                if (CollectionUtils.isEmpty(signals)) {
                    break;
                }
                for (Element signal : signals) {
                    try {
                        Thread.sleep(500);
                        Element linkToSignal = signal.getElementsByTag("a").get(0);
                        String[] hrefParts = linkToSignal.attr("href").split("/");
                        //to do
                        String id = hrefParts[hrefParts.length - 1];
                        //String id = "263900";


                        logger.info("Download mql5 metatrader 4 signal {}", id);

                        //load signal data
                        Document signalDoc = null;
                        try {
                            signalDoc = Jsoup.connect(ANONIMIZER + "ru/signals/" + id).timeout(30000).get();
                        } catch (Exception e) {
                            Thread.sleep(60000);
                            authCookie = getAuthCookie();
                            signalDoc = Jsoup.connect(ANONIMIZER + "ru/signals/" + id).timeout(30000).get();
                        }

                        //check whether subsription is enabled
                        Element subscribeButtonEl = signalDoc.getElementsByClass("subscribeButton").get(0);
                        if (subscribeButtonEl.hasClass("disabled")) {
                            //signal is not enabled
                            continue;
                        }

                        //get price
                        Double commission = 0.;
                        Element priceEl = signalDoc.getElementsByAttributeValue("itemprop", "price").get(0);
                        commission = Double.valueOf(priceEl.attr("content"));

                        String signalName = signalDoc.getElementsByClass("title-min").get(0).getElementsByTag("a").get(0).text();

                        TradeSignal tradeSignal = new Mql5TradeSignal(commission);
                        tradeSignal.setId(id);
                        Element signalsTradeData = signalDoc.getElementsByClass("product-left-panel").get(0).getElementsByClass("signalsTradeData").get(0);
                        String managerMoneyStr = signalsTradeData.getElementsByClass("items").get(0).getElementsByTag("span").get(4).text();
                        String currency = managerMoneyStr.substring(managerMoneyStr.length() - 3, managerMoneyStr.length());
                        if (!Currency.isRelevant(currency)) {
                            logger.warn("Download mql5 metatrader 4 signal {}, Absent currency {}", id, currency);
                            continue;
                        }

                        managerMoneyStr = managerMoneyStr.replaceAll(" " + currency, "").replaceAll(" ", "");
                        Double managerMoney = FixerIOExchangeRates.convert(Double.valueOf(managerMoneyStr), Currency.valueOf(currency), Currency.USD);
                        tradeSignal.setManagerMoney(managerMoney);

                        String usersMoneyStr = signalsTradeData.getElementsByAttributeValue("title", "Совокупные средства только реальных счетов в рамках используемых рисков").get(0).getElementsByTag("span").text().replaceAll(" USD", "");
                        char lastLetter = usersMoneyStr.charAt(usersMoneyStr.length() - 1);
                        Double usersMoney = 0.;
                        switch (lastLetter) {
                            case 'K':
                                usersMoney = Double.valueOf(usersMoneyStr.substring(0, usersMoneyStr.length() - 1)) * 1000;
                                break;
                            case 'M':
                                usersMoney = Double.valueOf(usersMoneyStr.substring(0, usersMoneyStr.length() - 1)) * 1000_000;
                                break;
                            default:
                                usersMoney = Double.valueOf(usersMoneyStr);
                        }

                        tradeSignal.setTotalMoney(usersMoney + managerMoney);

                        Elements scripts = signalDoc.getElementsByTag("script");
                        List<DailyChange> changes = new ArrayList<>();
                        //get months which we should not take into consideration
                        Elements elementsWithMonthsToIgnore = signalDoc.select(".cell.gray");
                        Map<Integer, Set<Integer>> monthsToIgnore = new HashMap<>();
                        if (CollectionUtils.isNotEmpty(elementsWithMonthsToIgnore)) {
                            elementsWithMonthsToIgnore.forEach(elementWithMonthsToIgnore -> {
                                //returnCell20163
                                String yearMonthToIgnore = elementWithMonthsToIgnore.id().replaceAll("returnCell", "");
                                Integer year = Integer.valueOf(yearMonthToIgnore.substring(0, 4));
                                Integer month = Integer.valueOf(yearMonthToIgnore.substring(4));
                                monthsToIgnore.putIfAbsent(year, new HashSet<>());
                                monthsToIgnore.get(year).add(month);

                            });
                        }
                        for (Element script : scripts) {
                            String scriptHtml = script.html();
                            if (scriptHtml.contains(RETURN_CHAR_DATA)) {
                                Integer returnChartDataStartIndex = scriptHtml.indexOf(RETURN_CHAR_DATA);
                                Integer returnChartDataEndIndex = scriptHtml.indexOf("]", returnChartDataStartIndex);
                                String returnChartData = scriptHtml.substring(returnChartDataStartIndex + RETURN_CHAR_DATA.length(), returnChartDataEndIndex);
                                String[] returnChartDataParts = returnChartData.split(",");
                                List<ReturnChartDataPoint> returnChartDataPoints = new ArrayList<>();
                                if (returnChartDataParts.length > 0) {
                                    for (int i = 2; i < returnChartDataParts.length; i += 2) {
                                        String tradeNumber = returnChartDataParts[i];
                                        String profit = returnChartDataParts[i + 1];
                                        ReturnChartDataPoint returnChartDataPoint = new ReturnChartDataPoint(Integer.valueOf(tradeNumber), Double.valueOf(profit));
                                        returnChartDataPoints.add(returnChartDataPoint);
                                    }
                                }

                                Integer returnChartIndexStartIndex = scriptHtml.indexOf(RETURN_CHAR_INDEX);
                                Integer returnChartIndexEndIndex = scriptHtml.indexOf(";", returnChartIndexStartIndex);
                                String returnChartIndex = scriptHtml.substring(returnChartIndexStartIndex + RETURN_CHAR_INDEX.length(), returnChartIndexEndIndex);

                                Map<String, Map<String, String>> chartIndex = objectMapper.readValue(returnChartIndex.replaceAll("'", "\""), new TypeReference<Map<String, Map<String, String>>>() {
                                });

                                final Double[] previousChange = {0.};
                                final LocalDate[] firstTradingDate = {null};
                                chartIndex.forEach((monthStr, trades) -> {
                                    String[] monthStrParts = monthStr.split("\\.");
                                    if ("0".equals(monthStrParts[1])) {
                                        return;

                                    }
                                    Integer year = Integer.valueOf(monthStrParts[0]);
                                    Integer month = Integer.valueOf(monthStrParts[1]);

                                    Integer startTradesIndex = Integer.valueOf(trades.get("i"));
                                    Integer tradesNumber = Integer.valueOf(trades.get("l"));
                                    if (monthsToIgnore.getOrDefault(year, new HashSet<>()).contains(month)) {
                                        //this month should be ignored
                                        ReturnChartDataPoint returnChartDataPoint = returnChartDataPoints.get(startTradesIndex + tradesNumber - 1);
                                        previousChange[0] = returnChartDataPoint.getProfit();
                                        return;
                                    }
                                    if (tradesNumber > 0) {
                                        YearMonth yearMonthObject = YearMonth.of(year, month);

                                        int daysInMonth = yearMonthObject.equals(yearMonthNow) ? now.getDayOfMonth() : yearMonthObject.lengthOfMonth();

                                        for (Integer tradeNumber = 0; tradeNumber < tradesNumber; tradeNumber++) {


                                            Integer date = (tradeNumber + 1) * daysInMonth / tradesNumber;
                                            Integer dateNext = (tradeNumber + 2) * daysInMonth / tradesNumber;
                                            if (date == 0 || (date.equals(dateNext) && tradeNumber != tradesNumber - 1)) {
                                                //not last date and previous is the same as current
                                                continue;
                                            }
                                            LocalDate localDate = LocalDate.of(year, month, date);
                                            if (tradeSignal.getAgeInDays() == null) {
                                                tradeSignal.setAgeInDays((int) ChronoUnit.DAYS.between(localDate, now));
                                                firstTradingDate[0] = localDate;
                                            }

                                            ReturnChartDataPoint returnChartDataPoint = returnChartDataPoints.get(startTradesIndex + tradeNumber);
                                            Double currentChange = returnChartDataPoint.getProfit();
                                            DailyChange dailyChange = new DailyChange(localDate, (currentChange - previousChange[0]) / (100 + previousChange[0]) * 100);
                                            changes.add(dailyChange);
                                            previousChange[0] = currentChange;
                                        }
                                    }

                                });

                                //let's calculate how many months were missed during trading;
                                Set<String> allMonths = new HashSet<>();
                                if (firstTradingDate[0] != null) {
                                    LocalDate monthToCheck = firstTradingDate[0];
                                    while (monthToCheck.isBefore(now)) {
                                        allMonths.add(monthToCheck.getYear() + "." + monthToCheck.getMonth().getValue());
                                        monthToCheck = monthToCheck.plusMonths(1);
                                    }

                                    allMonths.add(now.getYear() + "." + now.getMonth().getValue());
                                }
                                double monthNumber = allMonths.size();
                                allMonths.removeAll(chartIndex.keySet());
                                int monthWithoutTradesNumber = allMonths.size();
                                Double monthCoefficient = monthWithoutTradesNumber == 0 ? 1 : (monthNumber - monthWithoutTradesNumber) / monthNumber;


                                Double avgChange = addChangesToForexTrades(changes, tradeSignal);
                                avgChange *= monthCoefficient;
                                tradeSignal.addOffer(new InvestmentTargetOffer(signalName, null, null, null, null, 0., "https://www.mql5.com/ru/signals/" + id,
                                        Currency.USD, avgChange, new Mql5OfferRisk(), new ForexOfferProfit()));
                                tradeSignals.add(tradeSignal);
                                //check additional risk


                                Integer equityChartDataStartIndex = scriptHtml.indexOf(EQUITY_CHAR_DATA);
                                Integer equityChartDataEndIndex = scriptHtml.indexOf("]", equityChartDataStartIndex);
                                String equityChartData = scriptHtml.substring(equityChartDataStartIndex + EQUITY_CHAR_DATA.length(), equityChartDataEndIndex);
                                String[] equityChartDataParts = equityChartData.split(",");
                                List<EquityChartDataPoint> equityChartDataPoints = new ArrayList<>();
                                if (equityChartDataParts.length > 0) {
                                    for (int i = 0; i < equityChartDataParts.length; i += 3) {
                                        Long time = Long.valueOf(equityChartDataParts[i]) * 1000;
                                        Double balance = Double.valueOf(equityChartDataParts[i + 1]);
                                        Double equity = Double.valueOf(equityChartDataParts[i + 2]);
                                        EquityChartDataPoint equityChartDataPoint = new EquityChartDataPoint(time, balance, equity);
                                        equityChartDataPoints.add(equityChartDataPoint);
                                    }
                                }
                                //get average lots during last 60 trades
//                                List<Double> lots = new ArrayList<>();
//                                for (int i = 1; i <= 3; i++) {
//                                    List<Double> lotsOnOnePage = loadHistory(id, i, authCookie);
//                                    lots.addAll(lotsOnOnePage);
//                                    if (lotsOnOnePage.size() == 0) {
//                                        //last page
//                                        break;
//                                    }
//
//                                }
                                //we need average lot to check whether user with provided summ can use this account at all
                                ChartHistory chartHistory = loadFullHistory(id, authCookie);
                                tradeSignal.setAverageLot(chartHistory.getAverageLot());
                                tradeSignal.setAddsNumber(chartHistory.getAddsNumber());

                                //we add risk = average between max and average difference between balance and equity
                                Double averageEquityRisk = 0.;
                                Double maxEquityRisk = 0.;
                                String tradesNumberStr = signalDoc.getElementById("tradeDataColumns").getElementsByClass("signalsTradeData").get(0).getElementsByTag("span").get(0).text();
                                Double tradesNumber = Double.valueOf(tradesNumberStr.replaceAll(" ", ""));
                                if (CollectionUtils.isNotEmpty(equityChartDataPoints)) {
                                    int pointsNumber = 0;
                                    LocalDate nowMinusYear = now.minusDays(Constants.DAYS_PER_YEAR.longValue());
                                    Long nowMinusYearLong = nowMinusYear.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                                    for (EquityChartDataPoint equityChartDataPoint : equityChartDataPoints) {
                                        if (equityChartDataPoint.getTime() > nowMinusYearLong) {
                                            Double risk = Math.abs((equityChartDataPoint.getBalance() - equityChartDataPoint.getEquity()) / equityChartDataPoint.getBalance());
                                            averageEquityRisk += risk;
                                            if (maxEquityRisk < risk) {
                                                maxEquityRisk = risk;
                                            }
                                            pointsNumber++;
                                        }
                                    }
                                    if (pointsNumber > 0) {
                                        averageEquityRisk = averageEquityRisk * 100 / pointsNumber;
                                    }
                                }
                                maxEquityRisk *= 100;
                                tradeSignal.setAverageEquityRisk(averageEquityRisk);
                                tradeSignal.setMaxEquityRisk(maxEquityRisk);
                                tradeSignal.setTradesNumber(tradesNumber);

                                break;

                            }
                            /*Integer returnChartDataStartIndex = scriptHtml.indexOf(EQUITY_CHAR_DATA);
                            if (returnChartDataStartIndex >= 0) {
                                Integer equityChartDataEndIndex = scriptHtml.indexOf("]", returnChartDataStartIndex);
                                String equityChartData = scriptHtml.substring(returnChartDataStartIndex + EQUITY_CHAR_DATA.length(), equityChartDataEndIndex);
                                String[] equityChartDataParts = equityChartData.split(",");
                                List<EquityChartDataPoint> equityChartDataPoints = new ArrayList<>();
                                if (equityChartDataParts.length > 0) {
                                    for (int i = 0; i < equityChartDataParts.length; i += 3) {
                                        Long time = Long.valueOf(equityChartDataParts[i]) * 1000;
                                        Double balance = Double.valueOf(equityChartDataParts[i + 1]);
                                        Double equity = Double.valueOf(equityChartDataParts[i + 2]);
                                        EquityChartDataPoint equityChartDataPoint = new EquityChartDataPoint(time, balance, equity);
                                        equityChartDataPoints.add(equityChartDataPoint);
                                    }
                                }

                                Integer equityChartBalancesStartIndex = scriptHtml.indexOf(EQUITY_CHAR_BALANCES);
                                Integer equityChartBalancesEndIndex = scriptHtml.indexOf("]", equityChartBalancesStartIndex);
                                String equityChartBalancesStr = scriptHtml.substring(equityChartBalancesStartIndex + EQUITY_CHAR_BALANCES.length(), equityChartBalancesEndIndex);
                                String[] equityChartBalancesStrParts = equityChartBalancesStr.split(",");
                                List<EquityChartBalance> equityChartBalances = new LinkedList<>();
                                if (equityChartBalancesStrParts.length > 0) {
                                    for (int i = 0; i < equityChartBalancesStrParts.length; i += 2) {
                                        //let's add 10 minutes to balance change, because mql5 site have latency
                                        Long time = Long.valueOf(equityChartBalancesStrParts[i]) * 1000 + TimeUnit.MINUTES.toMillis(10);
                                        Double balance = Double.valueOf(equityChartBalancesStrParts[i + 1]);
                                        EquityChartBalance equityChartBalance = new EquityChartBalance(time, balance);
                                        equityChartBalances.add(equityChartBalance);
                                    }
                                }

                                if (equityChartDataPoints.size() > 0) {
                                    //let's remove last date - it is always today
                                    equityChartDataPoints.remove(equityChartDataPoints.size() - 1);
                                }
                                Double previousEquityValue = 0.;
                                if (equityChartDataPoints.size() > 0) {
                                    previousEquityValue = equityChartDataPoints.get(0).getEquity();
                                }

                                for (int i = 0; i < equityChartDataPoints.size(); i++) {
                                    EquityChartDataPoint equityChartDataPoint = equityChartDataPoints.get(i);
                                    LocalDate equityDate =
                                            Instant.ofEpochMilli(equityChartDataPoint.getTime()).atZone(ZoneId.of("America/Los_Angeles")).toLocalDate();
                                    LocalDate equityDateNext = null;
                                    if (i < equityChartDataPoints.size() - 1) {
                                        equityDateNext = Instant.ofEpochMilli(equityChartDataPoints.get(i + 1).getTime()).atZone(ZoneId.of("America/Los_Angeles")).toLocalDate();
                                    }
                                    if (equityDateNext != null && equityDateNext.isAfter(equityDate)) {
                                        //check whether balance was changed
                                        Double equityChange = equityChartDataPoint.getEquity() - previousEquityValue;
                                        while (equityChartBalances.size() > 0 && equityChartBalances.get(0).getTime() < equityChartDataPoint.getTime()) {
                                            equityChange -= equityChartBalances.get(0).getValue();
                                            equityChartBalances.remove(0);

                                        }

                                        if (tradeSignal.getAgeInDays() == null) {
                                            tradeSignal.setAgeInDays((int) ChronoUnit.DAYS.between(equityDate, now));
                                        }
                                        DailyChange dailyChange = new DailyChange(equityDate, equityChange / previousEquityValue * 100);
                                        previousEquityValue = equityChartDataPoint.getEquity();
                                        changes.add(dailyChange);

                                    }

                                }


                            }  */


                        }
                    } catch (Exception e) {
                        logger.error("Impossible to load signal {}", signal, e);
                        Thread.sleep(60000);
                        authCookie = getAuthCookie();
                    }
                   // break;//to do
                }


            } catch (Exception e) {
                logger.error("Impossible to load page {}", page, e);
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e1) {
                    logger.info("Impossible to start timer", e);
                }
                authCookie = getAuthCookie();
            }
            logger.info("Download all mql5 metatrader 4 signals, page {} is done, size = {}", page, tradeSignals.size());
            page++;

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                logger.info("Downloaded all mql5 metatrader 4 signals,impossible to sleep", e);
            }
            //break;//to do
        }
        logger.info("Downloaded all mql5 metatrader 4 signals, size = {}", tradeSignals.size());
        return tradeSignals;
    }

    /**
     * @return list with lots
     */
    public List<Double> loadHistory(String id, Integer historyPageNumber, String authCookie) throws IOException, InterruptedException {
        Thread.sleep(1000);

        List<Double> lots = new ArrayList<>();
        String url = ANONIMIZER + "ru/signals/" + id + "/history/page" + historyPageNumber;
        URL urlAll = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlAll.openConnection();
        //add request header. Without this header we will get full page, not just required part
        con.setRequestProperty("Accept", "*/*");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.8,ru;q=0.6,uk;q=0.4");
        con.setRequestProperty("Connection", "keep-alive");
        //con.setRequestProperty("Cookie", "__gads=ID=9f9fe185f495d383:T=1479826231:S=ALNI_MbNhN16liIRGRrc8h6UD-tTBjMdLQ; _ym_uid=1479833618107190641; _ga=GA1.2.874527927.1479833696; uniq=5170FD7A-8193-S-161218; lang=ru; sid=asr1ywrxev2dewgfzs40bnja; e323291854_noplace_shown=true; _media_uuid=2166088081; auth=308292556FF0245DED489949C6C03012B57D58C926B314F4F6DA6FDED0269E4626CDED298B23C16FACFB61AB45E728F0E960F884E04F0FF2149E0BAF8BF017E3844F5501138938E5FD5954F4B9A8D4166B5A5A6A");
        con.setRequestProperty("Cookie", authCookie);
        con.setRequestProperty("Host", "0s.o53xo.nvywynjomnxw2.cmle.ru");
        con.setRequestProperty("Referer", url);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        con.setRequestProperty("X-Requested-With", "XMLHttpRequest");

        String response = IOUtils.toString(con.getInputStream(), "UTF-8");
        Document historyDoc = Jsoup.parse(response);
        Elements historyLines = historyDoc.getElementsByTag("tbody").get(0).getElementsByTag("tr");
        if (CollectionUtils.isNotEmpty(historyLines)) {
            historyLines.forEach(historyLine -> {
                Elements tds = historyLine.getElementsByTag("td");
                if (CollectionUtils.isEmpty(tds) || tds.size() == 1) {
                    return;
                }
                String lot = tds.get(2).text();
                if (StringUtils.isNotEmpty(lot) && !"0".equals(lot)) {
                    lots.add(Double.valueOf(lot));
                }

            });
        }
        return lots;

    }

    /**
     * @return list with lots
     */
    public ChartHistory loadFullHistory(String id, String authCookie) throws IOException, InterruptedException {
        Thread.sleep(1000);
        String url = ANONIMIZER + "ru/signals/" + id + "/export/history";
        URL urlAll = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlAll.openConnection();
        //add request header. Without this header we will get full page, not just required part
        con.setRequestProperty("Cookie", authCookie);
        con.setRequestProperty("Host", "0s.o53xo.nvywynjomnxw2.cmle.ru");
        con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        con.setRequestProperty("accept-language", "en-US,en;q=0.8,ru;q=0.6,uk;q=0.4");
        con.setRequestProperty("referer", "https://www.mql5.com/ru/signals/" + id);
        con.setRequestProperty("upgrade-insecure-requests", "1");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");


        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        ChartHistory chartHistory = new ChartHistory();
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(in);
        LocalDate now = LocalDate.now();
        LocalDate nowMinus3Month = now.minusMonths(3);
        List<Double> lots = new ArrayList<>();
        records.forEach(csvRecord -> {
            if (csvRecord.size() < 12) {
                return;
            }
            String time = csvRecord.get(0);
            if ("time".equalsIgnoreCase(time)) {
                return;
            }
            //we need lots only for the last 3 month
            String type = csvRecord.get(1);
            String volume = csvRecord.get(2);
            String profit = csvRecord.get(11);

            if ("Balance".equals(type)) {
                if (profit.startsWith("-")) {
                    chartHistory.incAdds();
                }
                return;
            }
            if (StringUtils.isNotEmpty(volume)) {
                LocalDate parsedDate = time.length() == 10 ? LocalDate.parse(time, dateTimeFormatterShort) : LocalDate.parse(time, dateTimeFormatterLong);
                if (nowMinus3Month.isBefore(parsedDate)) {
                    lots.add(Double.valueOf(volume));
                }
            }
        });
        if (lots.size() > 0) {
            chartHistory.setAverageLot(lots.stream().mapToDouble(lot -> lot).average().getAsDouble());
        }
        return chartHistory;
    }


    private String getAuthCookie() throws IOException {
        URL urlAuthLogin = new URL(ANONIMIZER + "ru/auth_login");
        HttpURLConnection con = (HttpURLConnection) urlAuthLogin.openConnection();
        //add request header. Without this header we will get full page, not just required part
        con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.8,ru;q=0.6,uk;q=0.4");
        con.setRequestProperty("Cache-Control", "max-age=0");
        con.setRequestProperty("Connection", "keep-alive");
        //con.setRequestProperty("Content-Length:147");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Cookie", "__gads=ID=9f9fe185f495d383:T=1479826231:S=ALNI_MbNhN16liIRGRrc8h6UD-tTBjMdLQ; _ym_uid=1479833618107190641; _ga=GA1.2.874527927.1479833696; uniq=5170FD7A-8193-S-161218; lang=ru; sid=asr1ywrxev2dewgfzs40bnja; e323291854_noplace_shown=true; _media_uuid=2166088081");
        con.setRequestProperty("Host", "0s.o53xo.nvywynjomnxw2.cmle.ru");
        con.setRequestProperty("Origin", "http://0s.o53xo.nvywynjomnxw2.cmle.ru");
        con.setRequestProperty("Referer", ANONIMIZER + "ru/auth_login");
        con.setRequestProperty("Upgrade-Insecure-Requests", "1");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        con.setInstanceFollowRedirects(false);
        con.setReadTimeout(30000);
        con.setConnectTimeout(30000);
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);


        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes("RedirectAfterLoginUrl=http%3A%2F%2F0s.o53xo.nvywynjomnxw2.cmle.ru%2F&RegistrationUrl=&ViewType=0&Login=dima-amid&Password=fPXskx3g&RememberMe=false");
        wr.flush();
        wr.close();

        String locationWithToken = con.getHeaderField("Location");


        URL urlFromLocation = new URL(locationWithToken);
        HttpURLConnection conToFinalUrl = (HttpURLConnection) urlFromLocation.openConnection();
        //add request header. Without this header we will get full page, not just required part
        conToFinalUrl.setRequestProperty("Accept", "*/*");
        conToFinalUrl.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");

        conToFinalUrl.setRequestProperty("Accept-Language", "en-US,en;q=0.8,ru;q=0.6,uk;q=0.4");
        conToFinalUrl.setRequestProperty("Cache-Control", "max-age=0");
        conToFinalUrl.setRequestProperty("Connection", "keep-alive");
        conToFinalUrl.setRequestProperty("Cookie", "__gads=ID=9f9fe185f495d383:T=1479826231:S=ALNI_MbNhN16liIRGRrc8h6UD-tTBjMdLQ; _ym_uid=1479833618107190641; _ga=GA1.2.874527927.1479833696; uniq=5170FD7A-8193-S-161218; lang=ru; sid=asr1ywrxev2dewgfzs40bnja; e323291854_noplace_shown=true; _media_uuid=2166088081");
        conToFinalUrl.setRequestProperty("Host", "0s.o53xo.nvywynjomnxw2.cmle.ru");
        conToFinalUrl.setRequestProperty("Referer", ANONIMIZER + "ru/auth_login");
        conToFinalUrl.setRequestProperty("Upgrade-Insecure-Requests", "1");
        conToFinalUrl.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        conToFinalUrl.setInstanceFollowRedirects(false);
        List<String> cookies = conToFinalUrl.getHeaderFields().get("Set-Cookie");


        return StringUtils.join(cookies, "; ");
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.MQL5;
    }


}

