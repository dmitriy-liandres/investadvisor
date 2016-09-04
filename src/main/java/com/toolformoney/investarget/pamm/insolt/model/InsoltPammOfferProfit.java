package com.toolformoney.investarget.pamm.insolt.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.pamm.PammOfferProfit;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class InsoltPammOfferProfit extends PammOfferProfit {

    private InsoltInvestmentPlan insoltInvestmentPlan;

    public InsoltPammOfferProfit(InsoltInvestmentPlan insoltInvestmentPlan) {
        this.insoltInvestmentPlan = insoltInvestmentPlan;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(InvestmentTarget investmentTarget, InvestmentTargetOffer investmentTargetOffer, ProvidedParams providedParams) {
        Double avgChangePerWeek = investmentTargetOffer.getAvgChange() * 7;
        //recapitalization is done evey week
        //pay attention, this value is not in percentage, it is in percentage/100;
        Double estimatedIncrease = Math.pow(1 + avgChangePerWeek / 100, insoltInvestmentPlan.getDays() / 7) - 1;
        return 1 + estimatedIncrease * (1 - investmentTargetOffer.getCommissionFromProfit() / 100);
    }

    @Override
    public String toString() {
        return "InsoltPammOfferProfit{" +
                "insoltInvestmentPlan=" + insoltInvestmentPlan +
                "} " + super.toString();
    }
}
