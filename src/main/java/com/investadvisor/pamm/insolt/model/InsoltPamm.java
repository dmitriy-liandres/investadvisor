package com.investadvisor.pamm.insolt.model;

import com.investadvisor.Currency;
import com.investadvisor.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class InsoltPamm extends Pamm {
    @Override
    public String generateLink() {
        return "https://personal.insolt.com/register/?p=12588";
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 2.24;
    }

    @Override
    public Double getCommissionWithdrawPercentage() {
        return 2.;
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
