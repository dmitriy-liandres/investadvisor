package com.investadvisor.model;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public enum InvestmentType {
    PAMM("ПАММ"), HYIP("Хайп"), STARTUP("Стартап"), BANK("Банк"), TRUST_MANAGEMENT("Доверительное управление");

    private String name;

    InvestmentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
