package com.toolformoney.investarget.pamm.fxopen.model;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class FXOpenDepositLoad {

    private String date;
    private Double value;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FXOpenDepositLoad{" +
                "date='" + date + '\'' +
                ", value=" + value +
                '}';
    }
}
