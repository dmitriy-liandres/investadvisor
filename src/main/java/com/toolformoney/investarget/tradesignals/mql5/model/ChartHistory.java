package com.toolformoney.investarget.tradesignals.mql5.model;

/**
 * Author Dmitriy Liandres
 * Date 25.12.2016
 */
public class ChartHistory {
    private Double averageLot;
    private Double addsNumber = 0.;


    public Double getAverageLot() {
        return averageLot;
    }

    public Double getAddsNumber() {
        return addsNumber;
    }

    public void setAverageLot(Double averageLot) {
        this.averageLot = averageLot;
    }


    public void incAdds() {
        this.addsNumber += 1;
    }
}
