package com.toolformoney.investarget.pamm.weltrade.model;

/**
 * Author Dmitriy Liandres
 * Date 25.09.2016
 */
public class WeltradeAllPamsResult {
    private WeltradeData data;

    public WeltradeData getData() {
        return data;
    }

    public void setData(WeltradeData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WeltradeAllPamsResult{" +
                "data=" + data +
                '}';
    }
}
