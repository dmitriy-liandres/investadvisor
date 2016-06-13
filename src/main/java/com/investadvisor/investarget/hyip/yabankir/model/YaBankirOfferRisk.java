package com.investadvisor.investarget.hyip.yabankir.model;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentTargetOfferProfit;
import com.investadvisor.model.InvestmentTargetOfferRisk;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class YaBankirOfferRisk extends InvestmentTargetOfferRisk<InvestmentTarget, InvestmentTargetOfferProfit> {
    /**
     * Risk fo all hypes = 100*percentage per month
     *
     * @return total risk
     */

    @Override
    public Double calculateAndSetRisk(InvestmentTarget investmentTarget, ProvidedParams providedParams, InvestmentTargetOfferProfit investmentTargetOfferProfit) throws IOException {
        setTotalRisk(100. * 265 / 12);
        return getTotalRisk();
    }

    @Override
    public String toString() {
        return "YaBankirRisk{} " + super.toString();
    }
}
