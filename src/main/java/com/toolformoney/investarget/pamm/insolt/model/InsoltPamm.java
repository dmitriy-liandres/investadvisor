package com.toolformoney.investarget.pamm.insolt.model;

import com.toolformoney.Currency;
import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentType;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class InsoltPamm extends Pamm {

    public InsoltPamm() {
        super(null, "insolt", "/vklady-investitsii/doveritelnoe-upravlenie/insolt-doveritelnoe-upravlenie/");
        setInvestmentTypeLink("/vklady-investitsii/doveritelnoe-upravlenie/");
        setInvestmentType(InvestmentType.TRUST_MANAGEMENT);
    }

    @Override
    public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
        return 2.24;
    }

    @Override
    public Double getCommissionWithdrawPercentage(ProvidedParams providedParams) {
        return 2.;
    }

    @Override
    public Double getCommissionEnterFixed(ProvidedParams providedParams) throws IOException {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed(ProvidedParams providedParams) throws IOException {
        return 0.;
    }
}
