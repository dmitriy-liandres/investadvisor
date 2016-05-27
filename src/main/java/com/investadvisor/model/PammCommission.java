package com.investadvisor.model;

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
     * Calculates final result (1+ increase) based on estimated increase and manager commissions
     *
     * @return commission
     */
    public Double calculateFinalResultAfterMangerCommission(Double estimatedIncrease) {
        return 1 + estimatedIncrease * (1 - commissionFromProfit / 100);
    }

    /**
     * Some prokers takes daily fees, so we have to adjust avgChange
     *
     * @param avgChange average change
     * @return resulted average change
     */
    public Double adjustAvgChangeBasedOnCommission(Double avgChange) {
        return avgChange;
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
