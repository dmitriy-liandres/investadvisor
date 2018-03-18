package com.toolformoney.investarget.pamm.amarkets.model;

import com.toolformoney.Currency;
import com.toolformoney.ProvidedParams;
import com.toolformoney.exchangerates.FixerIOExchangeRates;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 11.11.2016
 */
public class AmarketsPamm extends Pamm {
    public AmarketsPamm() {
        super(InvestmentTypeName.AMARKETS, "Amarkets", "/vklady-investitsii/pamm/amarkets");
    }

    @Override
    public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawPercentage(ProvidedParams providedParams) {
        return 1.;
    }

    @Override
    public Double getCommissionEnterFixed(ProvidedParams providedParams) throws IOException {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed(ProvidedParams providedParams) throws IOException {
        return FixerIOExchangeRates.convert(50., Currency.RUB, providedParams.getCurrency());
    }
}
