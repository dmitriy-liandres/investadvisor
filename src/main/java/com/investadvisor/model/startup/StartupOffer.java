package com.investadvisor.model.startup;

import com.investadvisor.Currency;
import com.investadvisor.model.InvestmentTargetOfferProfit;
import com.investadvisor.model.InvestmentTargetOfferRisk;
import com.investadvisor.model.pamm.InvestmentTargetOffer;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class StartupOffer extends InvestmentTargetOffer {
    private Double percentagePerMonth;

    public StartupOffer(String name,
                        Double minInvestment,
                        Double maxInvestment,
                        Double percentagePerMonth,
                        String link,
                        InvestmentTargetOfferRisk investmentTargetOfferRisk,
                        InvestmentTargetOfferProfit investmentTargetOfferProfit) {
        super(name, minInvestment, maxInvestment, 30, 0., link, Currency.USD, null, investmentTargetOfferRisk, investmentTargetOfferProfit);

        this.percentagePerMonth = percentagePerMonth;
    }

    public Double getPercentagePerMonth() {
        return percentagePerMonth;
    }

    @Override
    public String toString() {
        return "StartupOffer{" +
                "percentagePerMonth=" + percentagePerMonth +
                "} " + super.toString();
    }
}
