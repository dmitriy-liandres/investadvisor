package com.toolformoney.investarget.pamm.privatefx;

import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.investarget.pamm.privatefx.model.PrivateFXPamm;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.pamm.*;
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
public class PrivateFXLoader extends PammLoader {

    private static final Logger logger = LoggerFactory.getLogger(PrivateFXLoader.class);
    //Feb 17, 2016, 12:49:12 AM
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy, H:mm:SS a");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");

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
                                    Double profitPerDate = Math.pow(Math.abs(profit), 0.2) * Math.signum(profit);

                                    String startWeekDateStr = details.get(3).text();
                                    if (!startWeekDateStrs.add(startWeekDateStr)) {
                                        isLoadedAllProfits = true;
                                        break;
                                    }
                                    LocalDate startWeekDate = LocalDate.parse(startWeekDateStr, dateFormatter);
                                    for (int i = 0; i < 5; i++) {
                                        changes.add(new DailyChange(startWeekDate.plusDays(1), profitPerDate));
                                    }
                                }
                            } else {
                                break;
                            }
                            pammChangesPage++;
                        }
                        Double avgChange = addChangesToPamm(changes, pamm);
                        pamm.addOffer(new InvestmentTargetOffer(name, minInvestment, null, minPeriod, null, commission, "https://privatefx.com/pamm/" + pamm.getId() + "?ref=121768",
                                Currency.USD, avgChange, new PammOfferRisk(), new PammOfferProfit()));

                        pamms.add(pamm);
                    } catch (Exception e) {
                        logger.error("Impossible to load pamm {} ({id = })", name, id, e);
                    }
                }
            } catch (Exception e) {
                logger.error("Impossible to load page {})", page, e);

            }
            page++;

        }

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

    private String loadAnyData(String url, boolean isAjax, int tryNumber) throws IOException, InterruptedException {
        URL urlAll = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlAll.openConnection();
        //add request header. Without this header we will get full page, not just required part
        con.setRequestProperty("Accept", "text/html, */*; q=0.01");
        con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        con.setRequestProperty("accept-language", "en-US,en;q=0.8,ru;q=0.6,uk;q=0.4");
        con.setRequestProperty("cookie", "__cfduid=d30cf046b9fd90bd8f7f1c33452f456d81471171130; _csrf=7f523ed37754d5e22bb60283e047d7d75ff812e6743e612b1900102cdc1fd3dba%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22ngEVODEGOgnAEmORRRvL0eygXvGzX9Vd%22%3B%7D; _ym_uid=1478515575637894999; _language=a5a597d711a44932aa3676bd25a912e7fd1b86c4153e111330879f8784f9e9efa%3A2%3A%7Bi%3A0%3Bs%3A9%3A%22_language%22%3Bi%3A1%3Bs%3A5%3A%22ru-RU%22%3B%7D; activity=0|-1; ref=7967b21d3fcb5ff2b4e0d21d4e96a58816f0f71d9d8692200a3364bdd791c7b2a%3A2%3A%7Bi%3A0%3Bs%3A3%3A%22ref%22%3Bi%3A1%3Bs%3A4%3A%224338%22%3B%7D; subid=72decc2a87abd2b7df0da24f1a59667cb1ed5bda8a63555e2aeb0def8eda56e8a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22subid%22%3Bi%3A1%3Bs%3A0%3A%22%22%3B%7D; doorway=67868f679aabab691af761ffc1649e47339456836ce783e0fc43b853aad19f65a%3A2%3A%7Bi%3A0%3Bs%3A7%3A%22doorway%22%3Bi%3A1%3Bs%3A22%3A%22https%3A%2F%2Fwww.google.by%2F%22%3B%7D; _identity=297a65565b49bbe8aadf2bfe8fca4ef50835659455ae5c70584cf4ec69e99f7da%3A2%3A%7Bi%3A0%3Bs%3A9%3A%22_identity%22%3Bi%3A1%3Bs%3A51%3A%22%5B121768%2C%22p5hdAGxoKSTJl8L-rwCjE_GPTm0aX2_P%22%2C2592000%5D%22%3B%7D; PHPSESSID=ii6iu9e1qcot5jol40rp2tk2q3; _ym_isad=2; _gat_UA-68411105-2=1; _identity=297a65565b49bbe8aadf2bfe8fca4ef50835659455ae5c70584cf4ec69e99f7da%3A2%3A%7Bi%3A0%3Bs%3A9%3A%22_identity%22%3Bi%3A1%3Bs%3A51%3A%22%5B121768%2C%22p5hdAGxoKSTJl8L-rwCjE_GPTm0aX2_P%22%2C2592000%5D%22%3B%7D; __utma=30976126.1951345668.1474298055.1478521540.1478760016.8; __utmb=30976126.8.10.1478760016; __utmc=30976126; __utmz=30976126.1478518808.6.5.utmcsr=my.privatefx.com|utmccn=(referral)|utmcmd=referral|utmcct=/pamm/details/2331; _ym_visorc_40576350=w; _ga=GA1.2.1951345668.1474298055");
        con.setRequestProperty("upgrade-insecure-requests", "1");
        con.setRequestProperty("referer", url);
        con.setRequestProperty("authority", "privatefx.com");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        if (isAjax) {
            con.setRequestProperty("x-requested-with", "XMLHttpRequest");
            con.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            con.setRequestProperty("x-pjax", "true");
            con.setRequestProperty("x-pjax-container", "#w1");
        }
        if (con.getResponseCode() != 200) {
            logger.warn("Impossible to load {}, isAjax = {}, tryNumber = {}", url, isAjax, tryNumber);
            if (tryNumber == 3) {
                throw new RuntimeException("Impossible to load " + url + ", isAjax = " + isAjax + ", tryNumber = " + tryNumber + ", con.getResponseCode() = " + con.getResponseCode());
            }
            Thread.sleep(10000);
            return loadAnyData(url, isAjax, tryNumber + 1);
        }
        String response = IOUtils.toString(con.getInputStream(), "UTF-8");
        return response;
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return InvestmentTypeName.PRIVATE_FX;
    }
}
