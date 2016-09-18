package com.toolformoney.investarget.pamm.alfaForex;

import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.investarget.pamm.alfaForex.model.AlfaForexPamm;
import com.toolformoney.investarget.pamm.alfaForex.model.PammAlfaForexManagerMoney;
import com.toolformoney.investarget.pamm.alfaForex.model.PammAlfaForexManagerMoneyResultItem;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.pamm.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Loads Pamms for alfa-forex.ru
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
@Singleton
//todo not loaded
public class AlfaForexLoader extends PammLoader {
    private static final Logger logger = LoggerFactory.getLogger(AlfaForexLoader.class);

    //patterns are used to fetch data from pages
    //fetch's csrf token
    Pattern CSRF_TOKEN_PATTERN = Pattern.compile("<meta name=\"csrf-token\" content=\"(.*?)\">");

    //fetches managers info
    Pattern MANAGER_PATTERN = Pattern.compile("<tr(.*?)</tr>", Pattern.DOTALL);
    //fetch id
    Pattern ID_PATTERN = Pattern.compile("view/(.*?)\"");
    //fetch name
    Pattern NAME_PATTERN = Pattern.compile("<p.*?>(.*?)</p>.*?");
    //fetchs age
    Pattern AGE_PATTERN = Pattern.compile("th_old_tbody\">(\\d*?) <span");
    //fetch total money
    Pattern TOTAL_MONEY_PATTERN = Pattern.compile("th_funds_tbody\">(.*?)</td>");
    //fetch currency
    Pattern CURRENCY_PATTERN = Pattern.compile("th_currency_tbody\">(.*?)</td>");

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public List<Pamm> load() throws IOException {
        logger.info("Download all Alfa forex managers");
        List<Pamm> pamms = new ArrayList<>();

        int page = 0;
        int resultsPerPage = 100;
        URL urlAll = new URL("https://www.alfa-forex.ru/ru/terms/pamm.html?&page=" + page + "&per_page=" + resultsPerPage + "&sortBy=position&sortType=desc");
           /*    results looks like
                 <tr data-link="https://my.alfa-forex.ru/public/pamm/view/7540" class="">
    <!--<td style="display:none;"></td>-->
    <td class="th_num_tbody">101</td>
    <td class="th_account_tbody z_index">136677</td>
    <td class="th_nik_tbody z_index">
        <p style="margin:0 !important;display:inline-block;vertical-align:middle;">FoxHunt</p>
    </td>
       <td class="th_old_tbody">35 <span class="dimension">дн.</span></td>
    <td class="th_income_tbody">101.46 <span class="dimension">%</span></td>
    <td class="th_funds_tbody">27558.69</td>
    <td class="th_currency_tbody">RUR</td>
           </tr> */
        HttpURLConnection con = (HttpURLConnection) urlAll.openConnection();
        //add request header. Without this header we will get full page, not just required part
        con.setRequestProperty("X-Requested-With", "XMLHttpRequest");

        String response = IOUtils.toString(con.getInputStream(), "UTF-8");

        boolean isCsrfTokenLoad = false;
        String cookies = null;
        String csrfToken = null;
        //we load block, each includes resultsPerPage managers
        while (StringUtils.isNotEmpty(response)) {

            Matcher managersMather = MANAGER_PATTERN.matcher(response);

            //let's overwork manages one by one
            while (managersMather.find()) {
                String managerBlock = managersMather.group(1).replaceAll("RUR", "RUB");

                String id = getMatchValue(ID_PATTERN, managerBlock);

                logger.info("Start load AlfaForex manager details, pammId = {}", id);

                String name = getMatchValue(NAME_PATTERN, managerBlock);
                Integer ageInDays = Integer.valueOf(getMatchValue(AGE_PATTERN, managerBlock));
                Double totalMoney = Double.valueOf(getMatchValue(TOTAL_MONEY_PATTERN, managerBlock));
                Currency currency = Currency.valueOf(getMatchValue(CURRENCY_PATTERN, managerBlock));

                Pamm pamm = new AlfaForexPamm();
                pamm.setPammBroker(PammBroker.ALFA_FOREX);
                pamm.setId(id);
                pamm.setAgeInDays(ageInDays);
                pamm.setTotalMoney(totalMoney);

                if (!isCsrfTokenLoad) {
                    //we have to load csrf token and cookie to load json data in next requests
                    logger.info("Load cookies and csrf");
                    URL urlBase = new URL("https://my.alfa-forex.ru/public/pamm/view/" + id);
                    HttpURLConnection conBase = (HttpURLConnection) urlBase.openConnection();
                    conBase.setRequestMethod("GET");
                    cookies = conBase.getHeaderField("Set-Cookie");
                    String responseBase = IOUtils.toString(conBase.getInputStream(), "UTF-8");
                    csrfToken = getMatchValue(CSRF_TOKEN_PATTERN, responseBase);
                    logger.info("Cookies and csrf loaded: cookies = {}, csrfToken={}", cookies, csrfToken);
                    isCsrfTokenLoad = true;
                }


                logger.info("load manager money, pammId = {}", pamm.getId());

                LocalDate now = LocalDate.now();
                LocalDate yesterday = now.minusDays(1);
                LocalDate dayBeforeYesterday = now.minusDays(2);
                LocalDate firstWorkDate = now.minusDays(pamm.getAgeInDays());

                String yesterdayText = yesterday.format(dateFormatter);
                String dayBeforeYesterdayText = dayBeforeYesterday.format(dateFormatter);
                String firstWorkDateText = firstWorkDate.format(dateFormatter);

                //to get investor money we have to send separate request
                PammAlfaForexManagerMoney pammAlfaForexManagerMoney = createHttpURLConnectionForSecuredPost
                        ("https://my.alfa-forex.ru/pamm/account-public/chart-data?id=" + id + "&type=capitalChart", "dateFrom=" + dayBeforeYesterdayText + "&dateTo=" + yesterdayText, cookies, csrfToken, PammAlfaForexManagerMoney.class);
                pamm.setManagerMoney(pammAlfaForexManagerMoney.getResult().get(0).getValue());

                //let's load changes
                pammAlfaForexManagerMoney = createHttpURLConnectionForSecuredPost
                        ("https://my.alfa-forex.ru/pamm/account-public/chart-data?id=" + id + "&type=profitChart", "dateFrom=" + firstWorkDateText + "&dateTo=" + yesterdayText, cookies, csrfToken, PammAlfaForexManagerMoney.class);

                //let's load deposit load
                PammAlfaForexManagerMoney pammAlfaForexdeDepositLoad = createHttpURLConnectionForSecuredPost
                        ("https://my.alfa-forex.ru/pamm/account-public/chart-data?id=" + id + "&type=loadingChart", "dateFrom=" + firstWorkDateText + "&dateTo=" + yesterdayText, cookies, csrfToken, PammAlfaForexManagerMoney.class);
                Map<String, Double> depositLoadPerDate = new HashMap<>();
                pammAlfaForexdeDepositLoad.getResult().forEach(depositLoad -> depositLoadPerDate.put(depositLoad.getDate(), depositLoad.getValue()));

                List<DailyChange> changes = new ArrayList<>();
                final Double[] previousValue = {0.};

                for (PammAlfaForexManagerMoneyResultItem resultItem : pammAlfaForexManagerMoney.getResult()) {
                    if (depositLoadPerDate.get(resultItem.getDate()) == 0.) {
                        //no deposit load, no need to calculate
                        continue;
                    }
                    //if resultItem.getValue() == -100, it means, that account was closed, but let's calculate next way
                    Double change = resultItem.getValue() == -100 ? -100 : (resultItem.getValue() - previousValue[0]) * 100 / (previousValue[0] + 100);
                    LocalDate localDate = LocalDate.parse(resultItem.getDate(), dateFormatter);

                    changes.add(new DailyChange(localDate, change));
                    previousValue[0] = resultItem.getValue();
                }

                Double avgChange = addChangesToPamm(changes, pamm);

                //load commissions
                Document doc = Jsoup.connect("https://my.alfa-forex.ru/public/pamm/view/" + id + "#offer").timeout(30000).get();
                Elements cells = doc.getElementById("w11-container").getElementsByTag("td");
                Double percentage = Double.valueOf(cells.get(1).text().replace("%", ""));
                Double minInvestment = Double.valueOf(cells.get(2).text().split("/")[0]);
                Double minPeriod = Double.valueOf(cells.get(3).text());

                pamm.addOffer(new InvestmentTargetOffer(name, minInvestment, null, minPeriod, null, percentage, "https://my.alfa-forex.ru/public/pamm/view/" + pamm.getId() + "?partner_id=719755",
                        currency, avgChange, new PammOfferRisk(), new PammOfferProfit()));

                pamms.add(pamm);

                logger.info("Finish load AlfaForex manager details, pammId = {}", id);


            }

            page++;
            urlAll = new URL("https://www.alfa-forex.ru/ru/terms/pamm.html?&page=" + page + "&per_page=" + resultsPerPage + "&sortBy=position&sortType=desc");
            con = (HttpURLConnection) urlAll.openConnection();
            con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            response = IOUtils.toString(con.getInputStream(), "UTF-8");
        }

        filterUselessPamms(pamms);
        return pamms;
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.ALFA_FOREX;
    }

    /**
     * Loads data from specified url, using post method and returns java object as a result
     *
     * @param url           url
     * @param urlParameters list of params. params are sent as form params
     * @param cookies       cookie
     * @param csrfToken     csrf Token
     * @param resultClass   result class
     * @return object of result class
     * @throws IOException
     */
    private <T> T createHttpURLConnectionForSecuredPost(String url, String urlParameters, String cookies, String csrfToken, Class<T> resultClass) throws IOException {
        URL urlManagerMoney = new URL(url);
        HttpURLConnection conManagerMoney = (HttpURLConnection) urlManagerMoney.openConnection();
        //add request header
        conManagerMoney.setRequestProperty("Cookie", cookies);
        conManagerMoney.setRequestProperty("X-CSRF-Token", csrfToken);
        conManagerMoney.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        conManagerMoney.setReadTimeout(30000);
        conManagerMoney.setConnectTimeout(30000);
        conManagerMoney.setRequestMethod("POST");
        conManagerMoney.setDoInput(true);
        conManagerMoney.setDoOutput(true);


        DataOutputStream wr = new DataOutputStream(conManagerMoney.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        return objectMapper.readValue(conManagerMoney.getInputStream(), resultClass);
    }


}
