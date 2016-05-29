package com.investadvisor.hyip.itcTravel.model;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.pamm.InvestmentTargetOffer;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class OfferItcTravel extends InvestmentTargetOffer {
    private ItcTravelInvestmentPlan itcTravelInvestmentPlan;

    public OfferItcTravel(ItcTravelInvestmentPlan itcTravelInvestmentPlan) {
        super(itcTravelInvestmentPlan.getMinInvestment(), itcTravelInvestmentPlan.getMinDays(), 0.);
        this.itcTravelInvestmentPlan = itcTravelInvestmentPlan;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(InvestmentTarget investmentTarget, ProvidedParams providedParams) {
        //get periods number
        if (itcTravelInvestmentPlan.getIsDepositClosed()) {
            Long periodsNumber = Math.round(providedParams.getPeriodInDays() / itcTravelInvestmentPlan.getMinDays());
            //recapitalization can be done evey month
            //pay attention, this value is not in percentage, it is in percentage/100;
            Double estimatedIncrease = Math.pow(1 + itcTravelInvestmentPlan.getPercentsPerMonth() / 100, periodsNumber) - 1;
            return 1 + estimatedIncrease * (1 - getCommissionFromProfit() / 100);
        } else {
            Double avgChange = itcTravelInvestmentPlan.getPercentsPerMonth() / 30;

            //pay attention, this value is not in percentage, it is in percentage/100;
            Double estimatedIncrease = avgChange * providedParams.getPeriodInDays();
            return 1 + estimatedIncrease / 100;
        }
    }
}