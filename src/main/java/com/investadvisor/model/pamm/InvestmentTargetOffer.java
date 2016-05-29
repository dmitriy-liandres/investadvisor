package com.investadvisor.model.pamm;

import com.investadvisor.ProvidedParams;
import com.investadvisor.model.InvestmentTarget;

/**
 * Author Dmitriy Liandres
 * Date 26.05.2016
 */
public class InvestmentTargetOffer {

    private Double minInvestment;
    private Integer minPeriodInDays;
    private Double commissionFromProfit;

    public InvestmentTargetOffer(Double minInvestment, Integer minPeriodInDays, Double commissionFromProfit) {
        this.minInvestment = minInvestment;
        this.minPeriodInDays = minPeriodInDays;
        this.commissionFromProfit = commissionFromProfit;
    }

    public Integer getMinPeriodInDays() {
        return minPeriodInDays;
    }

    public void setMinPeriodInDays(Integer minPeriodInDays) {
        this.minPeriodInDays = minPeriodInDays;
    }

    public Double getMinInvestment() {
        return minInvestment;
    }

    public void setMinInvestment(Double minInvestment) {
        this.minInvestment = minInvestment;
    }

    public Double getCommissionFromProfit() {
        return commissionFromProfit;
    }

    public void setCommissionFromProfit(Double commissionFromProfit) {
        this.commissionFromProfit = commissionFromProfit;
    }

    /**
     * Calculates final result (1+ increase) based on provided params
     *
     * @return commission
     */
    public Double calculateProfitAfterMangerCommission(InvestmentTarget investmentTarget, ProvidedParams providedParams) {
        Double avgChange = investmentTarget.getAvgChange();

        //pay attention, this value is not in percentage, it is in percentage/100;
        Double estimatedIncrease = Math.pow(1 + avgChange / 100, providedParams.getPeriodInDays()) - 1;
        return 1 + estimatedIncrease * (1 - commissionFromProfit / 100);
    }


    @Override
    public String toString() {
        return "PammOffer{" +
                "minInvestment=" + minInvestment +
                ", minPeriodInDays=" + minPeriodInDays +
                ", commissionFromProfit=" + commissionFromProfit +
                '}';
    }
}
