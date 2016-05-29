package com.investadvisor.pamm.alpari.model;

import com.investadvisor.Currency;
import com.investadvisor.exchangerates.YahooExchangeRates;
import com.investadvisor.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class AlpariPamm extends Pamm {
    @Override
    public String generateLink() {
        return "http://www.alpari.ru/ru/investor/pamm/" + getId() + "/?partner_id=1231285";
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
        return YahooExchangeRates.convertToUSD(30., Currency.RUB, currency);
    }
}
