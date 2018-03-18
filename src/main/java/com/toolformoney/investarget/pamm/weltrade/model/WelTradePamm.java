package com.toolformoney.investarget.pamm.weltrade.model;

import com.toolformoney.Currency;
import com.toolformoney.ProvidedParams;
import com.toolformoney.exchangerates.FixerIOExchangeRates;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 25.09.2016
 */
public class WelTradePamm extends Pamm {

    public WelTradePamm() {
        super(/*InvestmentTypeName.WEL_TRADE*/null, "Weltrade", "/vklady-investitsii/pamm/weltrade/");
    }

    @Override
    public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawPercentage(ProvidedParams providedParams) {
        return 2.9;
    }

    @Override
    public Double getCommissionEnterFixed(ProvidedParams providedParams) {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed(ProvidedParams providedParams) throws IOException {
        return FixerIOExchangeRates.convert(50., Currency.RUB, providedParams.getCurrency());
    }
}
