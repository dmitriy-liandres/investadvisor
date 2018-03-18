package com.toolformoney.exchangerates;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toolformoney.Currency;
import com.toolformoney.exchangerates.model.fixerIO.FixerIOResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://fixer.io/
 * dima-amid@tut.by / 2520486
 * Author Dmitriy Liandres
 * Date 18.03.2018
 */
public class FixerIOExchangeRates {
    private static final Logger logger = LoggerFactory.getLogger(FixerIOExchangeRates.class);

    private static LocalDate lastUpdateDate;
    private static Map<Currency, Map<Currency, Double>> rates = null;

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String urlString = "http://data.fixer.io/api/latest?access_key=40a2e2bd24b0be1876d6283496204a2a&format=1";


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
    private static Map<Currency, Map<Currency, Double>> loadRates() throws IOException {
        if (rates == null || ChronoUnit.DAYS.between(LocalDate.now(), lastUpdateDate) > 0) {
            reloadRates();
        }
        return rates;

    }

    /**
     * Loads exchange rates from yahoo
     *
     * @return map from currency->to currency>exchange rate
     */
    private static Map<Currency, Map<Currency, Double>> reloadRates() throws IOException {
        lastUpdateDate = LocalDate.now();
        logger.info("Start load currency exchange rates");
        //from currency->to currency>exchange rate
        Map<Currency, Map<Currency, Double>> result = new HashMap<>();
        //create list of currency exchange:







        URL urlAll = new URL(urlString);

        //maps returned json to the object
        FixerIOResponse allValue = objectMapper.readValue(urlAll.openStream(), FixerIOResponse.class);

        for (Currency currencyFrom : Currency.values()) {
            result.put(currencyFrom, new HashMap<>());
            Double euroToCurrencyFrom = allValue.getRates().get(currencyFrom.name());
            for (Currency currencyTo : Currency.values()) {
                Double euroToCurrencyTo = allValue.getRates().get(currencyTo.name());
                Double rate = 1 / euroToCurrencyFrom * euroToCurrencyTo;

                result.get(currencyFrom).put(currencyTo, rate);
            }
        }
        logger.info("Finish load currency exchange rates, rates = {}", result);
        rates = result;
        return result;


    }
}
