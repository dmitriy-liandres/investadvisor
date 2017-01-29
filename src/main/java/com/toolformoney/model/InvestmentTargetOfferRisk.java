package com.toolformoney.model;

import com.toolformoney.ProvidedParams;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public abstract class InvestmentTargetOfferRisk<IT extends InvestmentTarget> {

    /**
     *
     * @return total risk in %
     */
    public abstract Double calculateAndSetRisk(IT investmentTarget, ProvidedParams providedParams, InvestmentTargetOfferProfitCalculation investmentTargetOfferProfitCalculation) throws IOException;

}
