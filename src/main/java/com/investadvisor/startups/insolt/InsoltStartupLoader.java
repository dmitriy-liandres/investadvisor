package com.investadvisor.startups.insolt;

import com.investadvisor.model.InvestmentTargetLoader;
import com.investadvisor.model.startup.Startup;
import com.investadvisor.model.startup.StartupDetails;
import com.investadvisor.model.startup.StartupOffer;
import com.investadvisor.model.startup.StartupOfferRisk;
import com.investadvisor.startups.insolt.model.InsoltStartupOfferProfit;
import com.investadvisor.startups.insolt.model.InsoltStartup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class InsoltStartupLoader extends InvestmentTargetLoader<Startup> {

    private static final Logger logger = LoggerFactory.getLogger(InsoltStartupLoader.class);

    private static final List<StartupDetails> startups = new ArrayList<>();

    static {
        //it is possible to invest on less than 30 days, but use will not get any profit,only loss
        startups.add(new StartupDetails("√ид по стилю", "http://www.insolt.com/startups/idetail.php?id=80&p=12588&lang=ru", 20000., 40., 45., 15.3, "01.07.2016", new StartupOfferRisk(false, 9., 1.5)));
    }


    private static InsoltStartupLoader instance = new InsoltStartupLoader();

    //the way to make it singleton. When we add Guice, we should update this code to remove instance
    public static InsoltStartupLoader getInstance() {
        return instance;
    }

    private InsoltStartupLoader() {
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
}
