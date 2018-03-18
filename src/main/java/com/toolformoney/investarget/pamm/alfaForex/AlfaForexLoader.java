package com.toolformoney.investarget.pamm.alfaForex;

import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.investarget.pamm.alfaForex.model.AlfaForexPamm;
import com.toolformoney.investarget.pamm.alfaForex.model.PammAlfaForexManagerMoney;
import com.toolformoney.investarget.pamm.alfaForex.model.PammAlfaForexManagerMoneyResultItem;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.forex.ForexLoader;
import com.toolformoney.model.forex.ForexOfferProfit;

import com.toolformoney.model.pamm.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Loads Pamms for alfa-forex.ru
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
@Singleton
public class AlfaForexLoader extends ForexLoader {
    private static final Logger logger = LoggerFactory.getLogger(AlfaForexLoader.class);

    Pattern CSRF_HIDDEN_TOKEN_PATTERN = Pattern.compile("<input type=\"hidden\" name=\"_csrf\" value=\"(.*?)\">");

    //fetches managers info
    Pattern MANAGER_PATTERN = Pattern.compile("<tr data-key(.*?)</tr>", Pattern.DOTALL);
    //fetch id
    Pattern ID_PATTERN = Pattern.compile("view/(.*?)\"");
    //fetch name
    Pattern NAME_PATTERN = Pattern.compile("<a .*>(.*?)</a>");
    //fetchs age
    Pattern AGE_PATTERN = Pattern.compile("<td>(\\d*?) <small>");

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateTimeFormatter dateFormatterPamm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<Pamm> load() throws IOException {
        logger.info("Download all Alfa forex managers");
        List<Pamm> pamms = new ArrayList<>();

        //we have to login first
        CsrfToken csrfLoginToken = getCsrfCookie();
        String _identity = getIdentity(csrfLoginToken);

        int page = 1;


        String response = loadPammsPerPage(_identity, page, csrfLoginToken);

        Set<String> ids = new HashSet<>();
        //we load block, each includes resultsPerPage managers
        while (StringUtils.isNotEmpty(response)) {

            Matcher managersMather = MANAGER_PATTERN.matcher(response);
            //<tr data-key="24"><td>1</td><td>120734</td><td><a type="button" href="/public/pamm/view/2619" onclick="return printProfile($(this).prop(&quot;href&quot;), 1024, 768);" data-pjax="0">BullsandBearsUSD</a></td><td>612 <small>дн.</small></td><td><span class='decimal-delim'>4119095.22</span></td><td>22.09%</td></tr>
            //let's overwork manages one by one
              while (managersMather.find()) {
                try {
                    String managerBlock = managersMather.group(1).replaceAll("RUR", "RUB");

                    String id = getMatchValue(ID_PATTERN, managerBlock);
                    if (!ids.add(id)) {
                        //if idss areduplicate, we have to stop loading them
                        response = null;
                        break;
                    }

                    logger.info("Start load AlfaForex manager details, pammId = {}", id);

                    String name = getMatchValue(NAME_PATTERN, managerBlock);
                    Integer ageInDays = Integer.valueOf(getMatchValue(AGE_PATTERN, managerBlock));

                    Pamm pamm = new AlfaForexPamm();
                    pamm.setPammBroker(PammBroker.ALFA_FOREX);
                    pamm.setId(id);
                    pamm.setAgeInDays(ageInDays);


                    logger.info("load manager money, pammId = {}", pamm.getId());

                    LocalDate now = LocalDate.now();
                    LocalDate yesterday = now.minusDays(1);
                    LocalDate firstWorkDate = now.minusDays(pamm.getAgeInDays());

                    String yesterdayText = yesterday.format(dateFormatter);

                    String firstWorkDateText = firstWorkDate.format(dateFormatter);

                    //let's load changes
                    PammAlfaForexManagerMoney pammAlfaForexManagerMoney = createHttpURLConnectionForSecuredPost
                            ("https://my.alfaforex.com/pamm/account-public/chart-data?id=" + id + "&type=profitChart", "dateFrom=" + firstWorkDateText + "&dateTo=" + yesterdayText, csrfLoginToken, _identity, PammAlfaForexManagerMoney.class);

                    //let's load deposit load
                    PammAlfaForexManagerMoney pammAlfaForexdeDepositLoad = createHttpURLConnectionForSecuredPost
                            ("https://my.alfaforex.com/pamm/account-public/chart-data?id=" + id + "&type=loadingChart", "dateFrom=" + firstWorkDateText + "&dateTo=" + yesterdayText, csrfLoginToken, _identity, PammAlfaForexManagerMoney.class);
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
                        LocalDate localDate = LocalDate.parse(resultItem.getDate(), dateFormatterPamm);

                        changes.add(new DailyChange(localDate, change));
                        previousValue[0] = resultItem.getValue();
                    }

                    Double avgChange = addChangesToForexTrades(changes, pamm);

                    //load commissions
                    Document doc = Jsoup.connect("https://my.alfaforex.com/public/pamm/view/" + id + "#offer").timeout(30000).get();
                    Element offerForm = doc.getElementsByClass("offer").get(0).getElementsByClass("form-group").get(0);

                    Element percentageElement = offerForm.getElementsByClass("pull-left").size() > 0 ?
                            offerForm.getElementsByClass("pull-left").get(0) :
                            offerForm.getElementsByTag("td").get(0);
                    Double percentage = Double.valueOf(percentageElement.text().replace("%", ""));

                    Elements cells = doc.getElementById("w14-container").getElementsByTag("td");
                    Double minInvestment = 1.;
                    Double minPeriod = Double.valueOf(cells.get(2).text());


                    cells = doc.getElementsByClass("pull-left");

                    String totalMoneyStr = cells.get(7).text();
                    totalMoneyStr = totalMoneyStr.split(" ")[0];
                    Double totalMoney = Double.valueOf(totalMoneyStr);
                    pamm.setTotalMoney(totalMoney);
                    Currency currency = Currency.valueOf(cells.get(5).text().toUpperCase());
                    String managerMoneyStr = cells.get(8).text();
                    managerMoneyStr = managerMoneyStr.split(" ")[0];
                    pamm.setManagerMoney(Double.valueOf(managerMoneyStr));


                    pamm.addOffer(new InvestmentTargetOffer(name, minInvestment, null, minPeriod, null, percentage, "https://my.alfaforex.com/public/pamm/view/" + pamm.getId() + "?partner_id=719755",
                            currency, avgChange, new PammOfferRisk(), new ForexOfferProfit()));

                    pamms.add(pamm);

                    logger.info("Finish load AlfaForex manager details, pammId = {}", id);
                } catch (Exception e) {
                    logger.error("Impossible to load AlfaForexPamm", e);
                }

            }
            if (StringUtils.isNotEmpty(response)) {
                page++;
                response = loadPammsPerPage(_identity, page, csrfLoginToken);
            }
        }
        filterUselessForexAccounts(pamms);
        logger.info("Download all Alfa forex managers Done");
        return pamms;
    }

    private CsrfToken getCsrfCookie() throws IOException {
        URL urlManagerMoney = new URL("https://my.alfaforex.com/ru/login");
        HttpURLConnection conManagerMoney = (HttpURLConnection) urlManagerMoney.openConnection();

        conManagerMoney.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        //conManagerMoney.setRequestProperty("Accept-Encoding","gzip, deflate, br");
        conManagerMoney.setRequestProperty("Accept-Language", "en-US,en;q=0.8,ru;q=0.6,uk;q=0.4");
        conManagerMoney.setRequestProperty("Cache-Control", "max-age=0");
        conManagerMoney.setRequestProperty("Connection", "keep-alive");
        conManagerMoney.setRequestProperty("Content-Length", "172");
        //conManagerMoney.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        conManagerMoney.setRequestProperty("Host", "my.alfaforex.com");
        conManagerMoney.setRequestProperty("Origin", "https://my.alfaforex.com");
        conManagerMoney.setRequestProperty("Referer", "https://my.alfaforex.com/index/login");
        conManagerMoney.setRequestProperty("Upgrade-Insecure-Requests", "1");
        conManagerMoney.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");


        //add request header
        conManagerMoney.setReadTimeout(30000);
        conManagerMoney.setConnectTimeout(30000);


        List<String> cookies = conManagerMoney.getHeaderFields().get("Set-Cookie");
        CsrfToken csrfToken = new CsrfToken();
        //get cookie
        for (String cookie : cookies) {
            if (cookie.startsWith("_csrf")) {
                int end = cookie.indexOf("; path");
                csrfToken.csrfCookie = cookie.substring(6, end);
            }
            if (cookie.startsWith("FRONTENDSESSID")) {
                int end = cookie.indexOf("; path");
                csrfToken.FRONTENDSESSID = cookie.substring(15, end);
            }

        }


        String response = IOUtils.toString(conManagerMoney.getInputStream(), "UTF-8");
        csrfToken.csrfHiddenValue = getMatchValue(CSRF_HIDDEN_TOKEN_PATTERN, response);
        return csrfToken;
    }

    private static class CsrfToken {
        public String csrfCookie;
        public String csrfHiddenValue;

        public String FRONTENDSESSID;

    }

    private String getIdentity(CsrfToken csrfToken) throws IOException {
        URL urlManagerMoney = new URL("https://my.alfaforex.com/ru/login");
        HttpURLConnection conManagerMoney = (HttpURLConnection) urlManagerMoney.openConnection();
        conManagerMoney.setInstanceFollowRedirects(false);

        conManagerMoney.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conManagerMoney.setRequestProperty("Accept-Language", "en-US,en;q=0.8,ru;q=0.6,uk;q=0.4");
        conManagerMoney.setRequestProperty("Cache-Control", "max-age=0");
        conManagerMoney.setRequestProperty("Connection", "keep-alive");
        conManagerMoney.setRequestProperty("Content-Length", "172");
        conManagerMoney.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conManagerMoney.setRequestProperty("cookie", "_csrf=" + csrfToken.csrfCookie);
        conManagerMoney.setRequestProperty("Host", "my.alfaforex.com");
        conManagerMoney.setRequestProperty("Origin", "https://my.alfaforex.com");
        conManagerMoney.setRequestProperty("Referer", "https://my.alfaforex.com/index/login");
        conManagerMoney.setRequestProperty("Upgrade-Insecure-Requests", "1");
        conManagerMoney.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        //add request header
        conManagerMoney.setReadTimeout(30000);
        conManagerMoney.setConnectTimeout(30000);


        conManagerMoney.setRequestMethod("POST");


        conManagerMoney.setDoInput(true);
        conManagerMoney.setDoOutput(true);


        DataOutputStream wr = new DataOutputStream(conManagerMoney.getOutputStream());
        wr.writeBytes("_csrf=" + URLEncoder.encode(csrfToken.csrfHiddenValue, "UTF-8") + "&language=%2Fru%2Flogin&LoginForm%5Busername%5D=1099820&LoginForm%5Bpassword%5D=LiaDmi505252&login-button=");
        wr.flush();
        wr.close();

        List<String> cookies = conManagerMoney.getHeaderFields().get("Set-Cookie");
        //get cookie
        for (String cookie : cookies) {
            if (cookie.startsWith("_identity")) {
                return cookie;
            }
        }
        return null;


    }

    private String loadPammsPerPage(String _identity, Integer page, CsrfToken csrfLoginToken) throws IOException {
        URL urlAll = new URL("https://my.alfaforex.com/pamm/account/rating?sort=position&page=" + page + "&_pjax=%23w3");
        HttpURLConnection con = (HttpURLConnection) urlAll.openConnection();
        //add request header. Without this header we will get full page, not just required part
        con.setRequestProperty("Accept", "text/html, */*; q=0.01");
        //con.setRequestProperty("Accept-Encoding","gzip, deflate, sdch, br");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.8,ru;q=0.6,uk;q=0.4");
        con.setRequestProperty("Connection", "keep-alive");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        con.setRequestProperty("Cookie", _identity);
        con.setRequestProperty("Host", "my.alfaforex.com");
        con.setRequestProperty("Referer", "https://my.alfaforex.com/pamm/account/rating?sort=position&page=9");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        con.setRequestProperty("X-CSRF-Token", csrfLoginToken.csrfHiddenValue);
        con.setRequestProperty("X-PJAX", "true");
        con.setRequestProperty("X-PJAX-Container", "#w3");
        con.setRequestProperty("X-Requested-With", "XMLHttpRequest");

        String response = IOUtils.toString(con.getInputStream(), "UTF-8");
        return response;
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.ALFA_FOREX;
    }

    /**
     * Loads data from specified url, using post method and returns java object as a result
     *
     * @param url            url
     * @param urlParameters  list of params. params are sent as form params
     * @param csrfLoginToken data taken during log in
     * @param _identity      user identity
     * @param resultClass    result class
     * @return object of result class
     * @throws IOException
     */
    private <T> T createHttpURLConnectionForSecuredPost(String url, String urlParameters, CsrfToken csrfLoginToken, String _identity, Class<T> resultClass) throws IOException {
        URL urlManagerMoney = new URL(url);
        HttpURLConnection conManagerMoney = (HttpURLConnection) urlManagerMoney.openConnection();
        //add request header

        conManagerMoney.setRequestProperty("Accept", "*/*");
        //conManagerMoney.setRequestProperty("Accept-Encoding","gzip, deflate, br");
        conManagerMoney.setRequestProperty("Accept-Language", "en-US,en;q=0.8,ru;q=0.6,uk;q=0.4");
        conManagerMoney.setRequestProperty("Connection", "keep-alive");
        conManagerMoney.setRequestProperty("Content-Length", "37");
        conManagerMoney.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        conManagerMoney.setRequestProperty("Cookie", " _csrf=" + csrfLoginToken.csrfCookie + "; " + _identity);
        conManagerMoney.setRequestProperty("Host", "my.alfaforex.com");
        conManagerMoney.setRequestProperty("Origin", "https://my.alfaforex.com");
        conManagerMoney.setRequestProperty("Referer", "https://my.alfaforex.com/public/pamm/view/2619");
        conManagerMoney.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        conManagerMoney.setRequestProperty("X-CSRF-Token", csrfLoginToken.csrfHiddenValue);
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
