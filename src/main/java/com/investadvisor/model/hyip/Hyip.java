package com.investadvisor.model.hyip;

import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentTargetProfit;
import com.investadvisor.model.InvestmentTargetRisk;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public abstract class Hyip extends InvestmentTarget {

    public Hyip(InvestmentTargetRisk investmentTargetRisk,
                InvestmentTargetProfit investmentTargetProfit) {
        super(investmentTargetRisk, investmentTargetProfit);
    }

    @Override
    public Boolean isHyip() {
        return true;
    }

    @Override
    public String toString() {
        return "Hyip{} " + super.toString();
    }
}
