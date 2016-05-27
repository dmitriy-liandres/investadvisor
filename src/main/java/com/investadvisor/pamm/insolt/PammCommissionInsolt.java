package com.investadvisor.pamm.insolt;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.Pamm;
import com.investadvisor.model.PammCommission;
import com.investadvisor.pamm.insolt.model.InsoltInvestmentPlan;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class PammCommissionInsolt extends PammCommission {
    private InsoltInvestmentPlan insoltInvestmentPlan;

    public PammCommissionInsolt(InsoltInvestmentPlan insoltInvestmentPlan) {
        super(insoltInvestmentPlan.getMinimalInvestment(), insoltInvestmentPlan.getDays(), insoltInvestmentPlan.getManagerCommission());
        this.insoltInvestmentPlan = insoltInvestmentPlan;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(Pamm pamm, ProvidedParams providedParams) {
        Double avgChangePerWeek = pamm.getAvgChange() * 7;
        //pay attention, this value is not in percentage, it is in percentage/100;
        Double estimatedIncrease = Math.pow(1 + avgChangePerWeek/100, insoltInvestmentPlan.getDays() / 7) - 1;
        return 1 + estimatedIncrease * (1 - getCommissionFromProfit() / 100);
    }
}