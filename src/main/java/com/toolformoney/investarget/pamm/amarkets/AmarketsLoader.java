package com.toolformoney.investarget.pamm.amarkets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.inject.Singleton;
import com.toolformoney.Currency;
import com.toolformoney.investarget.pamm.DailyChange;
import com.toolformoney.investarget.pamm.amarkets.model.AmarketsPamm;
import com.toolformoney.investarget.pamm.amarkets.model.AmarketsPammGeneraLInfo;
import com.toolformoney.investarget.pamm.amarkets.model.AmarketsPammInfo;
import com.toolformoney.model.InvestmentTypeName;
import com.toolformoney.model.forex.ForexLoader;
import com.toolformoney.model.forex.ForexOfferProfit;
import com.toolformoney.model.forex.ForexOfferRisk;
import com.toolformoney.model.pamm.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads Pamms for http://www.amarkets.org/investment/pamm/reyting/
 * Author Dmitriy Liandres
 * Date 11.11.2016
 */
@Singleton
public class AmarketsLoader extends ForexLoader {

    private static final Logger logger = LoggerFactory.getLogger(AmarketsLoader.class);

    @Override
    public List<Pamm> load() throws IOException {
        logger.info("Download all Amarkets managers");
        List<Pamm> pamms = new ArrayList<>();

        //this url returns all pamms for alpari
        URL urlAll = new URL("https://my.amarkets.org/api/v1/pamm_rating?sort=profit&direction=desc&name=");

        //maps returned json to the object
        List<AmarketsPammGeneraLInfo> allValues = objectMapper.readValue(urlAll, new TypeReference<List<AmarketsPammGeneraLInfo>>() {
        });
        logger.info("Loaded all Amarkets managers");
        for (AmarketsPammGeneraLInfo onePammManager : allValues) {
            try {
                logger.info("load Privatefx  Amarkets " + onePammManager.getName() + " (" + onePammManager.getId() + ")");
                URL urlOne = new URL("https://www.amarkets.org/investment/pamm/reyting/" + onePammManager.getId() + "/json/");

                //maps returned json to the object
                AmarketsPammInfo oneValue = objectMapper.readValue(urlOne, AmarketsPammInfo.class);

                Pamm pamm = new AmarketsPamm();
                pamm.setPammBroker(PammBroker.AMARKETS);
                pamm.setAgeInDays(oneValue.getTrading_days());
                pamm.setManagerMoney(oneValue.getCurrent_mt_balance());
                pamm.setTotalMoney(oneValue.getCurrent_mt_balance() + oneValue.getCapital());
                pamm.setId(onePammManager.getId());

                List<DailyChange> changes = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(oneValue.getGraph_profits())) {
                    //sort from the earlist
                    oneValue.getGraph_profits().sort((o1, o2) -> Long.valueOf(o1.get(0)).compareTo(Long.valueOf(o2.get(0))));
                    LocalDate previousLocalDate = null;
                    List<Double> previousChanges = new ArrayList<>();
                    for (List<String> graphProfit : oneValue.getGraph_profits()) {
                        Long dateLong = Long.valueOf(graphProfit.get(0));
                        LocalDate currentLocalDate = Instant.ofEpochMilli(dateLong).atZone(ZoneId.systemDefault()).toLocalDate();
                        if (previousLocalDate != null && currentLocalDate.isAfter(previousLocalDate)) {
                            Double totalDayProfit = 1.;
                            for (Double previousChange : previousChanges) {
                                totalDayProfit *= 1 + previousChange / 100;
                            }
                            DailyChange dailyChange = new DailyChange(previousLocalDate, (totalDayProfit - 1) * 100);
                            changes.add(dailyChange);
                            previousChanges.clear();
                            previousLocalDate = currentLocalDate;
                        }

                        if (previousLocalDate == null) {
                            previousLocalDate = currentLocalDate;
                        }
                        Double profit = Double.valueOf(graphProfit.get(1));
                        previousChanges.add(profit);
                    }
                    Double avgChange = addChangesToForexTrades(changes, pamm);
                    oneValue.getPercent_rates().forEach(percentRate -> {
                        Double minInvestment = percentRate.get(0);
                        Double percentage = percentRate.get(1);
                        pamm.addOffer(new InvestmentTargetOffer(oneValue.getName(), minInvestment, null, oneValue.getPayment_period(), null, percentage, "https://www.amarkets.org/investment/pamm/reyting/" + pamm.getId() + "/g/4MCP6",
                                Currency.USD, avgChange, new PammOfferRisk(), new ForexOfferProfit()));
                    });


                    pamms.add(pamm);
                }
            } catch (Exception e) {
                logger.error("Impossible to load amarkets PAMM, onePammManager = {}", onePammManager, e);
            }

        }


        logger.info("Finish download all Amarkets managers");
        return pamms;
    }

    @Override
    public InvestmentTypeName getInvestmentTypeName() {
        return null/*InvestmentTypeName.AMARKETS*/;
    }
}
