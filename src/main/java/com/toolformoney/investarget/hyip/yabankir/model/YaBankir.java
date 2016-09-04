package com.toolformoney.investarget.hyip.yabankir.model;

import com.toolformoney.Currency;
import com.toolformoney.exchangerates.YahooExchangeRates;
import com.toolformoney.model.hyip.Hyip;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class YaBankir extends Hyip {
    public YaBankir() {
        super("yabankir", "ЯБанкир");
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 2.;
    }

    @Override
    public Double getCommissionWithdrawPercentage() {
        return 3.;
    }

    @Override
    public Double getCommissionEnterFixed(Currency currency) throws IOException {
        return YahooExchangeRates.convert(50., Currency.RUB, currency);
    }

    @Override
    public Double getCommissionWithdrawFixed(Currency currency) throws IOException {
        return 0.;
    }

    @Override
    public String toString() {
        return "YaBankir{} " + super.toString();
    }
}
