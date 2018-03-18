package com.toolformoney.investarget.pamm.privatefx;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.investarget.pamm.privatefx.model.PrivateFXPamm;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.forex.ForexLoader;
import com.toolformoney.model.forex.ForexOfferProfit;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.pamm.Pamm;
import com.toolformoney.model.pamm.PammBroker;
import com.toolformoney.model.pamm.PammOfferRisk;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Loads Pamms for https://privatefx.com/ratings_pamms
 * Author Dmitriy Liandres
 * Date 10.11.2016
 */
@Singleton
public class PrivateFXLoader extends ForexLoader {

    private static final Logger logger = LoggerFactory.getLogger(PrivateFXLoader.class);
    //Feb 17, 2016, 12:49:12 AM
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy, H:mm:SS a");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");

    private static PermissionKey permissionKey = null;

    private static void regeneratePermissionsKey() throws IOException {
        WebClient webClient = new WebClient();
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.waitForBackgroundJavaScript(10000);
        webClient.waitForBackgroundJavaScriptStartingBefore(10000);
        webClient.getCookieManager().setCookiesEnabled(true);
        HtmlPage page = webClient.getPage("https://privatefx.com/en-US/ratings_pamms");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            logger.error("Impossible to run timer", e);
        }
        permissionKey = new PermissionKey();
        for (Cookie cookie : webClient.getCookieManager().getCookies()) {
            if ("__cfduid".equals(cookie.getName())) {
                permissionKey.__cfduid = cookie.getValue();
            }
            if ("cf_clearance".equals(cookie.getName())) {
                permissionKey.cf_clearance = cookie.getValue();
            }
        }

    }

    private static class PermissionKey {
        public String __cfduid;
        public String cf_clearance;

    }

    @Override
    public List<Pamm> load() throws IOException {
        logger.info("Download all Privatefx managers");
        List<Pamm> pamms = new ArrayList<>();
        int page = 1;
        Set<String> ids = new HashSet<>();
        //String response = loadPammsPerPage(page);
        boolean isLoadedAllPamms = false;
        //we load block, each includes resultsPerPage managers
        while (!isLoadedAllPamms) {
            try {
                Document pammsDoc = Jsoup.parse(loadPammsPerPage(page));
                Elements pammsCells = pammsDoc.getElementById("w1").getElementsByClass("row");
                for (Element pammCell : pammsCells) {

                    String name = pammCell.getElementsByTag("h3").text();
                    String id = pammCell.getElementsByTag("a").get(0).text();
                    try {
                        logger.info("load Privatefx manager " + name + " (" + id + ")");
                        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(id)) {
                            continue;
                        }
                        if (!ids.add(id)) {
                            isLoadedAllPamms = true;
                            break;
                        }
                        Document pammDoc = Jsoup.parse(loadPamm(id));
                        Element pammBlock = pammDoc.getElementById("w0");
                        Elements values = pammBlock.getElementsByTag("td");

                        Pamm pamm = new PrivateFXPamm();
                        pamm.setPammBroker(PammBroker.PRIVATE_FX);
                        pamm.setId(id);

                        //check whether pass is alive
                        if (!values.get(2).text().equals("Open")) {
                            continue;
                        }


                        //Feb 17, 2016, 12:49:12 AM
                        String startDateStr = values.get(4).text();
                        LocalDate startDate = LocalDate.parse(startDateStr, dateTimeFormatter);
                        LocalDate now = LocalDate.now();
                        Long ageInDays = ChronoUnit.DAYS.between(startDate, now);
                        if(ageInDays < 30){
                            //too young
                            continue;
                        }
                        pamm.setAgeInDays(ageInDays.intValue());

                        String managerMoneyStr = values.get(5).text();
                        Double managerMoney = Double.valueOf(managerMoneyStr.replace(",", ""));
                        String totalInvestmentStr = values.get(7).text();
                        Double totalInvestment = Double.valueOf(totalInvestmentStr.replace(",", ""));

                        Double minInvestment = Double.valueOf(values.get(16).text());

                        Double minPeriod = 7 * Double.valueOf(values.get(3).text());

                        Double commission = Double.valueOf(values.get(13).text().replace("%", "").trim());

                        pamm.setManagerMoney(managerMoney);
                        pamm.setTotalMoney(managerMoney + totalInvestment);

                        List<DailyChange> changes = new ArrayList<>();
                        Integer pammChangesPage = 1;
                        //we use this set to check whether we already loaded all data
                        Set<String> startWeekDateStrs = new HashSet<>();
                        boolean isLoadedAllProfits = false;
                        while (!isLoadedAllProfits) {

                            Document pammChangesDoc = Jsoup.parse(loadPammChanges(id, pammChangesPage));
                            Elements pammPerWeek = pammChangesDoc.getElementsByTag("tr");
                            if (CollectionUtils.isNotEmpty(pammPerWeek) && pammPerWeek.size() > 1) {
                                for (int line = 1; line < pammPerWeek.size(); line++) {
                                    Elements details = pammPerWeek.get(line).getElementsByTag("td");
                                    if (details.size() < 4) {
                                        isLoadedAllProfits = true;
                                        break;
                                    }
                                    String profitStr = details.get(2).text();
                                    Double profit = Double.valueOf(profitStr.replace("%", "").replace(",", ".").replaceAll(" ", ""));
                                    Double profitPerDate = (Math.pow(Math.abs(1 + profit / 100), 0.2) - 1) * 100;

                                    String startWeekDateStr = details.get(3).text();
                                    if (!startWeekDateStrs.add(startWeekDateStr)) {
                                        isLoadedAllProfits = true;
                                        break;
                                    }
                                    LocalDate startWeekDate = LocalDate.parse(startWeekDateStr, dateFormatter);
                                    for (int i = 0; i < 5; i++) {
                                        changes.add(new DailyChange(startWeekDate.plusDays(i), profitPerDate));
                                    }
                                }
                            } else {
                                break;
                            }
                            pammChangesPage++;
                        }
                        Double avgChange = addChangesToForexTrades(changes, pamm);
                        pamm.addOffer(new InvestmentTargetOffer(name, minInvestment, null, minPeriod, null, commission, "https://privatefx.com/pamm/" + pamm.getId() + "?ref=121768",
                                Currency.USD, avgChange, new PammOfferRisk(), new ForexOfferProfit()));

                        pamms.add(pamm);
                    } catch (Exception e) {
                        logger.error("Impossible to load pamm {} ({id = })", name, id, e);
                    }
                }
            } catch (Exception e) {
                logger.error("Impossible to load page {})", page, e);

            }
            page++;
            //currently there are only 13 pages, 30 is enough for future
            if (page > 25) {
                break;
            }

        }

        filterUselessForexAccounts(pamms);
        logger.info("Downloaded all Privatefx managers");
        return pamms;
    }

    private String loadPammsPerPage(Integer page) throws IOException, InterruptedException {
        String url = "https://privatefx.com/en-US/ratings_pamms?page=" + page;
        return loadAnyData(url, false, 1);
    }

    private String loadPamm(String id) throws IOException, InterruptedException {
        String url = "https://privatefx.com/en-US/pamm/" + id;
        return loadAnyData(url, false, 1);
    }

    private String loadPammChanges(String id, Integer page) throws IOException, InterruptedException {
        String url = "https://privatefx.com/en-US/pamm/" + id + "?page=" + page + "&_pjax=%23w1&_pjax=%23w1";
        return loadAnyData(url, true, 1);
    }

    private HttpURLConnection generateConnection(String url, boolean isAjax) throws IOException, InterruptedException {
        Thread.sleep(1000);
        URL urlAll = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlAll.openConnection();
        //add request header. Without this header we will get full page, not just required part
        con.setRequestProperty("authority", "privatefx.com");
        con.setRequestProperty("method", "GET");
        con.setRequestProperty("path", "/en-US/ratings_pamms?page=1");
        //con.setRequestProperty("scheme", "https");
        con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        //con.setRequestProperty("accept-encoding:gzip, deflate, sdch, br");
        con.setRequestProperty("accept-language", "en-US,en;q=0.8,ru;q=0.6,uk;q=0.4");
        if (permissionKey != null) {
            con.setRequestProperty("cookie", "activity=0|40; __cfduid=" + permissionKey.__cfduid + "; cf_clearance=" + permissionKey.cf_clearance);
        }
        con.setRequestProperty("referer", "https://privatefx.com/en-US/ratings_pamms?page=1");
        con.setRequestProperty("upgrade-insecure-requests", "1");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36");


        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);
        if (isAjax) {
            con.setRequestProperty("x-requested-with", "XMLHttpRequest");
            con.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            con.setRequestProperty("x-pjax", "true");
            con.setRequestProperty("x-pjax-container", "#w1");
        }
        return con;
    }

    private String loadAnyData(String url, boolean isAjax, int tryNumber) throws IOException, InterruptedException {
        HttpURLConnection con = generateConnection(url, isAjax);
        if (con.getResponseCode() != 200) {
            logger.warn("Impossible to load {}, isAjax = {}, tryNumber = {}", url, isAjax, tryNumber);
            if (tryNumber == 3) {
                regeneratePermissionsKey();
            } else if (tryNumber == 6) {
                throw new RuntimeException("Impossible to load " + url + ", isAjax = " + isAjax + ", tryNumber = " + tryNumber + ", con.getResponseCode() = " + con.getResponseCode());
            } else {
                Thread.sleep(5000);
            }

            return loadAnyData(url, isAjax, tryNumber + 1);
        }
        String response = IOUtils.toString(con.getInputStream(), "UTF-8");
        return response;
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return null;//InvestmentTypeName.PRIVATE_FX;
    }
}
