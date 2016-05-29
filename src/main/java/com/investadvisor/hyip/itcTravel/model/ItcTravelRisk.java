package com.investadvisor.hyip.itcTravel.model;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentTargetRisk;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class ItcTravelRisk extends InvestmentTargetRisk {
    @Override
    public Double calculateAndSetRisk(InvestmentTarget investmentTarget, ProvidedParams providedParams) throws IOException {
        setTotalRisk(100. * (9 + 12 * +15 + 18 + 21) / 5 / 30);
        return getTotalRisk();
    }

    @Override
    public String toString() {
        return "ItcTravelRisk{} " + super.toString();
    }
}
