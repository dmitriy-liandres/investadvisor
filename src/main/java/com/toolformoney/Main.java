package com.toolformoney;

import com.toolformoney.model.InvestmentTarget;
import com.toolformoney.model.InvestmentTargetOfferProfit;
import com.toolformoney.model.pamm.InvestmentTargetOffer;
import com.toolformoney.model.pamm.Pamm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) throws Exception {


        List<InvestmentTarget> investmentTargets = new ArrayList<>();
        List<FutureTask<List<Pamm>>> pammLoadersFutureTasks = new ArrayList<>();

       ;
        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> InsoltLoader.getInstance().load()));
        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> UniTradeLoader.getInstance().load()));
        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> AlpariLoader.getInstance().load()));
        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> AlfaForexLoader.getInstance().load()));
        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> FxOpenLoader.getInstance().load()));

        //investmentTargets.addAll(YaBankirLoader.getInstance().load());
        //investmentTargets.addAll(ItcTravelLoader.getInstance().load());

       // investmentTargets.addAll( ShareInStockLoader.getInstance().load());
       // investmentTargets.addAll( InsoltStartupLoader.getInstance().load());
        //investmentTargets.addAll( SberbankLoader.getInstance().load());


        pammLoadersFutureTasks.forEach(pammLoadersFutureTask -> {
            try {
                investmentTargets.addAll(pammLoadersFutureTask.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Impossible to get pamms", e);
            }
        });
        Scanner scanIn = new Scanner(System.in);
        //calculate risk
        while (true) {
            try {
                System.out.println("enter summ");
                Double summ = Double.valueOf(scanIn.nextLine());
                System.out.println("enter days");
                Double periodInDays = Double.valueOf(scanIn.nextLine());
                System.out.println("enter currency");
                Currency currency = Currency.valueOf(scanIn.nextLine());
                System.out.println("max allowed risk");
                Integer maxAllowedRisk = Integer.valueOf(scanIn.nextLine());
                ProvidedParams providedParams = new ProvidedParams(summ, periodInDays, currency, maxAllowedRisk);

                //broker coefficient
                List<InvestmentTargetOffer> investmentTargetCalculatedOffers = new ArrayList<>();
                for (InvestmentTarget investmentTarget : investmentTargets) {
                    for (InvestmentTargetOffer offer : investmentTarget.getInvestmentTargetOffers()) {

                        InvestmentTargetOfferProfit investmentTargetOfferProfit = offer.getInvestmentTargetOfferProfit();
                        Boolean isSuitsUser = investmentTargetOfferProfit.calculateProfit(investmentTarget, providedParams);
                        if (isSuitsUser) {
                            logger.info("investmentTarget = {}, offer = {} ", investmentTarget, offer);
                            investmentTargetCalculatedOffers.add(offer);
                        }
                        offer.getInvestmentTargetOfferRisk().calculateAndSetRisk(investmentTarget, providedParams, investmentTargetOfferProfit);
                    }
                }
                //filter by max allowed risk
                investmentTargetCalculatedOffers = investmentTargetCalculatedOffers.stream().filter(offer -> offer.getInvestmentTargetOfferRisk().getTotalRisk() <= providedParams.getMaxAllowedRisk()).collect(Collectors.toList());
                investmentTargetCalculatedOffers.sort((offer1, offer2) -> offer1.getInvestmentTargetOfferRisk().getTotalRisk().compareTo(offer2.getInvestmentTargetOfferRisk().getTotalRisk()));
                System.out.println(investmentTargetCalculatedOffers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static FutureTask<List<Pamm>> createAndStartFutureTask(Callable<List<Pamm>> callable) {
        FutureTask<List<Pamm>> task = new FutureTask<>(callable);
        Thread t = new Thread(task);
        t.start();
        return task;
    }

}
