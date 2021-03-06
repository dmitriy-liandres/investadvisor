package com.toolformoney.investarget.hyip.itcTravel;

import com.toolformoney.investarget.hyip.itcTravel.model.ItcTravel;
import com.toolformoney.investarget.hyip.itcTravel.model.ItcTravelInvestmentPlan;
import com.toolformoney.investarget.hyip.itcTravel.model.OfferItcTravel;
import com.toolformoney.model.InvestmentTargetLoader;
import com.toolformoney.model.InvestmentTypeName;
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
        final String link = "https://itc-travel.biz/investor/investment-offer/?id=688";
        ITC_TRAVEL_INVESTMENT_PLANs = new ArrayList<>();
        ITC_TRAVEL_INVESTMENT_PLANs.add(new ItcTravelInvestmentPlan("PREMIER", 9., 0., false, 10., link));
        ITC_TRAVEL_INVESTMENT_PLANs.add(new ItcTravelInvestmentPlan("GRAND", 12., 30., true, 50., link));
        ITC_TRAVEL_INVESTMENT_PLANs.add(new ItcTravelInvestmentPlan("DELUXE", 15., 90., true, 100., link));
        ITC_TRAVEL_INVESTMENT_PLANs.add(new ItcTravelInvestmentPlan("PREMIUM", 18., 180., true, 200., link));
        ITC_TRAVEL_INVESTMENT_PLANs.add(new ItcTravelInvestmentPlan("PRESIDENT", 21., 360., true, 500., link));

    }

    @Override
    public List<ItcTravel> load() throws IOException {
        logger.info("Start download all itc-travel.biz offers");
        List<ItcTravel> hyips = new ArrayList<>();
        for (ItcTravelInvestmentPlan itcTravelInvestmentPlan : ITC_TRAVEL_INVESTMENT_PLANs) {
            //add offer in usd


            ItcTravel itcTravel = new ItcTravel();

            OfferItcTravel offerItcTravel = new OfferItcTravel(itcTravelInvestmentPlan);
            itcTravel.addOffer(offerItcTravel);
            hyips.add(itcTravel);

        }
        logger.info("Finish download all itc-travel.biz offers");
        return hyips;
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return null;
    }
}

