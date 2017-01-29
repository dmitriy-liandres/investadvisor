package com.toolformoney.model.pamm;

import com.toolformoney.model.ForexInvestmentTarget;
import com.toolformoney.model.InvestmentType;
import com.toolformoney.model.InvestmentTypeName;

/**
 * Base Bean for all PAMMS and PAMM based companies
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
public abstract class Pamm extends ForexInvestmentTarget {

    private PammBroker pammBroker;

    public Pamm(InvestmentTypeName investmentTypeName, String name, String investmentPartnerLink) {
        super(InvestmentType.PAMM, investmentTypeName, name, "/vklady-investitsii/pamm/", investmentPartnerLink);
    }

    public PammBroker getPammBroker() {
        return pammBroker;
    }

    public void setPammBroker(PammBroker pammBroker) {
        this.pammBroker = pammBroker;
    }

    @Override
    public String toString() {
        return "Pamm{" +
                "pammBroker=" + pammBroker +
                "} " + super.toString();
    }
}
