package com.toolformoney.investarget.tradesignals.mql5.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.tradesignals.TradeSignal;
import com.toolformoney.utils.Constants;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 11.12.2016
 */
public class Mql5TradeSignal extends TradeSignal {
    private Double commissionPerMonth;

    public Mql5TradeSignal(Double commissionPerMonth) {
        super(InvestmentTypeName.MQL5, "MQL5", "/vklady-investitsii/torgovye-signaly");
        this.commissionPerMonth = commissionPerMonth;
    }

    @Override
    public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
        return 0.;
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
        return commissionPerMonth * providedParams.getPeriodInDays() / Constants.DAYS_PER_MONTH;
    }
}
