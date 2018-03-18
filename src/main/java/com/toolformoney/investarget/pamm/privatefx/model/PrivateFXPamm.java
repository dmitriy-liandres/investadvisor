package com.toolformoney.investarget.pamm.privatefx.model;

import com.toolformoney.Currency;
import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 10.11.2016
 */
public class PrivateFXPamm extends Pamm {
    public PrivateFXPamm() {
        super(null/*InvestmentTypeName.PRIVATE_FX*/, "privateFX", "/vklady-investitsii/pamm/private-fx");
    }

    @Override
    public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
        return 0.;
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
