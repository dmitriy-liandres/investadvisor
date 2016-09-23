package com.toolformoney.investarget.hyip.itcTravel.model;

import com.toolformoney.Currency;
import com.toolformoney.model.hyip.Hyip;

import java.io.IOException;

/**
 * PAY ATTENTION: COMISSION IS NOT CORRECT
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class ItcTravel extends Hyip {
    public ItcTravel() {
        super("itc-travel", "/itc-travel");
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 9.85;
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
