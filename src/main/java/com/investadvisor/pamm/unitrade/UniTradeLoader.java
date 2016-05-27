package com.investadvisor.pamm.unitrade;

import com.investadvisor.Currency;
import com.investadvisor.model.Pamm;
import com.investadvisor.model.PammBroker;
import com.investadvisor.model.PammLoader;
import com.investadvisor.pamm.unitrade.model.InvestmentPlan;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * https://uni-trade.net/ is not a pumm, but we can calcualte risk as for PUMM
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class UniTradeLoader extends PammLoader {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final List<InvestmentPlan> investmentPlans = new ArrayList<>();

    static {
        investmentPlans.add(new InvestmentPlan("Start", 30, 9.5, 1, 25.));//
        investmentPlans.add(new InvestmentPlan("Index", 91, 12., 3, 100.));
        investmentPlans.add(new InvestmentPlan("Gold", 182, 14., 6, 500.));
        investmentPlans.add(new InvestmentPlan("Top1", 365, 16.5, 12, 1000.));
    }

    private static UniTradeLoader instance = new UniTradeLoader();

    //the way to make it singleton. When we add Guice, we should update this code to remove instance
    public static UniTradeLoader getInstance() {
        return instance;
    }

    private UniTradeLoader() {
    }

    @Override
    public List<Pamm> load() throws IOException {
        List<Pamm> pamms = new ArrayList<>();
        for (InvestmentPlan investmentPlan : investmentPlans) {
            Pamm pamm = new Pamm();
            pamm.setPammBroker(PammBroker.UNI_TRADE);
            LocalDate now = LocalDate.now();

            String startDateStr = "12.02.2016";
            LocalDate localDate = LocalDate.parse(startDateStr, dateFormatter);
            pamm.setAgeInDays((int) ChronoUnit.DAYS.between(localDate, now));
            pamm.setTotalMoney(1500000.);//asked their support
            pamm.setManagerMoney(pamm.getTotalMoney() / 10);  //we can't get more accirate data

            pamm.setCurrency(Currency.USD);

            pamm.setName(investmentPlan.getName());
            pamm.setId(String.valueOf(pamms.size() + 1));
            PammCommissionUniTrade pammCommission = new PammCommissionUniTrade(investmentPlan.getMinimalInvestment(), investmentPlan.getDays(), 0., investmentPlan);
            pamm.addCommission(pammCommission);
            pamm.setLossDaysPercentage(5.); //we can't get more accurate data
            pamm.setMaxDailyLoss(5.); //we can't get more accurate data
            pamm.setAverageDailyLoss(2.5);   //we can't get more accurate data
            pamm.setDeviation(10.);   //we can't get more accurate data
            pamm.setAvgChange(investmentPlan.getPercentagePerMonth() / 30.4 / 100);
            pamms.add(pamm);
        }
        return pamms;
    }
}
