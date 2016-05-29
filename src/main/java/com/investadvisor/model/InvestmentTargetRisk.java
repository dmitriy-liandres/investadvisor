package com.investadvisor.model;

import com.investadvisor.ProvidedParams;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public abstract class InvestmentTargetRisk {

    private Double totalRisk;
    /**
     *
     * @return total rist
     */
    public Double getTotalRisk(){
        return totalRisk;
    }

    protected void setTotalRisk(Double totalRisk) {
        this.totalRisk = totalRisk;
    }

    public abstract Double calculateAndSetRisk(InvestmentTarget investmentTarget, ProvidedParams providedParams) throws IOException;

    @Override
    public String toString() {
        return "InvestmentTargetRisk{" +
                "totalRisk=" + totalRisk +
                '}';
    }
}
