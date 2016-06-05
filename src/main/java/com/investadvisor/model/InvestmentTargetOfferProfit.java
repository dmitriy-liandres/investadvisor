package com.investadvisor.model;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.pamm.InvestmentTargetOffer;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public abstract class InvestmentTargetOfferProfit {


    private Double profitPercentage;
    private Double profitMoney;


    protected void setProfitPercentage(Double profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    protected void setProfitMoney(Double profitMoney) {
        this.profitMoney = profitMoney;
    }

    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public Double getProfitMoney() {
        return profitMoney;
    }


    /**
     * Commission is calculated based on visa/mastercard. This method must set profitMoney and profitPercentage
     *
     * @param investmentTarget investmentTarget
     * @param providedParams   provided params
     * @return true if investmentTarget suits user, false otherwise
     * @throws Exception
     */
    public Boolean calculateProfit(InvestmentTarget investmentTarget, ProvidedParams providedParams) throws Exception {

        //calculate commission
        Double commissionEnterPercentage = investmentTarget.getCommissionEnterPercentage();
        Double commissionWithdrawPercentage = investmentTarget.getCommissionWithdrawPercentage();

        Double commissionEnterFixed = investmentTarget.getCommissionEnterFixed(providedParams.getCurrency());
        Double commissionWithdrawFixed = investmentTarget.getCommissionWithdrawFixed(providedParams.getCurrency());

        //let's tale into consideration that pamm manager works with entered money - commission
        Double investedMoney = providedParams.getSumm() * (1 - commissionEnterPercentage / 100) - commissionEnterFixed;

        Double resultMoney = null;
        //select the best offer
        for (InvestmentTargetOffer investmentTargetOffer : investmentTarget.getInvestmentTargetOffers()) {
            if (investmentTargetOffer.getMinInvestment() > investedMoney || investmentTargetOffer.getMinPeriodInDays() > providedParams.getPeriodInDays()) {
                continue;
            }

            Double calculatedFinalResultAfterMangerCommission = calculateProfitAfterMangerCommission(investmentTarget, investmentTargetOffer, providedParams);

            Double profitMoneyForSpecifiedCommission = investedMoney * (calculatedFinalResultAfterMangerCommission - 1);

            //let's tale into consideration that user loses money when he withdraws money
            profitMoneyForSpecifiedCommission = profitMoneyForSpecifiedCommission * (1 - commissionWithdrawPercentage / 100) - commissionWithdrawFixed;
            if (resultMoney == null || resultMoney < profitMoneyForSpecifiedCommission) {
                resultMoney = profitMoneyForSpecifiedCommission;
            }


        }
        if (resultMoney == null) {
            //no suitable commission
            return false;
        }

        setProfitMoney(resultMoney);
        //we decrease on 100 to get net profit
        setProfitPercentage((resultMoney / providedParams.getSumm()) * 100);

        return true;
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
        return "InvestmentTargetProfit{" +
                "profitPercentage=" + profitPercentage +
                ", profitMoney=" + profitMoney +
                '}';
    }
}
