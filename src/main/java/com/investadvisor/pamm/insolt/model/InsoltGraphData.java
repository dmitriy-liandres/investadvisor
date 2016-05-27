package com.investadvisor.pamm.insolt.model;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class InsoltGraphData {

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
        return "InsoltGraphData{" +
                "date='" + date + '\'' +
                ", value=" + value +
                '}';
    }
}
