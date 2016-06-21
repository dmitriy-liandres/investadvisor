package com.investadvisor.model.hyip;

import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentType;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public abstract class Hyip extends InvestmentTarget {

    public Hyip(String name) {
        super(InvestmentType.HYIP, name, "/хайп/");
    }


    @Override
    public String toString() {
        return "Hyip{} " + super.toString();
    }
}
