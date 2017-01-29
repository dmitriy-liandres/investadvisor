package com.toolformoney.investarget.hyip.itcTravel.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.hyip.Hyip;

import java.io.IOException;

/**
 * PAY ATTENTION: COMISSION IS NOT CORRECT
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class ItcTravel extends Hyip {
    public ItcTravel() {
        super(null, "itc-travel", "/itc-travel");
    }

    @Override
    public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
        return 9.85;
    }

    @Override
    public Double getCommissionWithdrawPercentage(ProvidedParams providedParams) {
        return 0.;
    }

    @Override
    public Double getCommissionEnterFixed(ProvidedParams providedParams) throws IOException {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed(ProvidedParams providedParams) throws IOException {
        return 0.;
    }
}
