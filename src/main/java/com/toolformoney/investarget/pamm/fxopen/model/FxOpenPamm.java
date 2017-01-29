package com.toolformoney.investarget.pamm.fxopen.model;

import com.toolformoney.Currency;
import com.toolformoney.ProvidedParams;
import com.toolformoney.exchangerates.YahooExchangeRates;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class FxOpenPamm extends Pamm {

    public FxOpenPamm() {
        super(InvestmentTypeName.FX_OPEN, "fxopen", "/vklady-investitsii/pamm/fxopen");
    }

    @Override
    public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
        return 2.29;
    }

    @Override
    public Double getCommissionWithdrawPercentage(ProvidedParams providedParams) {
        return 2.5;
    }

    @Override
    public Double getCommissionEnterFixed(ProvidedParams providedParams) throws IOException {
        return YahooExchangeRates.convert(0.22, Currency.USD, providedParams.getCurrency());
    }

    @Override
    public Double getCommissionWithdrawFixed(ProvidedParams providedParams) throws IOException {
        return YahooExchangeRates.convert(50., Currency.RUB, providedParams.getCurrency());
    }
}
