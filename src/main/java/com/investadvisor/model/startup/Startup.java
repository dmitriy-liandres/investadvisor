package com.investadvisor.model.startup;

import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentType;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public abstract class Startup extends InvestmentTarget {
    private StartupBroker startupBroker;


    public Startup(String name, StartupBroker startupBroker) {
        super(InvestmentType.STARTUP, name);
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
