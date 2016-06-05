package com.investadvisor.startups.shareinstock;

import com.investadvisor.model.InvestmentTargetLoader;
import com.investadvisor.model.startup.Startup;
import com.investadvisor.model.startup.StartupDetails;
import com.investadvisor.model.startup.StartupOffer;
import com.investadvisor.model.startup.StartupOfferRisk;
import com.investadvisor.startups.shareinstock.model.ShareInStock;
import com.investadvisor.startups.shareinstock.model.ShareInStockOfferProfit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class ShareInStockLoader extends InvestmentTargetLoader<Startup> {

    private static final Logger logger = LoggerFactory.getLogger(ShareInStockLoader.class);

    private static final List<StartupDetails> startups = new ArrayList<>();

    static {
        //it is possible to invest on less than 30 days, but use will not get any profit,only loss
        startups.add(new StartupDetails("Интеллектуальная онлайн игра LargeIQ", "https://shareinstock.com/exchange/271_LargeIQ?source=partners&medium=revshare&campaign=rocg9ktnl8", 100000., 2., 64.4, 7.784, null, new StartupOfferRisk(true, 5., 2.)));
        startups.add(new StartupDetails("Каталог автосервисов Vse-sto", "https://shareinstock.com/exchange/181_Vse-sto?source=partners&medium=revshare&campaign=x8iy5kgvs6", 205496.0, 5., 86.3, 19.19, null, new StartupOfferRisk(true, 3., 1.5)));
        startups.add(new StartupDetails("NigmaBet - прогнозы на спорт", "https://shareinstock.com/exchange/601_NigmaBet?source=partners&medium=revshare&campaign=zrtd9wyku3", 50000., 1., 42.3, 0., null, new StartupOfferRisk(true, 5., 1.)));
        startups.add(new StartupDetails("Академия практического трейдинга", "https://shareinstock.com/lp/aptumg?source=partners&medium=revshare&campaign=s6x7z0wblk", 100000., 2., 65.7, 0., null, new StartupOfferRisk(true, 5., 2.)));
        startups.add(new StartupDetails("РВХ-Сервис", "https://shareinstock.com/exchange/421_RVHS?source=partners&medium=revshare&campaign=1hjl6megb8", 200000., 1., 78.9, 0., null, new StartupOfferRisk(true, 7., 2.)));
        startups.add(new StartupDetails("Школа Инвесторов \"Рантье\"", "https://shareinstock.com/lp/rantie/?source=partners&medium=revshare&campaign=6x2gsd1n9h", 100027.20, 2.4, 8.3, 2.9536, null, new StartupOfferRisk(false, 5., 2.)));
        startups.add(new StartupDetails("Мобильное приложение для навигации в мире шоппинга и развлечений QROK", "https://shareinstock.com/exchange/122_QROK?source=partners&medium=revshare&campaign=xutbg47ie6", 2000030., 10., 29.1, 3.846, null, new StartupOfferRisk(true, 4., 1.)));
        startups.add(new StartupDetails("Юзерклик", "https://shareinstock.com/exchange/208_yuzerklik?source=partners&medium=revshare&campaign=tnzfh4gdmo", 29990., 10., 21., 0., null, new StartupOfferRisk(true, 4., 1.5)));
    }


    private static ShareInStockLoader instance = new ShareInStockLoader();

    //the way to make it singleton. When we add Guice, we should update this code to remove instance
    public static ShareInStockLoader getInstance() {
        return instance;
    }

    private ShareInStockLoader() {
    }


    @Override
    public List<Startup> load() throws IOException {
        logger.info("Start download all offers for shareinstock.com");
        List<Startup> result = new ArrayList<>();
        Startup startup = new ShareInStock();

        startups.forEach(oneStartup -> {
            StartupOffer startupOffer = new StartupOffer(oneStartup.getName(), oneStartup.getMinInvestment(), oneStartup.getTotalPrice() * (100 - oneStartup.getFilledPercentage()) / 100, oneStartup.getStartupOfferRisk().getAvgPercentagePerMonth(), oneStartup.getUrl(), oneStartup.getStartupOfferRisk(), new ShareInStockOfferProfit(oneStartup.getAskBidDifferenceInPercentages()));
            startup.addOffer(startupOffer);
        });

        result.add(startup);

        logger.info("Finish download all offers for shareinstock.com");
        return result;
    }
}
