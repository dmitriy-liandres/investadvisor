package com.toolformoney.investarget.pamm.unitrade;

import com.toolformoney.Currency;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.forex.ForexOfferRisk;
import com.toolformoney.investarget.pamm.unitrade.model.UnitradeInvestmentPlan;
import com.toolformoney.investarget.pamm.unitrade.model.UnitradeForexOfferProfit;
import com.toolformoney.model.pamm.PammOfferRisk;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class PammOfferUniTrade extends InvestmentTargetOffer {
    private UnitradeInvestmentPlan unitradeInvestmentPlan;

    public PammOfferUniTrade(UnitradeInvestmentPlan unitradeInvestmentPlan) {
        super(unitradeInvestmentPlan.getName(), unitradeInvestmentPlan.getMinimalInvestment(), null, unitradeInvestmentPlan.getDays(), null, 0., unitradeInvestmentPlan.getLink(), Currency.USD, null, new PammOfferRisk(), new UnitradeForexOfferProfit(unitradeInvestmentPlan));
        this.unitradeInvestmentPlan = unitradeInvestmentPlan;
    }

    @Override
    public String toString() {
        return "PammOfferUniTrade{" +
                "unitradeInvestmentPlan=" + unitradeInvestmentPlan +
                "} " + super.toString();
    }
}
