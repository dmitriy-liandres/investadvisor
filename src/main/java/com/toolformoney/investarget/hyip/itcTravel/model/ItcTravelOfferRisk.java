package com.toolformoney.investarget.hyip.itcTravel.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.InvestmentTargetOfferProfit;
import com.toolformoney.model.InvestmentTargetOfferRisk;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class ItcTravelOfferRisk extends InvestmentTargetOfferRisk<InvestmentTarget, InvestmentTargetOfferProfit> {
    @Override
    public Double calculateAndSetRisk(InvestmentTarget investmentTarget, ProvidedParams providedParams, InvestmentTargetOfferProfit investmentTargetOfferProfit) throws IOException {
        setTotalRisk(100. * (9 + 12 * +15 + 18 + 21) / 5 / 30);
        return getTotalRisk();
    }

    @Override
    public String toString() {
        return "ItcTravelRisk{} " + super.toString();
    }
}
