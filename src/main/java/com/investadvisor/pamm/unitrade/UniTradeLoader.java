package com.investadvisor.pamm.unitrade;

import com.investadvisor.Currency;
import com.investadvisor.model.pamm.Pamm;
import com.investadvisor.model.pamm.PammBroker;
import com.investadvisor.model.pamm.PammLoader;
import com.investadvisor.pamm.unitrade.model.UniTradePamm;
import com.investadvisor.pamm.unitrade.model.UnitradeInvestmentPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * https://uni-trade.net/ is not a pamm, but we can calculate risk as for PUMM
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class UniTradeLoader extends PammLoader {

    private static final Logger logger = LoggerFactory.getLogger(UniTradeLoader.class);
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final List<UnitradeInvestmentPlan> UNITRADE_INVESTMENT_PLANS = new ArrayList<>();

    static {
        String link = "https://uni-trade.net/registration?par=dima";
        UNITRADE_INVESTMENT_PLANS.add(new UnitradeInvestmentPlan("Start", 30., 9.5, 1, 25., link));
        UNITRADE_INVESTMENT_PLANS.add(new UnitradeInvestmentPlan("Index", 91., 12., 3, 100., link));
        UNITRADE_INVESTMENT_PLANS.add(new UnitradeInvestmentPlan("Gold", 182., 14., 6, 500., link));
        UNITRADE_INVESTMENT_PLANS.add(new UnitradeInvestmentPlan("Top1", 365., 16.5, 12, 1000., link));
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
        logger.info("Finish download all offers for Unitrade");
        List<Pamm> pamms = new ArrayList<>();
        for (UnitradeInvestmentPlan unitradeInvestmentPlan : UNITRADE_INVESTMENT_PLANS) {
            Pamm pamm = new UniTradePamm();
            pamm.setPammBroker(PammBroker.UNI_TRADE);
            LocalDate now = LocalDate.now();

            String startDateStr = "12.02.2016";
            LocalDate localDate = LocalDate.parse(startDateStr, dateFormatter);
            pamm.setAgeInDays((int) ChronoUnit.DAYS.between(localDate, now));
            pamm.setTotalMoney(1500000.);//asked their support
            pamm.setManagerMoney(pamm.getTotalMoney() / 10);  //we can't get more accirate data

            pamm.setId(String.valueOf(pamms.size() + 1));
            PammOfferUniTrade pammOffer = new PammOfferUniTrade(unitradeInvestmentPlan);
            pamm.addOffer(pammOffer);
            pamm.setLossDaysPercentage(5.); //we can't get more accurate data
            pamm.setMaxDailyLoss(5.); //we can't get more accurate data
            pamm.setAverageDailyLoss(2.5);   //we can't get more accurate data
            pamm.setDeviation(10.);   //we can't get more accurate data
            pamms.add(pamm);
        }
        logger.info("Finish download all offers for Insolt, pamms = {}", pamms);
        return pamms;
    }
}
