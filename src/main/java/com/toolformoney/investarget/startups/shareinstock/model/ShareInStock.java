package com.toolformoney.investarget.startups.shareinstock.model;

import com.toolformoney.Currency;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.startup.Startup;
import com.toolformoney.model.startup.StartupBroker;

import java.io.IOException;

/**   shareinstock.com
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class ShareInStock extends Startup {
    public ShareInStock() {
        super(InvestmentTypeName.SHARE_IN_STOCK, "shareinstock" , StartupBroker.SHARE_IN_STOCK, "/vklady-investitsii/startup/shareinstock/");
    }

    @Override
    public Double getCommissionEnterPercentage() {
        return 0.5;
    }

    @Override
    public Double getCommissionWithdrawPercentage() {
        return 0.5 + 5.;
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
