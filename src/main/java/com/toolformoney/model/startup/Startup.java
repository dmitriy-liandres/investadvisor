package com.toolformoney.model.startup;

import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.InvestmentType;
import com.toolformoney.model.InvestmentTypeName;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public abstract class Startup extends InvestmentTarget {
    private StartupBroker startupBroker;


    public Startup(InvestmentTypeName investmentTypeName, String name, StartupBroker startupBroker, String investmentPartnerLink) {
        super(InvestmentType.STARTUP, investmentTypeName, name, "/vklady-investitsii/startup/", investmentPartnerLink);
        this.startupBroker = startupBroker;
    }

    public StartupBroker getStartupBroker() {
        return startupBroker;
    }

    @Override
    public String toString() {
        return "Startup{" +
                "startupBroker=" + startupBroker +
                "} " + super.toString();
    }
}
