package com.toolformoney.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.pamm.InvestmentTargetOffer;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public abstract class InvestmentTargetOfferProfit {


    /**
     * Commission is calculated based on visa/mastercard. This method must set profitMoney and profitPercentage
     *
     * @param investmentTarget investmentTarget
     * @param providedParams   provided params
     * @throws Exception
     */
    public InvestmentTargetOfferProfitCalculation calculateProfit(InvestmentTarget investmentTarget, InvestmentTargetOffer investmentTargetOffer, ProvidedParams providedParams) throws Exception {
        InvestmentTargetOfferProfitCalculation investmentTargetOfferProfitCalculation = new InvestmentTargetOfferProfitCalculation();
        //calculate commission
        Double commissionEnterPercentage = investmentTarget.getCommissionEnterPercentage(providedParams);
        Double commissionWithdrawPercentage = investmentTarget.getCommissionWithdrawPercentage(providedParams);

        Double commissionEnterFixed = investmentTarget.getCommissionEnterFixed(providedParams);
        Double commissionWithdrawFixed = investmentTarget.getCommissionWithdrawFixed(providedParams);

        //let's tale into consideration that pamm manager works with entered money - commission
        Double investedMoney = providedParams.getSumm() * (1 - commissionEnterPercentage / 100) - commissionEnterFixed;

        Double resultMoney = null;
        //check whether offer fits

        if ((investmentTargetOffer.getMaxInvestment() != null && investmentTargetOffer.getMinInvestment() > investedMoney)
                || (investmentTargetOffer.getMaxInvestment() != null && investmentTargetOffer.getMaxInvestment() < investedMoney)
                || (investmentTargetOffer.getMinPeriodInDays() != null && investmentTargetOffer.getMinPeriodInDays() > providedParams.getPeriodInDays())
                || (investmentTargetOffer.getMaxPeriodInDays() != null && investmentTargetOffer.getMaxPeriodInDays() < providedParams.getPeriodInDays())
                || investmentTargetOffer.getCurrency() != providedParams.getCurrency()) {
            investmentTargetOfferProfitCalculation.setIsSuitsUser(false);
            return investmentTargetOfferProfitCalculation;
        }

        Double calculatedFinalResultAfterMangerCommission = calculateProfitAfterMangerCommission(investmentTarget, investmentTargetOffer, providedParams);

        Double profitMoneyForSpecifiedCommission = investedMoney * (calculatedFinalResultAfterMangerCommission - 1);

        //let's take into consideration that user loses money when he withdraws money
        profitMoneyForSpecifiedCommission = profitMoneyForSpecifiedCommission * (1 - commissionWithdrawPercentage / 100) - commissionWithdrawFixed;

        resultMoney = profitMoneyForSpecifiedCommission;

        investmentTargetOfferProfitCalculation.setProfitMoney(resultMoney);
        //we decrease on 100 to get net profit
        investmentTargetOfferProfitCalculation.setProfitPercentage((resultMoney / providedParams.getSumm()) * 100);

        investmentTargetOfferProfitCalculation.setIsSuitsUser(true);

        return investmentTargetOfferProfitCalculation;
    }

    /**
     * Calculates final result (1+ increase) based on provided params
     *
     * @return commission
     */
    public Double calculateProfitAfterMangerCommission(InvestmentTarget investmentTarget, InvestmentTargetOffer investmentTargetOffer, ProvidedParams providedParams) {
        Double avgChange = investmentTargetOffer.getAvgChange();

        //pay attention, this value is not in percentage, it is in percentage/100;
        Double estimatedIncrease = Math.pow(1 + avgChange / 100, providedParams.getPeriodInDays()) - 1;
        return 1 + estimatedIncrease * (1 - investmentTargetOffer.getCommissionFromProfit() / 100);
    }

    @Override
    public String toString() {
        return "InvestmentTargetOfferProfit{}";
    }
}
