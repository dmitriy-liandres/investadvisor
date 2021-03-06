package com.toolformoney.investarget.hyip.itcTravel.model;

import com.toolformoney.Currency;
import com.toolformoney.model.pamm.InvestmentTargetOffer;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class OfferItcTravel extends InvestmentTargetOffer {
    private ItcTravelInvestmentPlan itcTravelInvestmentPlan;

    public OfferItcTravel(ItcTravelInvestmentPlan itcTravelInvestmentPlan) {
        super(itcTravelInvestmentPlan.getName(), itcTravelInvestmentPlan.getMinInvestment(), null, itcTravelInvestmentPlan.getMinDays(), null, 0., itcTravelInvestmentPlan.getLink(), Currency.USD, itcTravelInvestmentPlan.getAvgPercentage(), new ItcTravelOfferRisk(), new ItcTravelProfit(itcTravelInvestmentPlan));
        this.itcTravelInvestmentPlan = itcTravelInvestmentPlan;
    }

    @Override
    public String toString() {
        return "OfferItcTravel{" +
                "itcTravelInvestmentPlan=" + itcTravelInvestmentPlan +
                "} " + super.toString();
    }
}