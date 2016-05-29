package com.investadvisor.pamm.insolt;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.pamm.InvestmentTargetOffer;
import com.investadvisor.pamm.insolt.model.InsoltInvestmentPlan;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class PammOfferInsolt extends InvestmentTargetOffer {
    private InsoltInvestmentPlan insoltInvestmentPlan;

    public PammOfferInsolt(InsoltInvestmentPlan insoltInvestmentPlan) {
        super(insoltInvestmentPlan.getMinimalInvestment(), insoltInvestmentPlan.getDays(), insoltInvestmentPlan.getManagerCommission());
        this.insoltInvestmentPlan = insoltInvestmentPlan;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(InvestmentTarget pamm, ProvidedParams providedParams) {
        Double avgChangePerWeek = pamm.getAvgChange() * 7;
        //pay attention, this value is not in percentage, it is in percentage/100;
        Double estimatedIncrease = Math.pow(1 + avgChangePerWeek / 100, insoltInvestmentPlan.getDays() / 7) - 1;
        return 1 + estimatedIncrease * (1 - getCommissionFromProfit() / 100);
    }
}