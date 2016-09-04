package com.toolformoney.investarget.pamm.fxopen.model;

import com.toolformoney.Currency;

/**
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class FXOpenManagerGeneral {

    private String id;
    private String name;

    //days work
    private Integer days;
    //total money
    private Double balance;

    //manager capital
    private Double mastercapital;

    private Currency currency;

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

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getMastercapital() {
        return mastercapital;
    }

    public void setMastercapital(Double mastercapital) {
        this.mastercapital = mastercapital;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


}
