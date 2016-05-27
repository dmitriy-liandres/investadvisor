package com.investadvisor.pamm.fxopen.model;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class FxOpenOffersResultData {
    private String id;
    private String name;
    private String offerTypeStr;//Active
    private Double performanceFee;
    private Double managementFee;
    private Double minimumPerformanceConstraint;
    private Double depositCommision;
    private Double initialDeposit;
    private Integer interval;

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfferTypeStr() {
        return offerTypeStr;
    }

    public void setOfferTypeStr(String offerTypeStr) {
        this.offerTypeStr = offerTypeStr;
    }

    public Double getPerformanceFee() {
        return performanceFee;
    }

    public void setPerformanceFee(Double performanceFee) {
        this.performanceFee = performanceFee;
    }

    public Double getManagementFee() {
        return managementFee;
    }

    public void setManagementFee(Double managementFee) {
        this.managementFee = managementFee;
    }

    public Double getMinimumPerformanceConstraint() {
        return minimumPerformanceConstraint;
    }

    public void setMinimumPerformanceConstraint(Double minimumPerformanceConstraint) {
        this.minimumPerformanceConstraint = minimumPerformanceConstraint;
    }

    public Double getDepositCommision() {
        return depositCommision;
    }

    public void setDepositCommision(Double depositCommision) {
        this.depositCommision = depositCommision;
    }

    public Double getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(Double initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    @Override
    public String toString() {
        return "FxOpenOffersResultData{" +
                "interval=" + interval +
                ", initialDeposit=" + initialDeposit +
                ", depositCommision=" + depositCommision +
                ", minimumPerformanceConstraint=" + minimumPerformanceConstraint +
                ", managementFee=" + managementFee +
                ", performanceFee=" + performanceFee +
                ", offerTypeStr='" + offerTypeStr + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
