package com.investadvisor.model;

import com.investadvisor.Currency;
import com.investadvisor.ProvidedParams;
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
     * @param pamm           manager's pamm
     * @param providedParams provided params
     * @return true if pamm suits user, false otherwise
     * @throws Exception
     */
    public Boolean calculateProfit(Pamm pamm, ProvidedParams providedParams) throws Exception {

        //use commission
        Double commissionEnterPercentage;
        Double commissionWithdrawPercentage;

        Double commissionEnterFixed;
        Double commissionWithdrawFixed;
        switch (pamm.getPammBroker()) {
            case ALPARI:
                commissionEnterPercentage = 2.5;
                commissionWithdrawPercentage = 2.5;

                commissionEnterFixed = 0.;
                commissionWithdrawFixed = YahooExchangeRates.convertToUSD(30., Currency.RUB, providedParams.getCurrency());
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

            case UNI_TRADE:
                commissionEnterPercentage = 3.627;
                commissionWithdrawPercentage = 0.; //todo check test

                commissionEnterFixed = 0.;
                commissionWithdrawFixed = 0.;
                break;
            default:
                throw new Exception("Commission is not defined for " + pamm.getPammBroker());
        }

        //let's tale into consideration that pamm manager works with entered money - commission
        Double investedMoney = providedParams.getSumm() * (1 - commissionEnterPercentage / 100) - commissionEnterFixed;

        profitMoney = null;
        //select best commission


        for (PammCommission pammCommission : pamm.getCommissions()) {
            if (pammCommission.getMinInvestment() > investedMoney || pammCommission.getMinPeriodInDays() > providedParams.getPeriodInDays()) {
                continue;
            }

            Double calculatedFinalResultAfterMangerCommission = pammCommission.calculateProfitAfterMangerCommission(pamm, providedParams);

            Double profitMoneyForSpecifiedCommission = investedMoney * calculatedFinalResultAfterMangerCommission;

            //let's tale into consideration that user loses money when he withdraws money
            profitMoneyForSpecifiedCommission = profitMoneyForSpecifiedCommission * (1 - commissionWithdrawPercentage / 100) - commissionWithdrawFixed;
            if (profitMoney == null || profitMoney < profitMoneyForSpecifiedCommission) {
                profitMoney = profitMoneyForSpecifiedCommission;
            }


        }
        if (profitMoney == null) {
            //no suitable commission
            return false;
        }
        //we decrease on 100 to get net profit
        profitPercentage = (profitMoney / providedParams.getSumm()) * 100 - 100;

        return true;
    }


    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public Double getProfitMoney() {
        return profitMoney;
    }
}
