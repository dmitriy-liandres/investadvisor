package com.toolformoney.investarget.startups.shareinstock.model;

import com.toolformoney.ProvidedParams;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.startup.Startup;
import com.toolformoney.model.startup.StartupBroker;

import java.io.IOException;

/**
 * shareinstock.com
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class ShareInStock extends Startup {
    public ShareInStock() {
        super(null/*InvestmentTypeName.SHARE_IN_STOCK*/, "shareinstock", StartupBroker.SHARE_IN_STOCK, "/vklady-investitsii/startup/shareinstock/");
    }

    @Override
    public Double getCommissionEnterPercentage(ProvidedParams providedParams) {
        return 0.5;
    }

    @Override
    public Double getCommissionWithdrawPercentage(ProvidedParams providedParams) {
        return 0.5 + 5.;
    }

    @Override
    public Double getCommissionEnterFixed(ProvidedParams providedParams) throws IOException {
        return 0.;
    }

    @Override
    public Double getCommissionWithdrawFixed(ProvidedParams providedParams) throws IOException {
        return 0.;
    }
}
