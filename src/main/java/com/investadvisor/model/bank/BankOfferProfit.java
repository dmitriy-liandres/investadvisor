package com.investadvisor.model.bank;

import com.investadvisor.Currency;
import com.investadvisor.ProvidedParams;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentTargetOfferProfit;
import com.investadvisor.model.pamm.InvestmentTargetOffer;
import com.investadvisor.utils.Constants;

/**
 * Author Dmitriy Liandres
 * Date 06.06.2016
 */
public class BankOfferProfit extends InvestmentTargetOfferProfit {

    private boolean isCapitalization;
    private Double yearChange;

    public BankOfferProfit(boolean isCapitalization, Double monthChange) {
        this.isCapitalization = isCapitalization;
        this.yearChange = monthChange;
    }

    @Override
    public Double calculateProfitAfterMangerCommission(InvestmentTarget investmentTarget, InvestmentTargetOffer investmentTargetOffer, ProvidedParams providedParams) {

        //let's take into consideration taxis (http://www.banki.ru/services/calculators/deposits/)
        Double realYearChange;
        if (providedParams.getCurrency() == Currency.RUB) {
            realYearChange = yearChange > 16 ? 16 + (yearChange - 16) * 0.65 : yearChange;
        } else {
            realYearChange = yearChange > 9 ? 9 + (yearChange - 9) * 0.65 : yearChange;
        }
        //after deposit is finished, all money are moved to 0.1% deposit
        Double days = investmentTargetOffer.getMaxPeriodInDays() == null ? providedParams.getPeriodInDays() : Math.min(providedParams.getPeriodInDays(), investmentTargetOffer.getMaxPeriodInDays());
        Double daysWith01percentage = providedParams.getPeriodInDays() - days;

        Double estimatedIncrease;
        if (!isCapitalization) {
            estimatedIncrease = realYearChange / Constants.DAYS_PER_YEAR * providedParams.getPeriodInDays() / 100;
        } else {

            //how many full months bank account will be open
            Double monthsNumber = Math.floor(providedParams.getPeriodInDays() / Constants.DAYS_PER_MONTH);

            //how many days it will stay open after full months will be finished
            Double daysLeft = providedParams.getPeriodInDays() - monthsNumber * Constants.DAYS_PER_MONTH;


            //pay attention, this value is not in percentage, it is in percentage/100;
            estimatedIncrease = Math.pow(1 + realYearChange / Constants.MONTH_PER_YEAR / 100, monthsNumber) + realYearChange / Constants.DAYS_PER_YEAR * daysLeft / 100 - 1;
        }

        estimatedIncrease += 0.1 * daysWith01percentage / Constants.DAYS_PER_YEAR / 100;
        return 1 + estimatedIncrease;
    }

    @Override
    public String toString() {
        return "BankOfferProfit{" +
                "isCapitalization=" + isCapitalization +
                "} " + super.toString();
    }
}
