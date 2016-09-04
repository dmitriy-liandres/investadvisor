package com.toolformoney.investarget.pamm.unitrade.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.pamm.PammOfferProfit;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class UnitradePammOfferProfit extends PammOfferProfit {

    private UnitradeInvestmentPlan unitradeInvestmentPlan;

    public UnitradePammOfferProfit(UnitradeInvestmentPlan unitradeInvestmentPlan) {
        this.unitradeInvestmentPlan = unitradeInvestmentPlan;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(InvestmentTarget investmentTarget, InvestmentTargetOffer investmentTargetOffer, ProvidedParams providedParams) {
        return 1 + unitradeInvestmentPlan.getPercentagePerMonth() * unitradeInvestmentPlan.getMonthsNumber() / 100;
    }

    @Override
    public String toString() {
        return "UnitradePammOfferProfit{" +
                "unitradeInvestmentPlan=" + unitradeInvestmentPlan +
                "} " + super.toString();
    }
}
