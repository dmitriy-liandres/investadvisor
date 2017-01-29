package com.toolformoney.investarget.startups.insolt.model;

import com.toolformoney.Currency;
import com.toolformoney.ProvidedParams;
import com.toolformoney.model.startup.Startup;
import com.toolformoney.model.startup.StartupBroker;

import java.io.IOException;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class InsoltStartup extends Startup {
    public InsoltStartup() {
        super(null, "Insolt", StartupBroker.INSOLT, "/vklady-investitsii/startup/insolt-startup/");
    }

    @Override
    public Double getCommissionEnterPercentage( ProvidedParams providedParams) {
        return 2.24;
    }

    @Override
    public Double getCommissionWithdrawPercentage( ProvidedParams providedParams) {
        return 2.;
    }

    @Override
    public Double getCommissionEnterFixed( ProvidedParams providedParams) throws IOException {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed( ProvidedParams providedParams) throws IOException {
        return 0.;
    }
}
