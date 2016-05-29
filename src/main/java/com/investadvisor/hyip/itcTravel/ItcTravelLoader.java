package com.investadvisor.hyip.itcTravel;

import com.investadvisor.Currency;
import com.investadvisor.hyip.itcTravel.model.ItcTravel;
import com.investadvisor.hyip.itcTravel.model.ItcTravelInvestmentPlan;
import com.investadvisor.hyip.itcTravel.model.ItcTravelRisk;
import com.investadvisor.hyip.itcTravel.model.OfferItcTravel;
import com.investadvisor.model.InvestmentTargetLoader;
import com.investadvisor.model.hyip.HyipProfit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://itc-travel.biz
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class ItcTravelLoader extends InvestmentTargetLoader<ItcTravel> {

    private static final Logger logger = LoggerFactory.getLogger(ItcTravelLoader.class);

    private static final List<ItcTravelInvestmentPlan> ITC_TRAVEL_INVESTMENT_PLANs;

    static {
        ITC_TRAVEL_INVESTMENT_PLANs = new ArrayList<>();
        ITC_TRAVEL_INVESTMENT_PLANs.add(new ItcTravelInvestmentPlan("PREMIER", 9., 0, false, 10.));
        ITC_TRAVEL_INVESTMENT_PLANs.add(new ItcTravelInvestmentPlan("GRAND", 12., 30, true, 50.));
        ITC_TRAVEL_INVESTMENT_PLANs.add(new ItcTravelInvestmentPlan("DELUXE", 15., 90, true, 100.));
        ITC_TRAVEL_INVESTMENT_PLANs.add(new ItcTravelInvestmentPlan("PREMIUM", 18., 180, true, 200.));
        ITC_TRAVEL_INVESTMENT_PLANs.add(new ItcTravelInvestmentPlan("PRESIDENT", 21., 360, true, 500.));

    }

    private static ItcTravelLoader instance = new ItcTravelLoader();

    //the way to make it singleton. When we add Guice, we should update this code to remove instance
    public static ItcTravelLoader getInstance() {
        return instance;
    }

    private ItcTravelLoader() {
    }

    @Override
    public List<ItcTravel> load() throws IOException {
        logger.info("Start download all itc-travel.biz offers");
        List<ItcTravel> hyips = new ArrayList<>();
        for (ItcTravelInvestmentPlan itcTravelInvestmentPlan : ITC_TRAVEL_INVESTMENT_PLANs) {
            //add offer in usd

            Double avgPercentage = (Math.pow((1 + itcTravelInvestmentPlan.getPercentsPerMonth() / 100), 1. / 30) - 1) * 100;

            ItcTravel itcTravel = new ItcTravel(new ItcTravelRisk(), new HyipProfit());
            itcTravel.setAvgChange(avgPercentage);
            itcTravel.setCurrency(Currency.USD);

            OfferItcTravel offerItcTravel = new OfferItcTravel(itcTravelInvestmentPlan);
            itcTravel.addOffer(offerItcTravel);
            hyips.add(itcTravel);

        }
        logger.info("Finish download all itc-travel.biz offers");
        return hyips;
    }
}

