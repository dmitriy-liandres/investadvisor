package com.toolformoney.investarget.pamm.alfaForex.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class AlfaForexPamm extends Pamm {

    public AlfaForexPamm() {
        super(null/*InvestmentTypeName.ALFA_FOREX*/, "alfa-forex", "/vklady-investitsii/pamm/alfa-forex");
    }

    @Override
    public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
        return 2.;
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
