package com.investadvisor.model;

import com.investadvisor.ProvidedParams;

/**
 * Author Dmitriy Liandres
 * Date 26.05.2016
 */
public class PammCommission {

    private Double minInvestment;
    private Integer minPeriodInDays;
    private Double commissionFromProfit;

    public PammCommission(Double minInvestment, Integer minPeriodInDays, Double commissionFromProfit) {
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
    public Double calculateProfitAfterMangerCommission(Pamm pamm, ProvidedParams providedParams) {
        Double avgChange = pamm.getAvgChange();

        //pay attention, this value is not in percentage, it is in percentage/100;
        Double estimatedIncrease = Math.pow(1 + avgChange, providedParams.getPeriodInDays()) - 1;
        return 1 + estimatedIncrease * (1 - commissionFromProfit / 100);
    }


    @Override
    public String toString() {
        return "PammCommission{" +
                "minInvestment=" + minInvestment +
                ", minPeriodInDays=" + minPeriodInDays +
                ", commissionFromProfit=" + commissionFromProfit +
                '}';
    }
}
