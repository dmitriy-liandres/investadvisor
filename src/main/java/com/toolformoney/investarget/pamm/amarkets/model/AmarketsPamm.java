package com.toolformoney.investarget.pamm.amarkets.model;

import com.toolformoney.Currency;
import com.toolformoney.exchangerates.YahooExchangeRates;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 11.11.2016
 */
public class AmarketsPamm extends Pamm {
    public AmarketsPamm() {
        super(InvestmentTypeName.AMARKETS, "Amarkets", "vklady-investitsii/pamm/amarkets");
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawPercentage() {
        return 1.;
    }

    @Override
    public Double getCommissionEnterFixed(Currency currency) throws IOException {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed(Currency currency) throws IOException {
        return YahooExchangeRates.convert(50., Currency.RUB, currency);
    }
}
