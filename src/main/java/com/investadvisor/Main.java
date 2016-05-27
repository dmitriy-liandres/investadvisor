package com.investadvisor;

import com.investadvisor.model.Pamm;
import com.investadvisor.model.PammProfit;
import com.investadvisor.model.PammRisk;
import com.investadvisor.model.PammRiskProfit;
import com.investadvisor.pamm.alfaForex.AlfaForexLoader;
import com.investadvisor.pamm.alpari.AlpariLoader;
import com.investadvisor.pamm.fxopen.FxOpenLoader;
import com.investadvisor.pamm.insolt.InsoltLoader;
import com.investadvisor.pamm.unitrade.UniTradeLoader;
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


        List<Pamm> pamms = new ArrayList<>();
        List<FutureTask<List<Pamm>>> pammLoadersFutureTasks = new ArrayList<>();

        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> InsoltLoader.getInstance().load()));
        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> UniTradeLoader.getInstance().load()));
        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> AlpariLoader.getInstance().load()));
        pammLoadersFutureTasks.add(createAndStartFutureTask(() -> AlfaForexLoader.getInstance().load()));
        //pammLoadersFutureTasks.add(createAndStartFutureTask(() -> FxOpenLoader.getInstance().load()));

        pammLoadersFutureTasks.forEach(pammLoadersFutureTask -> {
            try {
                pamms.addAll(pammLoadersFutureTask.get());
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
                List<PammRiskProfit> pammRisksProfit = new ArrayList<>();
                for (Pamm pamm : pamms) {
                    if (pamm.getCurrency() != currency) {
                        continue;
                    }

                    PammRisk pammRisk = new PammRisk();
                    pammRisk.calculateRisk(pamm, providedParams);
                    PammProfit pammProfit = new PammProfit();
                    Boolean isSuitsUser = pammProfit.calculateProfit(pamm, providedParams);
                    if (isSuitsUser) {
                        logger.info("pamm = {}, pammRisk = {}, pammProfit = {] ", pamm, pammRisk, pammProfit);
                        pammRisksProfit.add(new PammRiskProfit(pamm, pammRisk, pammProfit));
                    }
                }
                //filter by max allowed risk
                pammRisksProfit = pammRisksProfit.stream().filter(pammRisks -> pammRisks.getPammRisk().getTotalRisk() <= providedParams.getMaxAllowedRisk()).collect(Collectors.toList());
                pammRisksProfit.sort((o1, o2) -> o1.getPammRisk().getTotalRisk().compareTo(o2.getPammRisk().getTotalRisk()));
                System.out.println(pammRisksProfit);
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
