package com.toolformoney.investarget.pamm;

import java.time.LocalDate;

/**
 * Author Dmitriy Liandres
 * Date 18.09.2016
 */
public class DailyChange {
    private LocalDate date;
    private Double change;

    public DailyChange(LocalDate date, Double change) {
        this.date = date;
        this.change = change;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getChange() {
        return change;
    }
}
