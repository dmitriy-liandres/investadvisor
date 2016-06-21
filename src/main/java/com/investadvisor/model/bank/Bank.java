package com.investadvisor.model.bank;

import com.investadvisor.Currency;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentType;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 06.06.2016
 */
public class Bank extends InvestmentTarget {
    public Bank(String name) {
        super(InvestmentType.BANK, name, "/банк/");
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
