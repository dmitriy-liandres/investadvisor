package com.toolformoney.investarget.startups.shareinstock;

import com.google.inject.Singleton;
import com.toolformoney.model.InvestmentTargetLoader;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.startup.Startup;
import com.toolformoney.model.startup.StartupDetails;
import com.toolformoney.model.startup.StartupOffer;
import com.toolformoney.model.startup.StartupOfferRisk;
import com.toolformoney.investarget.startups.shareinstock.model.ShareInStock;
import com.toolformoney.investarget.startups.shareinstock.model.ShareInStockOfferProfit;
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
public class ShareInStockLoader extends InvestmentTargetLoader<Startup> {

    private static final Logger logger = LoggerFactory.getLogger(ShareInStockLoader.class);

    private static final List<StartupDetails> startups = new ArrayList<>();

    static {
        //it is possible to invest on less than 30 days, but use will not get any profit,only loss
        startups.add(new StartupDetails("Интеллектуальная онлайн игра LargeIQ", "https://shareinstock.com/exchange/271_LargeIQ?source=partners&medium=revshare&campaign=rocg9ktnl8", 100000., 2., 65.4, 9.7, null, new StartupOfferRisk(true, 5., 2.)));
        startups.add(new StartupDetails("Каталог автосервисов Vse-sto", "https://shareinstock.com/exchange/181_Vse-sto?source=partners&medium=revshare&campaign=x8iy5kgvs6", 205496.0, 5., 86.7, 16.5, null, new StartupOfferRisk(true, 3., 1.5)));
        startups.add(new StartupDetails("NigmaBet - прогнозы на спорт", "https://shareinstock.com/exchange/601_NigmaBet?source=partners&medium=revshare&campaign=zrtd9wyku3", 50000., 1., 46.4, 12.8, null, new StartupOfferRisk(true, 5., 1.)));
        startups.add(new StartupDetails("Академия практического трейдинга", "https://shareinstock.com/lp/aptumg?source=partners&medium=revshare&campaign=s6x7z0wblk", 100000., 2., 65.7, 0., null, new StartupOfferRisk(true, 5., 2.)));
        startups.add(new StartupDetails("РВХ-Сервис", "https://shareinstock.com/exchange/421_RVHS?source=partners&medium=revshare&campaign=1hjl6megb8", 200000., 1., 94.2, 0., null, new StartupOfferRisk(true, 7., 2.)));
        startups.add(new StartupDetails("Школа Инвесторов \"Рантье\"", "https://shareinstock.com/lp/rantie/?source=partners&medium=revshare&campaign=6x2gsd1n9h", 100027.20, 2.4, 8.3, 2.9536, null, new StartupOfferRisk(false, 5., 2.)));
        startups.add(new StartupDetails("Мобильное приложение для навигации в мире шоппинга и развлечений QROK", "https://shareinstock.com/exchange/122_QROK?source=partners&medium=revshare&campaign=xutbg47ie6", 2000030., 10., 29.2, 13.8, null, new StartupOfferRisk(true, 4., 1.)));
        startups.add(new StartupDetails("Юзерклик", "https://shareinstock.com/exchange/208_yuzerklik?source=partners&medium=revshare&campaign=tnzfh4gdmo", 29990., 10., 21.1, 12.1, null, new StartupOfferRisk(true, 4., 1.5)));
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

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return null;//InvestmentTypeName.SHARE_IN_STOCK;
    }
}
