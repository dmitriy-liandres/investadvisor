package com.investadvisor.pamm.fxopen.model;

import com.investadvisor.Currency;
import com.investadvisor.exchangerates.YahooExchangeRates;
import com.investadvisor.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class FxOpenPamm extends Pamm {
    @Override
    public String generateLink() {
        return "https://pamm.fxopen.ru/Pamm/" + getName() +"?agent=691142";
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
        return YahooExchangeRates.convertToUSD(0.22, Currency.USD, currency);
    }

    @Override
    public Double getCommissionWithdrawFixed(Currency currency) throws IOException {
        return YahooExchangeRates.convertToUSD(50., Currency.RUB, currency);
    }
}
