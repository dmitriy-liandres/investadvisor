package com.investadvisor.pamm.alfaForex.model;

import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class PammAlfaForexManagerMoney {

    private List<PammAlfaForexManagerMoneyResultItem> result;

    public List<PammAlfaForexManagerMoneyResultItem> getResult() {
        return result;
    }

    public void setResult(List<PammAlfaForexManagerMoneyResultItem> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PammAlfaForexManagerMoney{" +
                "result=" + result +
                '}';
    }
}
