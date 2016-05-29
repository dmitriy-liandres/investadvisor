package com.investadvisor.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author Dmitriy Liandres
 * Date 29.05.2016
 */
public abstract class InvestmentTargetLoader<T extends InvestmentTarget> {


    /**
     * Maps json to object and back
     */
    protected static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }


    /**
     * Loads all investment targets for one broker
     *
     * @return list of investment targets
     * @throws IOException
     */
    protected abstract List<T> load() throws IOException;

    /**
     * Gets match values using provided patterns and text
     *
     * @param pattern regexp pattern
     * @param text    text where we search for pattern
     * @return the first found group
     */
    protected String getMatchValue(Pattern pattern, String text) {
        Matcher idMatcher = pattern.matcher(text);
        idMatcher.find();
        return idMatcher.group(1);
    }
}
