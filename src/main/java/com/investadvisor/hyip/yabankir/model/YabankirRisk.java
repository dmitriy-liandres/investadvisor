package com.investadvisor.hyip.yabankir.model;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentTargetRisk;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class YaBankirRisk extends InvestmentTargetRisk {
    /**
     * Risk fo all hypes = 100*average percentage
     *
     * @return total risk
     */

    @Override
    public Double calculateAndSetRisk(InvestmentTarget investmentTarget, ProvidedParams providedParams) throws IOException {
        setTotalRisk(100. * 1);
        return getTotalRisk();
    }

    @Override
    public String toString() {
        return "YaBankirRisk{} " + super.toString();
    }
}
