package com.toolformoney.investarget.pamm.fxopen.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.pamm.PammOfferProfit;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class FxOpenPammOfferProfit extends PammOfferProfit {


    @Override
    public Double calculateProfitAfterMangerCommission(InvestmentTarget investmentTarget, InvestmentTargetOffer investmentTargetOffer, ProvidedParams providedParams) {
        PammOfferFxOpen pammOfferFxOpen = (PammOfferFxOpen) investmentTargetOffer;
        Double avgChange = investmentTargetOffer.getAvgChange() - pammOfferFxOpen.getAnnualMasterCommission() / 365;
        //pay attention, this value is not in percentage, it is in percentage/100;
        Double estimatedIncrease = Math.pow(1 + avgChange / 100, providedParams.getPeriodInDays()) - 1;
        Double finalIncreased = (1 + estimatedIncrease) * (1 - pammOfferFxOpen.getAssignmentCommissions() / 100);

        if (finalIncreased - 1 < pammOfferFxOpen.getMinimumPerformanceConstant() / 100) {
            return finalIncreased;
        } else {
            Double managerCommission = (finalIncreased - pammOfferFxOpen.getMinimumPerformanceConstant() / 100 - 1) * pammOfferFxOpen.getCommissionFromProfit() / 100;
            return (finalIncreased - managerCommission);
        }

    }
}
