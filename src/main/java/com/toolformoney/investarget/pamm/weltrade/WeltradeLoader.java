package com.toolformoney.investarget.pamm.weltrade;

import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.investarget.pamm.weltrade.model.WelTradePamm;
import com.toolformoney.investarget.pamm.weltrade.model.WeltradeAllPamsResult;
import com.toolformoney.investarget.pamm.weltrade.model.WeltradeItem;
import com.toolformoney.investarget.pamm.weltrade.model.WeltradeItemCode;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.pamm.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http://www.weltrade.ru/
 * Author Dmitriy Liandres
 * Date 25.09.2016
 */
@Singleton
public class WeltradeLoader extends PammLoader {
    private static final Logger logger = LoggerFactory.getLogger(WeltradeLoader.class);

    //fetch name
    Pattern TABLE_WITH_DETAILS_PATTERN = Pattern.compile("<table class=\"wt-traders-detail-table\">(.*<tr(.*?)</tr>.*)</table>", Pattern.DOTALL);
    //fetches one line info, for example, max load or investors number
    Pattern MANAGER_ONE_LINE_PATTERN = Pattern.compile("<tr(.*?)</tr>", Pattern.DOTALL);

    //fetches info from one line
    Pattern MANAGER_ONE_LINE_DETAILS_PATTERN = Pattern.compile("<td.*?>(.*?)</td>", Pattern.DOTALL);
    //fetches info about all profits
    Pattern PROFIT_PATTERN = Pattern.compile("(\\[\\[.*?\\].*?])", Pattern.DOTALL);

    @Override
    public List<Pamm> load() throws IOException {
        logger.info("Start download all Weltrade pamm managers");
        List<Pamm> pamms = new ArrayList<>();
        //load all traders
        int pageNumber = 1;
        WeltradeAllPamsResult weltradeAllPamsResult = loadPamms(pageNumber);
        while (CollectionUtils.isNotEmpty(weltradeAllPamsResult.getData().getItems())) {
            for (WeltradeItem weltradeItem : weltradeAllPamsResult.getData().getItems()) {
                try {
                    WeltradeItemCode weltradeItemCode = weltradeItem.getCode();
                    if (!"Y".equals(weltradeItemCode.getOpened())) {
                        continue;
                    }
                    Pamm pamm = new WelTradePamm();
                    pamm.setPammBroker(PammBroker.WELTRADE);
                    logger.info("Weltrade: load data for weltradeItem= {}", weltradeItemCode);
                    //get id, example of url: \/investors\/rating_pamm_account\/details\/?id=3876&PAGEN_1=1&cnt=20
                    String[] parts = weltradeItemCode.getDetail_page_url().split("\\/\\?");
                    String query = parts[1];
                    String[] queryParams = query.split("&");
                    String id = null;
                    for (String queryParam : queryParams) {
                        if (queryParam.startsWith("id=")) {
                            id = queryParam.split("=")[1];
                            break;
                        }
                    }
                    if (id == null) {
                        logger.error("Impossible to get id for weltradeItem = {}", weltradeItem);
                        continue;
                    }
                    pamm.setId(id);
                    //load all pamm trading data
                    //load page
                    URL urlPage = new URL("http://www.weltrade.ru/investors/rating_pamm_account/details/?id=" + id + "&PAGEN_1=1&cnt=20");
                    HttpURLConnection conPage = (HttpURLConnection) urlPage.openConnection();
                    String responsePage = IOUtils.toString(conPage.getInputStream(), "UTF-8");
                    String tableWithDetails = getMatchValue(TABLE_WITH_DETAILS_PATTERN, responsePage);
                    Matcher managerOneLineMather = MANAGER_ONE_LINE_PATTERN.matcher(tableWithDetails);

                    Double minInvestment = null;
                    Double commission = null;
                    Integer age = null;
                    Currency currency = null;
                    while (managerOneLineMather.find()) {
                        String oneLine = managerOneLineMather.group(1);
                        Matcher managerOneLineDetailsMather = MANAGER_ONE_LINE_DETAILS_PATTERN.matcher(oneLine);
                        boolean isFound = managerOneLineDetailsMather.find();
                        if (!isFound) {
                            continue;
                        }
                        String title = managerOneLineDetailsMather.group(1);
                        isFound = managerOneLineDetailsMather.find();
                        if (!isFound) {
                            continue;
                        }
                        String value = managerOneLineDetailsMather.group(1);
                        if (StringUtils.isNotEmpty(title)) {
                            switch (title) {
                                case "Общий капитал":
                                    String[] valueParts = value.split("&nbsp;");
                                    pamm.setTotalMoney(Double.valueOf(valueParts[0]));
                                    currency = Currency.valueOf(valueParts[1]);
                                    break;
                                case "Капитал Управляющего":
                                    pamm.setManagerMoney(Double.valueOf(value.replace("&nbsp;" + currency, "")));
                                    break;
                                case "Минимальная сумма инвестирования":
                                    minInvestment = Double.valueOf(value.replace("&nbsp;" + currency, ""));
                                    break;
                                case "Вознаграждение Управляющего":
                                    commission = Double.valueOf(value.replace("%", ""));
                                    break;
                                case "Возраст, дней":
                                    if (StringUtils.isEmpty(value)) {
                                        //no trades
                                        break;
                                    }
                                    age = Integer.valueOf(value);
                                    pamm.setAgeInDays(age);

                                    break;
                            }
                        }
                    }
                    if (age == null) {
                        //no trades
                        continue;
                    }
                    //
                    URL urlProfit = new URL("http://www.weltrade.ru/local/components/wt/pamm.account.detail/ajax.php?ID=" + id);
                    String profits = IOUtils.toString(urlProfit.openStream());
                    Matcher profitMatcher = PROFIT_PATTERN.matcher(profits);
                    //profit per date
                    List<DailyChange> changes = new ArrayList<>();

                    LocalDate previousDate = null;
                    Double previousProfit = 0.;
                    Double previousDateProfit = 0.;

                    while (profitMatcher.find()) {
                        String oneProfit = profitMatcher.group(1);
                        String[] values = oneProfit.split("\"");
                        Integer year = Integer.valueOf(values[1]);
                        Integer month = Integer.valueOf(values[3]);
                        Integer day = Integer.valueOf(values[5]);

                        Double profit = Double.valueOf(values[9]);
                        LocalDate currentDate = LocalDate.of(year, month, day);
                        if (previousDate != null && currentDate.isAfter(previousDate)) {
                            //new date
                            Double profitForDay = (previousProfit - previousDateProfit) / (previousDateProfit + 100) * 100;
                            changes.add(new DailyChange(previousDate, profitForDay));
                            previousDateProfit = previousProfit;
                        }
                        previousDate = currentDate;
                        previousProfit = profit;
                    }
                    Double avgChange = addChangesToPamm(changes, pamm);
                    InvestmentTargetOffer investmentTargetOffer = new InvestmentTargetOffer(weltradeItemCode.getDisplay_value(), minInvestment, null, 1., null, commission, weltradeItemCode.getDetail_page_url() + "&r1=ipartner&r2=9667", currency, avgChange, new PammOfferRisk(), new PammOfferProfit());
                    pamm.addOffer(investmentTargetOffer);
                    pamms.add(pamm);
                } catch (Exception e) {
                    logger.error("Impossible to load pamm, weltradeItem = {}", weltradeItem);
                }
            }
            weltradeAllPamsResult = loadPamms(++pageNumber);
        }
        logger.info("Finish download all Weltrade pamm managers");
        return pamms;
    }

    private WeltradeAllPamsResult loadPamms(int pageNumber) throws IOException {
        URL urlAll = new URL("http://www.weltrade.ru/investors/rating_pamm_account/");
        HttpURLConnection allConnection = (HttpURLConnection) urlAll.openConnection();
        //add request header
        allConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        allConnection.setRequestProperty("X-WT-AJAX-ID", "995897708ab9e42bb8e31d8aeb6da509");
        allConnection.setRequestProperty("Host", "www.weltrade.ru");
        allConnection.setRequestProperty("Origin", "http://www.weltrade.ru");
        allConnection.setRequestProperty("Referer", "http://www.weltrade.ru/investors/rating_pamm_account/");
        allConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36");


        allConnection.setReadTimeout(30000);
        allConnection.setConnectTimeout(30000);
        allConnection.setRequestMethod("POST");
        allConnection.setDoInput(true);
        allConnection.setDoOutput(true);


        DataOutputStream wr = new DataOutputStream(allConnection.getOutputStream());
        wr.writeBytes("ajax=Y&sort=INDICATOR_OBJECT.RATING&by=DESC&PAGEN_1=" + pageNumber + "&f_CODE=");
        wr.flush();
        wr.close();

        //maps returned json to the object
        return objectMapper.readValue(allConnection.getInputStream(), WeltradeAllPamsResult.class);
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.WEL_TRADE;
    }
}
