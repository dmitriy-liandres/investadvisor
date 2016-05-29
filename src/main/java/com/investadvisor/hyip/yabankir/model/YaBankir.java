package com.investadvisor.hyip.yabankir.model;

import com.investadvisor.Currency;
import com.investadvisor.exchangerates.YahooExchangeRates;
import com.investadvisor.model.InvestmentTargetProfit;
import com.investadvisor.model.InvestmentTargetRisk;
import com.investadvisor.model.hyip.Hyip;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public class YaBankir extends Hyip {
    public YaBankir(InvestmentTargetRisk investmentTargetRisk,
                    InvestmentTargetProfit investmentTargetProfit) {
        super(investmentTargetRisk, investmentTargetProfit);
    }

    @Override
    public String generateLink() {
        return "https://yabankir.com/?aid=dima-amid";
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 2.;
    }

    @Override
    public Double getCommissionWithdrawPercentage() {
        return 3.;
    }

    @Override
    public Double getCommissionEnterFixed(Currency currency) throws IOException {
        return YahooExchangeRates.convertToUSD(50., Currency.RUB, currency);
    }

    @Override
    public Double getCommissionWithdrawFixed(Currency currency) throws IOException {
        return 0.;
    }

    @Override
    public String toString() {
        return "YaBankir{} " + super.toString();
    }
}
