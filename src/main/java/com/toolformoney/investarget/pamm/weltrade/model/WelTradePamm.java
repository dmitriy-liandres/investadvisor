package com.toolformoney.investarget.pamm.weltrade.model;

import com.toolformoney.Currency;
import com.toolformoney.exchangerates.YahooExchangeRates;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 25.09.2016
 */
public class WelTradePamm extends Pamm {

    public WelTradePamm() {
        super("Weltrade", "vklady-investitsii/pamm/weltrade/");
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawPercentage() {
        return 2.9;
    }

    @Override
    public Double getCommissionEnterFixed(Currency currency) {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed(Currency currency) throws IOException {
        return YahooExchangeRates.convert(50., Currency.RUB, currency);
    }
}
