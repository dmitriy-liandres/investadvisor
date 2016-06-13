package com.investadvisor.investarget.pamm.unitrade;

import com.investadvisor.Currency;
import com.investadvisor.model.pamm.InvestmentTargetOffer;
import com.investadvisor.model.pamm.PammOfferRisk;
import com.investadvisor.investarget.pamm.unitrade.model.UnitradeInvestmentPlan;
import com.investadvisor.investarget.pamm.unitrade.model.UnitradePammOfferProfit;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class PammOfferUniTrade extends InvestmentTargetOffer {
    private UnitradeInvestmentPlan unitradeInvestmentPlan;

    public PammOfferUniTrade(UnitradeInvestmentPlan unitradeInvestmentPlan) {
        super(unitradeInvestmentPlan.getName(), unitradeInvestmentPlan.getMinimalInvestment(), null, unitradeInvestmentPlan.getDays(), null, 0., unitradeInvestmentPlan.getLink(), Currency.USD, null, new PammOfferRisk(), new UnitradePammOfferProfit(unitradeInvestmentPlan));
        this.unitradeInvestmentPlan = unitradeInvestmentPlan;
    }

    @Override
    public String toString() {
        return "PammOfferUniTrade{" +
                "unitradeInvestmentPlan=" + unitradeInvestmentPlan +
                "} " + super.toString();
    }
}
