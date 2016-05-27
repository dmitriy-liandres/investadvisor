package com.investadvisor.pamm.unitrade;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.Pamm;
import com.investadvisor.model.PammCommission;
import com.investadvisor.pamm.unitrade.model.InvestmentPlan;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class PammCommissionUniTrade extends PammCommission {
    private InvestmentPlan investmentPlan;

    public PammCommissionUniTrade(Double minInvestment,
                                  Integer minPeriodInDays,
                                  Double commissionFromProfit,
                                  InvestmentPlan investmentPlan) {
        super(minInvestment, minPeriodInDays, commissionFromProfit);
        this.investmentPlan = investmentPlan;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(Pamm pamm, ProvidedParams providedParams) {
        return 1 + investmentPlan.getPercentagePerMonth() * investmentPlan.getMonthsNumber() / 100;
    }
}
