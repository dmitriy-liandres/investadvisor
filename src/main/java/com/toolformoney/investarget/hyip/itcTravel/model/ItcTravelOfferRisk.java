package com.toolformoney.investarget.hyip.itcTravel.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.InvestmentTargetOfferProfitCalculation;
import com.toolformoney.model.InvestmentTargetOfferRisk;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class ItcTravelOfferRisk extends InvestmentTargetOfferRisk<InvestmentTarget> {
    @Override
    public Double calculateAndSetRisk(InvestmentTarget investmentTarget, ProvidedParams providedParams, InvestmentTargetOfferProfitCalculation investmentTargetOfferProfitCalculation) throws IOException {
        return 100. * (9 + 12 * +15 + 18 + 21) / 5 / 30;
    }

    @Override
    public String toString() {
        return "ItcTravelRisk{} " + super.toString();
    }
}
