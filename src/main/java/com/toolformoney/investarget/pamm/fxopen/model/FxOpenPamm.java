package com.toolformoney.investarget.pamm.fxopen.model;

import com.toolformoney.Currency;
import com.toolformoney.exchangerates.YahooExchangeRates;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class FxOpenPamm extends Pamm {

    public FxOpenPamm() {
        super("fxopen", "vklady-investitsii/pamm/fxopen");
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 2.29;
    }

    @Override
    public Double getCommissionWithdrawPercentage() {
        return 2.5;
    }

    @Override
    public Double getCommissionEnterFixed(Currency currency) throws IOException {
        return YahooExchangeRates.convert(0.22, Currency.USD, currency);
    }

    @Override
    public Double getCommissionWithdrawFixed(Currency currency) throws IOException {
        return YahooExchangeRates.convert(50., Currency.RUB, currency);
    }
}
