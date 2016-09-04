package com.toolformoney.investarget.pamm.insolt.model;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class InsoltInvestmentPlan {
    private String name;
    private Double days;
    private Double managerCommission;
    private Integer insoltId;
    private Double minimalInvestment;
    private String startDate;
    private String link;

    public InsoltInvestmentPlan(String name, Double days, Double managerCommission, Integer insoltId, Double minimalInvestment, String startDate, String link) {
        this.name = name;
        this.days = days;
        this.managerCommission = managerCommission;
        this.insoltId = insoltId;
        this.minimalInvestment = minimalInvestment;
        this.startDate = startDate;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDays() {
        return days;
    }

    public void setDays(Double days) {
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
