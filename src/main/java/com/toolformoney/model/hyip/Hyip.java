package com.toolformoney.model.hyip;

import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.InvestmentType;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public abstract class Hyip extends InvestmentTarget {

    public Hyip(String name, String investmentPartnerLink) {
        super(InvestmentType.HYIP, name, "/vklady-investitsii/hyip/", investmentPartnerLink);
    }


    @Override
    public String toString() {
        return "Hyip{} " + super.toString();
    }
}
