package com.investadvisor.hyip.itcTravel.model;

import com.investadvisor.Currency;
import com.investadvisor.model.InvestmentTargetProfit;
import com.investadvisor.model.InvestmentTargetRisk;
import com.investadvisor.model.hyip.Hyip;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class ItcTravel extends Hyip {
    public ItcTravel(InvestmentTargetRisk investmentTargetRisk,
                     InvestmentTargetProfit investmentTargetProfit) {
        super(investmentTargetRisk, investmentTargetProfit);
    }

    @Override
    public String generateLink() {
        return "https://itc-travel.biz/investor/investment-offer/?id=688";
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
