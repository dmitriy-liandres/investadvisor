package com.toolformoney;

/**
 * Enum with all possible currencies
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
public enum Currency {
    USD, EUR, RUB, GBP, AUD, CZK, SGD, JPY, TRY, PLN;

    /**
     * Checks whether valueToTest is from the enum
     *
     * @param valueToTest value to test
     * @return true if value is from the enum
     */
    public static Boolean isRelevant(String valueToTest) {
        for (Currency currency : Currency.values()) {
            if (currency.name().equals(valueToTest)) {
                return true;
            }
        }
        return false;

    }


}
