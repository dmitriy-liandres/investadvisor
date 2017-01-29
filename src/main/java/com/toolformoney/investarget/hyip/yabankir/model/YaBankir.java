package com.toolformoney.investarget.hyip.yabankir.model;

import com.toolformoney.Currency;
import com.toolformoney.ProvidedParams;
import com.toolformoney.exchangerates.YahooExchangeRates;
import com.toolformoney.model.hyip.Hyip;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class YaBankir extends Hyip {
    public YaBankir() {
        super(null, "yabankir", "ЯБанкир");
    }

    @Override
    public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
        return 2.;
    }

    @Override
    public Double getCommissionWithdrawPercentage(ProvidedParams providedParams) {
        return 3.;
    }

    @Override
    public Double getCommissionEnterFixed(ProvidedParams providedParams) throws IOException {
        return YahooExchangeRates.convert(50., Currency.RUB, providedParams.getCurrency());
    }

    @Override
    public Double getCommissionWithdrawFixed(ProvidedParams providedParams) throws IOException {
        return 0.;
    }

    @Override
    public String toString() {
        return "YaBankir{} " + super.toString();
    }
}
