package com.toolformoney.investarget.pamm.alfaForex.model;

/**
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class PammAlfaForexManagerMoneyResultItem {

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
        return "PammAlfaForexManagerMoneyResultItem{" +
                "date='" + date + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
