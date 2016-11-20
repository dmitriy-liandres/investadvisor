package com.toolformoney.investarget.pamm.alfaForex.model;

import com.toolformoney.Currency;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class AlfaForexPamm extends Pamm {

    public AlfaForexPamm() {
        super(InvestmentTypeName.ALFA_FOREX, "alfa-forex", "/vklady-investitsii/pamm/alfa-forex");
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 2.;
    }

    @Override
    public Double getCommissionWithdrawPercentage() {
        return 0.;
    }

    @Override
    public Double getCommissionEnterFixed(Currency currency) throws IOException {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed(Currency currency) throws IOException {
        return 0.;
    }
}
