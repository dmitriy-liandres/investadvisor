package com.investadvisor;

import com.investadvisor.exchangerates.YahooExchangeRates;

/**
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */

public class PammProfit {

    private Double profitPercentage;
    private Double profitMoney;

    /**
     * Commission is calculated based on visa/mastercard
     *
     * @param pamm            manager's pamm
     * @param providedParams  provided params
     * @throws Exception
     */
    public void calculateProfit(Pamm pamm, ProvidedParams providedParams) throws Exception {
        //pay attention, this valus is not in percentage, it is in percentage/100;
        Double profitInParts = Math.pow(1 + pamm.getAvgChange() / 100, providedParams.getPeriodInDays());

        //use commission
        Double commissionEnterPercentage = 0.;
        Double commissionWithdrawPercentage = 0.;

        Double commissionEnterFixed = 0.;
        Double commissionWithdrawFixed = 0.;
        switch (pamm.getPammBroker()) {
            case ALPARI:
                commissionEnterPercentage = 2.5;
                commissionWithdrawPercentage = 2.5;

                commissionEnterFixed = 0.;
                commissionWithdrawFixed = YahooExchangeRates.convertToUSD(0.5, Currency.RUB, providedParams.getCurrency());
                //http://www.alpari.ru/ru/trading/deposit_withdrawal/
                break;
            case FXOPEN:
                commissionEnterPercentage = 2.29;
                commissionWithdrawPercentage = 2.5;

                commissionEnterFixed = YahooExchangeRates.convertToUSD(0.22, Currency.USD, providedParams.getCurrency());
                commissionWithdrawFixed = YahooExchangeRates.convertToUSD(50., Currency.RUB, providedParams.getCurrency());
                //https://www.fxopen.ru/ru/komissii/
                break;
            case ALFA_FOREX:
                commissionEnterPercentage = 2.;
                commissionWithdrawPercentage = 0.;

                commissionEnterFixed = 0.;
                commissionWithdrawFixed = 0.;
                //https://www.alfa-forex.ru/ru/terms/traders/deposit-withdrawal.html#output_means-tab
                break;
            default:
                throw new Exception("Commission is not defined for " + pamm.getPammBroker());
        }

        //let's tale into consideration that pamm manager works with entered money - commission
        profitMoney = (providedParams.getSumm() * (1 - commissionEnterPercentage / 100) - commissionEnterFixed) * profitInParts;
        //let's tale into consideration that user loses money when we withdraws money
        profitMoney = profitMoney * (1 - commissionWithdrawPercentage / 100) - commissionWithdrawFixed;

        //we decrease on 100 to get net profit
        profitPercentage = (profitMoney / providedParams.getSumm()) * 100 - 100;
    }

    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public Double getProfitMoney() {
        return profitMoney;
    }
}
