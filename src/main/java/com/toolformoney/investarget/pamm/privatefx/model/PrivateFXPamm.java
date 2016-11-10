package com.toolformoney.investarget.pamm.privatefx.model;

import com.toolformoney.Currency;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 10.11.2016
 */
public class PrivateFXPamm extends Pamm {
    public PrivateFXPamm() {
        super(InvestmentTypeName.PRIVATE_FX, "privateFX", "vklady-investitsii/pamm/private-fx");
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 0.;
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
