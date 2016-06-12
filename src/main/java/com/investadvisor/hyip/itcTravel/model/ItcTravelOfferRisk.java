package com.investadvisor.hyip.itcTravel.model;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentTargetOfferProfit;
import com.investadvisor.model.InvestmentTargetOfferRisk;

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