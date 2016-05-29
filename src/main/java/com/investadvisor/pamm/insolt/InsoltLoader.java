package com.investadvisor.pamm.insolt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.investadvisor.Currency;
import com.investadvisor.model.pamm.Pamm;
import com.investadvisor.model.pamm.PammBroker;
import com.investadvisor.model.pamm.PammLoader;
import com.investadvisor.pamm.insolt.model.InsoltGraphData;
import com.investadvisor.pamm.insolt.model.InsoltInvestmentPlan;
import com.investadvisor.pamm.insolt.model.InsoltPamm;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * insolt.com  is not a pamm, but we can calculate risk as for PUMM
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class InsoltLoader extends PammLoader {

    private static final Logger logger = LoggerFactory.getLogger(InsoltLoader.class);
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final List<InsoltInvestmentPlan> INSOLT_INVESTMENT_PLANS = new ArrayList<>();

    //fetches income
    Pattern INCOME_PATTERN = Pattern.compile(".*?AmCharts.makeChart\\(\"chartdiv3\".*?dataProvider\":(.*?)\\].*?", Pattern.DOTALL);

    static {
        INSOLT_INVESTMENT_PLANS.add(new InsoltInvestmentPlan("������ ����� (CFD)/������ 1", 91, 40., 10, 100., "01.08.2015"));
        INSOLT_INVESTMENT_PLANS.add(new InsoltInvestmentPlan("������ ����� (CFD)/������ 2", 182, 30., 10, 100., "01.08.2015"));
        INSOLT_INVESTMENT_PLANS.add(new InsoltInvestmentPlan("������ ����� (CFD)/������ 3", 357, 20., 10, 100., "01.08.2015"));

        INSOLT_INVESTMENT_PLANS.add(new InsoltInvestmentPlan("�� ������ (CFD)/������ 1", 28, 35., 20, 200., "01.08.2015"));
        INSOLT_INVESTMENT_PLANS.add(new InsoltInvestmentPlan("�� ������ (CFD)/������ 2", 91, 30., 20, 200., "01.08.2015"));
        INSOLT_INVESTMENT_PLANS.add(new InsoltInvestmentPlan("�� ������ (CFD)/������ 3", 182, 25., 20, 200., "01.08.2015"));

        INSOLT_INVESTMENT_PLANS.add(new InsoltInvestmentPlan("������ ����� (�������� �����)/������ 1", 91, 30., 30, 500., "19.09.2015"));
        INSOLT_INVESTMENT_PLANS.add(new InsoltInvestmentPlan("������ ����� (�������� �����)/������ 2", 182, 25., 30, 500., "19.09.2015"));
        INSOLT_INVESTMENT_PLANS.add(new InsoltInvestmentPlan("������ ����� (�������� �����)/������ 3", 357, 20., 30, 500., "19.09.2015"));
    }

    private static InsoltLoader instance = new InsoltLoader();

    //the way to make it singleton. When we add Guice, we should update this code to remove instance
    public static InsoltLoader getInstance() {
        return instance;
    }

    private InsoltLoader() {
    }

    @Override
    public List<Pamm> load() throws IOException {
        logger.info("Start download all offers for Insolt");
        List<Pamm> pamms = new ArrayList<>();

        //login
        URL urlLogin = new URL("https://personal.insolt.com/login/");
        HttpURLConnection conLogin = (HttpURLConnection) urlLogin.openConnection();
        conLogin.setReadTimeout(30000);
        conLogin.setConnectTimeout(30000);
        conLogin.setRequestMethod("POST");
        conLogin.setDoInput(true);
        conLogin.setDoOutput(true);


        DataOutputStream wr = new DataOutputStream(conLogin.getOutputStream());
        wr.writeBytes("LOGIN=%2B375295054862&PSW=2520486&a=make_us_auth");
        wr.flush();
        wr.close();
        List<String> cookies = conLogin.getHeaderFields().get("Set-Cookie");
        String phpSessionIdCookie = null;
        for (String cookie : cookies) {
            if (cookie.startsWith("PHPSESSID")) {
                phpSessionIdCookie = cookie;
                break;
            }
        }

        //load offers
        for (InsoltInvestmentPlan insoltInvestmentPlan : INSOLT_INVESTMENT_PLANS) {
            Pamm pamm = new InsoltPamm();
            pamm.setPammBroker(PammBroker.INSOLT);


            URL urlAll = new URL("https://personal.insolt.com/invest/detail.php?id=" + insoltInvestmentPlan.getInsoltId());
            HttpURLConnection con = (HttpURLConnection) urlAll.openConnection();
            con.setRequestProperty("cookie", phpSessionIdCookie);
            String response = IOUtils.toString(con.getInputStream(), "UTF-8");
            Matcher managersMather = INCOME_PATTERN.matcher(response);
            managersMather.find();
            String result = managersMather.group(1).replaceAll("\t", "").replaceAll("\n", "").replaceAll(",}", "}");
            StringBuilder b = new StringBuilder(result);
            b.replace(result.lastIndexOf(","), result.lastIndexOf(",") + 1, "]");
            List<InsoltGraphData> insoltGraphData = objectMapper.readValue(b.toString(), new TypeReference<List<InsoltGraphData>>() {
            });

            ///get total money
            Document doc = Jsoup.parse(response);
            Elements combinedValues = doc.getElementsByClass("calc-item");
            String totalMoneyText = combinedValues.get(combinedValues.size() - 1).getElementsByTag("strong").text();
            totalMoneyText = totalMoneyText.replaceAll("USD", "").replaceAll(" ", "");

            LocalDate now = LocalDate.now();

            String startDateStr = insoltInvestmentPlan.getStartDate();
            LocalDate localDate = LocalDate.parse(startDateStr, dateFormatter);
            pamm.setAgeInDays((int) ChronoUnit.DAYS.between(localDate, now));
            pamm.setTotalMoney(Double.valueOf(totalMoneyText));
            pamm.setManagerMoney(pamm.getTotalMoney() / 10);  //we can't get more accurate data
            pamm.setCurrency(Currency.USD);
            pamm.setName(insoltInvestmentPlan.getName());
            pamm.setId(String.valueOf(pamms.size() + 1));
            PammOfferInsolt pammOffer = new PammOfferInsolt(insoltInvestmentPlan);
            pamm.addOffer(pammOffer);

            List<Double> changes = new ArrayList<>();
            Double previousValue = 0.;
            Double totalIncreaseInPercents = 0.;
            for (InsoltGraphData insoltGraphDataOneWeek : insoltGraphData) {
                Double change = (insoltGraphDataOneWeek.getValue() - previousValue) * 100 / (previousValue + 100);
                changes.add(change);
                previousValue = insoltGraphDataOneWeek.getValue();
                totalIncreaseInPercents = insoltGraphDataOneWeek.getValue();

            }
            addChangesToPamm(changes, totalIncreaseInPercents, pamm);

            pamms.add(pamm);
        }
        logger.info("Finish download all offers for Insolt, pamms = {}", pamms);
        return pamms;
    }
}
