package com.toolformoney.investarget.pamm.weltrade.model;

import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 25.09.2016
 */
public class WeltradeData {

    private List<WeltradeItem> items;

    public List<WeltradeItem> getItems() {
        return items;
    }

    public void setItems(List<WeltradeItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "WeltradeData{" +
                "items=" + items +
                '}';
    }
}
