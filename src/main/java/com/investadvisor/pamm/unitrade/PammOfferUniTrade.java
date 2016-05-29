package com.investadvisor.pamm.unitrade;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.pamm.InvestmentTargetOffer;
import com.investadvisor.pamm.unitrade.model.UnitradeInvestmentPlan;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class PammOfferUniTrade extends InvestmentTargetOffer {
    private UnitradeInvestmentPlan unitradeInvestmentPlan;

    public PammOfferUniTrade(UnitradeInvestmentPlan unitradeInvestmentPlan) {
        super(unitradeInvestmentPlan.getMinimalInvestment(), unitradeInvestmentPlan.getDays(), 0.);
        this.unitradeInvestmentPlan = unitradeInvestmentPlan;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(InvestmentTarget pamm, ProvidedParams providedParams) {
        return 1 + unitradeInvestmentPlan.getPercentagePerMonth() * unitradeInvestmentPlan.getMonthsNumber() / 100;
    }
}
