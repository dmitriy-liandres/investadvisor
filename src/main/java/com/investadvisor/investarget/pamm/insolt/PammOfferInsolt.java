package com.investadvisor.investarget.pamm.insolt;

import com.investadvisor.Currency;
import com.investadvisor.model.pamm.InvestmentTargetOffer;
import com.investadvisor.model.pamm.PammOfferRisk;
import com.investadvisor.investarget.pamm.insolt.model.InsoltInvestmentPlan;
import com.investadvisor.investarget.pamm.insolt.model.InsoltPammOfferProfit;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class PammOfferInsolt extends InvestmentTargetOffer {
    public PammOfferInsolt(InsoltInvestmentPlan insoltInvestmentPlan, Double avgChange) {
        super(insoltInvestmentPlan.getName(), insoltInvestmentPlan.getMinimalInvestment(), null, insoltInvestmentPlan.getDays(), null, insoltInvestmentPlan.getManagerCommission(), insoltInvestmentPlan.getLink(), Currency.USD, avgChange, new PammOfferRisk(), new InsoltPammOfferProfit(insoltInvestmentPlan));
    }

    @Override
    public String toString() {
        return "PammOfferInsolt{} " + super.toString();
    }
}