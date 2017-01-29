package com.toolformoney.investarget.pamm.insolt;

import com.toolformoney.Currency;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.forex.ForexOfferRisk;
import com.toolformoney.investarget.pamm.insolt.model.InsoltInvestmentPlan;
import com.toolformoney.investarget.pamm.insolt.model.InsoltForexOfferProfit;
import com.toolformoney.model.pamm.PammOfferRisk;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class PammOfferInsolt extends InvestmentTargetOffer {
    public PammOfferInsolt(InsoltInvestmentPlan insoltInvestmentPlan, Double avgChange) {
        super(insoltInvestmentPlan.getName(), insoltInvestmentPlan.getMinimalInvestment(), null, insoltInvestmentPlan.getDays(), null, insoltInvestmentPlan.getManagerCommission(), insoltInvestmentPlan.getLink(), Currency.USD, avgChange, new PammOfferRisk(), new InsoltForexOfferProfit(insoltInvestmentPlan));
    }

    @Override
    public String toString() {
        return "PammOfferInsolt{} " + super.toString();
    }
}