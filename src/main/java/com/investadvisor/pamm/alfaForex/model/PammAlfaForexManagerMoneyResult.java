package com.investadvisor.pamm.alfaForex.model;

import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class PammAlfaForexManagerMoneyResult {

    private List<PammAlfaForexManagerMoneyResultItem> items;

    public List<PammAlfaForexManagerMoneyResultItem> getItems() {
        return items;
    }

    public void setItems(List<PammAlfaForexManagerMoneyResultItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PammAlfaForexManagerMoneyResult{" +
                "items=" + items +
                '}';
    }
}
