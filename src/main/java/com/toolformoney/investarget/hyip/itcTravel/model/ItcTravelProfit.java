package com.toolformoney.investarget.hyip.itcTravel.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.hyip.HyipOfferProfit;
import com.toolformoney.model.pamm.InvestmentTargetOffer;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class ItcTravelProfit extends HyipOfferProfit {
    private ItcTravelInvestmentPlan itcTravelInvestmentPlan;

    public ItcTravelProfit(ItcTravelInvestmentPlan itcTravelInvestmentPlan) {
        this.itcTravelInvestmentPlan = itcTravelInvestmentPlan;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(InvestmentTarget investmentTarget, InvestmentTargetOffer investmentTargetOffer, ProvidedParams providedParams) {
        //get periods number
        if (itcTravelInvestmentPlan.getIsDepositClosable()) {
            Long periodsNumber = Math.round(providedParams.getPeriodInDays() / itcTravelInvestmentPlan.getMinDays());
            //recapitalization can be done evey month
            //pay attention, this value is not in percentage, it is in percentage/100;
            Double estimatedIncrease = Math.pow(1 + itcTravelInvestmentPlan.getPercentsPerMonth() / 100, periodsNumber) - 1;
            return 1 + estimatedIncrease * (1 - investmentTargetOffer.getCommissionFromProfit() / 100);
        } else {
            Double avgChange = itcTravelInvestmentPlan.getPercentsPerMonth() / 30;

            //pay attention, this value is not in percentage, it is in percentage/100;
            Double estimatedIncrease = avgChange * providedParams.getPeriodInDays();
            return 1 + estimatedIncrease / 100;
        }
    }
}
