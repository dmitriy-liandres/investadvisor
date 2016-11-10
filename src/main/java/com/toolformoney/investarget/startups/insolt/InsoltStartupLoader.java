package com.toolformoney.investarget.startups.insolt;

import com.google.inject.Singleton;
import com.toolformoney.model.InvestmentTargetLoader;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.startup.Startup;
import com.toolformoney.model.startup.StartupDetails;
import com.toolformoney.model.startup.StartupOffer;
import com.toolformoney.model.startup.StartupOfferRisk;
import com.toolformoney.investarget.startups.insolt.model.InsoltStartupOfferProfit;
import com.toolformoney.investarget.startups.insolt.model.InsoltStartup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
@Singleton
public class InsoltStartupLoader extends InvestmentTargetLoader<Startup> {

    private static final Logger logger = LoggerFactory.getLogger(InsoltStartupLoader.class);

    private static final List<StartupDetails> startups = new ArrayList<>();

    static {
        //it is possible to invest on less than 30 days, but use will not get any profit,only loss
        startups.add(new StartupDetails("Гид по стилю", "http://www.insolt.com/startups/idetail.php?id=80&p=12588&lang=ru", 20000., 40., 45., 15.3, "01.07.2016", new StartupOfferRisk(false, 9., 1.5)));
    }

    @Override
    public List<Startup> load() throws IOException {
        logger.info("Start download all offers for insolt (startpus)");
        List<Startup> result = new ArrayList<>();
        Startup startup = new InsoltStartup();

        startups.forEach(oneStartup -> {
            StartupOffer startupOffer = new StartupOffer(oneStartup.getName(), oneStartup.getMinInvestment(), oneStartup.getTotalPrice() * (100 - oneStartup.getFilledPercentage()) / 100, oneStartup.getStartupOfferRisk().getAvgPercentagePerMonth(), oneStartup.getUrl(), oneStartup.getStartupOfferRisk(), new InsoltStartupOfferProfit(oneStartup.getAskBidDifferenceInPercentages(), oneStartup));
            startup.addOffer(startupOffer);
        });

        result.add(startup);

        logger.info("Finish download all offers for insolt (startpus)");
        return result;
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return null;//InvestmentTypeName.INSOLT_STARTUP;
    }
}
