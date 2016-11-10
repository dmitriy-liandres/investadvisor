package com.toolformoney.model.hyip;

import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.InvestmentType;
import com.toolformoney.model.InvestmentTypeName;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public abstract class Hyip extends InvestmentTarget {

    public Hyip(InvestmentTypeName investmentTypeName, String name, String investmentPartnerLink) {
        super(InvestmentType.HYIP, investmentTypeName, name, "/vklady-investitsii/hyip/", investmentPartnerLink);
    }


    @Override
    public String toString() {
        return "Hyip{} " + super.toString();
    }
}
