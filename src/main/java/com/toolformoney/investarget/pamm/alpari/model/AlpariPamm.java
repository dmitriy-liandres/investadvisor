package com.toolformoney.investarget.pamm.alpari.model;

import com.toolformoney.Currency;
import com.toolformoney.exchangerates.YahooExchangeRates;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class AlpariPamm extends Pamm {

    public AlpariPamm() {
        super("Alpari", "vklady-investitsii/pamm/alpari/");
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 2.5;
    }

    @Override
    public Double getCommissionWithdrawPercentage() {
        return 2.5;
    }

    @Override
    public Double getCommissionEnterFixed(Currency currency) {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed(Currency currency) throws IOException {
        return YahooExchangeRates.convert(30., Currency.RUB, currency);
    }
}