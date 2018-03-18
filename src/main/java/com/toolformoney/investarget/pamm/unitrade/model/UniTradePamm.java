package com.toolformoney.investarget.pamm.unitrade.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentType;
import com.toolformoney.model.pamm.Pamm;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class UniTradePamm extends Pamm {

    public UniTradePamm() {
        super(null, "uni-trade", "/uni-trade");
        setInvestmentTypeLink("/vklady-investitsii/doveritelnoe-upravlenie/");
        setInvestmentType(InvestmentType.TRUST_MANAGEMENT);
    }

    @Override
    public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
        return 3.627;
    }

    @Override
    public Double getCommissionWithdrawPercentage(ProvidedParams providedParams) {
        return 0.;
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
