package com.toolformoney.investarget.pamm.weltrade.model;

/**
 * Author Dmitriy Liandres
 * Date 25.09.2016
 */
public class WeltradeItem {
    private WeltradeItemCode code;

    public WeltradeItemCode getCode() {
        return code;
    }

    public void setCode(WeltradeItemCode code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "WeltradeItem{" +
                "code=" + code +
                '}';
    }
}
