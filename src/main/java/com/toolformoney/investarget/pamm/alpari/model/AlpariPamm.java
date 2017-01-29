package com.toolformoney.investarget.pamm.alpari.model;

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
public class AlpariPamm extends Pamm {

    public AlpariPamm() {
        super(InvestmentTypeName.ALPARI, "Alpari", "/vklady-investitsii/pamm/alpari/");
    }

    @Override
    public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
        return 2.5;
    }

    @Override
    public Double getCommissionWithdrawPercentage(ProvidedParams providedParams) {
        return 2.5;
    }

    @Override
    public Double getCommissionEnterFixed(ProvidedParams providedParams) {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed(ProvidedParams providedParams) throws IOException {
        return YahooExchangeRates.convert(30., Currency.RUB, providedParams.getCurrency());
    }
}
