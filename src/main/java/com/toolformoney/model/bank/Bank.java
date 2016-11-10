package com.toolformoney.model.bank;

import com.toolformoney.Currency;
import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.InvestmentType;
import com.toolformoney.model.InvestmentTypeName;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 06.06.2016
 */
public class Bank extends InvestmentTarget {
    public Bank(InvestmentTypeName investmentTypeName, String name, String investmentPartnerLink) {
        super(InvestmentType.BANK, investmentTypeName, name, "/vklady-investitsii/banki/", investmentPartnerLink);
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
