package com.investadvisor;

import com.investadvisor.hyip.itcTravel.ItcTravelLoader;
import com.investadvisor.hyip.itcTravel.model.ItcTravel;
import com.investadvisor.hyip.yabankir.YaBankirLoader;
import com.investadvisor.model.InvestmentTarget;
import com.investadvisor.model.InvestmentTargetProfit;
import com.investadvisor.model.pamm.Pamm;
import com.investadvisor.pamm.insolt.InsoltLoader;
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


        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> InsoltLoader.getInstance().load()));
        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> UniTradeLoader.getInstance().load()));
        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> AlpariLoader.getInstance().load()));
        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> AlfaForexLoader.getInstance().load()));
        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> FxOpenLoader.getInstance().load()));

        //investmentTargets.addAll(YaBankirLoader.getInstance().load());
        investmentTargets.addAll(ItcTravelLoader.getInstance().load());
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
                List<InvestmentTarget> investmentTargetCalculated = new ArrayList<>();
                for (InvestmentTarget investmentTarget : investmentTargets) {
                    if (investmentTarget.getCurrency() != currency) {
                        continue;
                    }

                    investmentTarget.getInvestmentTargetRisk().calculateAndSetRisk(investmentTarget, providedParams);
                    InvestmentTargetProfit investmentTargetProfit = investmentTarget.getInvestmentTargetProfit();
                    Boolean isSuitsUser = investmentTargetProfit.calculateProfit(investmentTarget, providedParams);
                    if (isSuitsUser) {
                        logger.info("investmentTarget = {} ", investmentTarget);
                        investmentTargetCalculated.add(investmentTarget);
                    }
                }
                //filter by max allowed risk
                investmentTargetCalculated = investmentTargetCalculated.stream().filter(pammRisks -> pammRisks.getInvestmentTargetRisk().getTotalRisk() <= providedParams.getMaxAllowedRisk()).collect(Collectors.toList());
                investmentTargetCalculated.sort((o1, o2) -> o1.getInvestmentTargetRisk().getTotalRisk().compareTo(o2.getInvestmentTargetRisk().getTotalRisk()));
                System.out.println(investmentTargetCalculated);
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
