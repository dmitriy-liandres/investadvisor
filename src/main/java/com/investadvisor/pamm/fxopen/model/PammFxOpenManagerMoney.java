package com.investadvisor.pamm.fxopen.model;

/**
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class PammFxOpenManagerMoney {

    private String date;
    private Double dailyGain;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDailyGain() {
        return dailyGain;
    }

    public void setDailyGain(Double dailyGain) {
        this.dailyGain = dailyGain;
    }

    @Override
    public String toString() {
        return "PammFxOpenManagerMoney{" +
                "date='" + date + '\'' +
                ", dailyGain=" + dailyGain +
                '}';
    }
}
