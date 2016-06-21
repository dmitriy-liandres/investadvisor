package com.investadvisor.investarget.pamm.alpari.model;

import com.investadvisor.Currency;
import com.investadvisor.exchangerates.YahooExchangeRates;
import com.investadvisor.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class AlpariPamm extends Pamm {

    public AlpariPamm() {
        super("Alpari", "/alpari");
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
