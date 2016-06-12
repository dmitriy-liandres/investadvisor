package com.investadvisor.model.bank;

import com.investadvisor.Currency;
import com.investadvisor.ProvidedParams;
import com.investadvisor.exchangerates.YahooExchangeRates;
import com.investadvisor.model.InvestmentTargetOfferRisk;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 06.06.2016
 */
public class BankOfferRisk extends InvestmentTargetOfferRisk<Bank, BankOfferProfit> {
    private static final Double MAX_SAFE_AMOUNT = 1_400_000.;
    private static final Double BASE_RISK_VALUE = 0.1;
    //todo we have to calculate this somehow in future
    private Double bankRisk;

    public BankOfferRisk(Double bankRisk) {
        this.bankRisk = bankRisk;
    }

    @Override
    public Double calculateAndSetRisk(Bank bank, ProvidedParams providedParams, BankOfferProfit bankOfferProfit) throws IOException {
        //let's calculate sum

        Double resultsMoneyInRubles = YahooExchangeRates.convert(bankOfferProfit.getProfitMoney() + providedParams.getSumm(), providedParams.getCurrency(), Currency.RUB);
        Double risk ;
        if (resultsMoneyInRubles < MAX_SAFE_AMOUNT) {
            //amount will be returned by special agency in any case
            risk = BASE_RISK_VALUE;
        } else {
            //there is a risk to loose part of your money
            risk = BASE_RISK_VALUE + bankRisk;
        }

        setTotalRisk(risk);
        return getTotalRisk();
    }
}
