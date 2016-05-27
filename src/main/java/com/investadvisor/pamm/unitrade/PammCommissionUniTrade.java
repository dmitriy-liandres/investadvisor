package com.investadvisor.pamm.unitrade;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.Pamm;
import com.investadvisor.model.PammCommission;
import com.investadvisor.pamm.unitrade.model.UnitradeInvestmentPlan;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class PammCommissionUniTrade extends PammCommission {
    private UnitradeInvestmentPlan unitradeInvestmentPlan;

    public PammCommissionUniTrade(UnitradeInvestmentPlan unitradeInvestmentPlan) {
        super(unitradeInvestmentPlan.getMinimalInvestment(), unitradeInvestmentPlan.getDays(), 0.);
        this.unitradeInvestmentPlan = unitradeInvestmentPlan;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(Pamm pamm, ProvidedParams providedParams) {
        return 1 + unitradeInvestmentPlan.getPercentagePerMonth() * unitradeInvestmentPlan.getMonthsNumber() / 100;
    }
}
