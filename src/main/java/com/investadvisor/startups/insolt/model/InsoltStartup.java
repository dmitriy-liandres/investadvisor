package com.investadvisor.startups.insolt.model;

import com.investadvisor.Currency;
import com.investadvisor.model.startup.Startup;
import com.investadvisor.model.startup.StartupBroker;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class InsoltStartup extends Startup {
    public InsoltStartup() {
        super("Insolt", StartupBroker.INSOLT);
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 2.24;
    }

    @Override
    public Double getCommissionWithdrawPercentage() {
        return 2.;
    }

    @Override
    public Double getCommissionEnterFixed(Currency currency) throws IOException {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed(Currency currency) throws IOException {
        return 0.;
    }
}
