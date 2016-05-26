package com.investadvisor.fxopen;

/**
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class PammFxOpenManagerMoney {

    private Double dailyGain;

    public Double getDailyGain() {
        return dailyGain;
    }

    public void setDailyGain(Double dailyGain) {
        this.dailyGain = dailyGain;
    }

    @Override
    public String toString() {
        return "PammFxOpenManagerMoneyOneDay{" +
                ", dailyGain=" + dailyGain +
                '}';
    }
}
