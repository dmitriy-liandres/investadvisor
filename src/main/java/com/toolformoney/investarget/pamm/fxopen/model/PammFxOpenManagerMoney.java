package com.toolformoney.investarget.pamm.fxopen.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PammFxOpenManagerMoney {

    private String date;
    private Double dailyGain;
    private Double totalGain;

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

    public Double getTotalGain() {
        return totalGain;
    }

    public void setTotalGain(Double totalGain) {
        this.totalGain = totalGain;
    }

    @Override
    public String toString() {
        return "PammFxOpenManagerMoney{" +
                "date='" + date + '\'' +
                ", dailyGain=" + dailyGain +
                ", totalGain=" + totalGain +
                '}';
    }
}
