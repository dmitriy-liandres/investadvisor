package com.investadvisor.startups.shareinstock.model;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentTargetOfferProfit;
import com.investadvisor.model.pamm.InvestmentTargetOffer;
import com.investadvisor.model.startup.StartupOffer;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class ShareInStockOfferProfit extends InvestmentTargetOfferProfit {
    /**
     * if it is possible to sell for 1.5$ and original price is 2, than value = 91.5-2)/2*100
     */
    private Double askBidDifferenceInPercentages;

    public ShareInStockOfferProfit(Double askBidDifferenceInPercentages) {
        this.askBidDifferenceInPercentages = askBidDifferenceInPercentages;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(InvestmentTarget investmentTarget, InvestmentTargetOffer investmentTargetOffer, ProvidedParams providedParams) {

        StartupOffer startupOffer = (StartupOffer) investmentTargetOffer;

        //pay attention, this value is not in percentage, it is in percentage/100;
        Double estimatedIncrease = startupOffer.getPercentagePerMonth() * Math.floor(providedParams.getPeriodInDays() / 30) / 100;

        if (providedParams.getPeriodInDays() < 365) {
            //if shares were bought more than a year ago it is possible to sell them by nominal price
            estimatedIncrease -= askBidDifferenceInPercentages / 100;
        }
        return 1 + estimatedIncrease;
    }
}
