package com.investadvisor.hyip.itcTravel.model;

import com.investadvisor.Currency;
import com.investadvisor.model.hyip.Hyip;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class ItcTravel extends Hyip {
    public ItcTravel() {
        super("itc-travel");
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 9.85;
    }

    @Override
    public Double getCommissionWithdrawPercentage() {
        return 0.;//todo no defined yet
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
