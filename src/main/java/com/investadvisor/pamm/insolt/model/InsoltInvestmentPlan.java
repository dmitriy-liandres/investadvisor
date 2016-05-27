package com.investadvisor.pamm.insolt.model;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class InsoltInvestmentPlan {
    private String name;
    private Integer days;
    private Double managerCommission;
    private Integer insoltId;
    private Double minimalInvestment;
    private String startDate;

    public InsoltInvestmentPlan(String name, Integer days, Double managerCommission, Integer insoltId, Double minimalInvestment, String startDate) {
        this.name = name;
        this.days = days;
        this.managerCommission = managerCommission;
        this.insoltId = insoltId;
        this.minimalInvestment = minimalInvestment;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Double getManagerCommission() {
        return managerCommission;
    }

    public void setManagerCommission(Double managerCommission) {
        this.managerCommission = managerCommission;
    }

    public Integer getInsoltId() {
        return insoltId;
    }

    public void setInsoltId(Integer insoltId) {
        this.insoltId = insoltId;
    }

    public Double getMinimalInvestment() {
        return minimalInvestment;
    }

    public void setMinimalInvestment(Double minimalInvestment) {
        this.minimalInvestment = minimalInvestment;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "InsoltInvestmentPlan{" +
                "name='" + name + '\'' +
                ", days=" + days +
                ", managerCommission=" + managerCommission +
                ", insoltId=" + insoltId +
                ", minimalInvestment=" + minimalInvestment +
                ", startDate=" + startDate +
                '}';
    }
}
