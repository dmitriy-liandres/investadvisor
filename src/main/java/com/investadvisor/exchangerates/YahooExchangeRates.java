package com.investadvisor.exchangerates;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.investadvisor.Currency;
import com.investadvisor.exchangerates.model.YahooExchangeRateResponse;
import com.investadvisor.investarget.pamm.fxopen.FxOpenLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author Dmitriy Liandres
 * Date 26.05.2016
 */
public class YahooExchangeRates {

    private static final Logger logger = LoggerFactory.getLogger(FxOpenLoader.class);

    private static Map<Currency, Map<Currency, Double>> rates = null;

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String urlString = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20((?))&env=store://datatables.org/alltableswithkeys&format=json";


    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    public static Double convert(Double value, Currency fromCurrency, Currency toCurrency) throws IOException {
        return loadRates().get(fromCurrency).get(toCurrency) * value;
    }

    /**
     * returns previosly loaded rates. If no rates were loaded, we load them from yahoo
     *
     * @return map from currency->to currency>exchange rate
     */
    public static Map<Currency, Map<Currency, Double>> loadRates() throws IOException {
        if (rates == null) {
            reloadRates();
        }
        return rates;

    }

    /**
     * Loads exchange rates from yahoo
     *
     * @return map from currency->to currency>exchange rate
     */
    public static Map<Currency, Map<Currency, Double>> reloadRates() throws IOException {
        logger.info("Start load currency exchange rates");
        //from currency->to currency>exchange rate
        Map<Currency, Map<Currency, Double>> result = new HashMap<>();
        //create list of currency exchange:
        List<String> currencyExchanges = new ArrayList<>();
        for (Currency currencyFrom : Currency.values()) {
            for (Currency currencyTo : Currency.values()) {
                currencyExchanges.add("\"" + currencyFrom.name() + currencyTo.name() + "\"");
            }
        }
        String url = urlString.replace("(?)", StringUtils.join(currencyExchanges, ","));
        //this url returns all pamms for alpari
        URL urlAll = new URL(url);

        //maps returned json to the object
        YahooExchangeRateResponse allValue = objectMapper.readValue(urlAll.openStream(), YahooExchangeRateResponse.class);
        allValue.getQuery().getResults().getRate().forEach(rate -> {
            String[] currencyPair = rate.getName().split("/");
            result.putIfAbsent(Currency.valueOf(currencyPair[0]), new HashMap<>());
            result.get(Currency.valueOf(currencyPair[0])).put(Currency.valueOf(currencyPair[1]), Double.valueOf(rate.getRate()));
        });
        logger.info("Finish load currency exchange rates, rates = {}", result);
        rates = result;
        return result;


    }

}
