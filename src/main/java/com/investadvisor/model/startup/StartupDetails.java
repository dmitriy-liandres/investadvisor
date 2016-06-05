package com.investadvisor.model.startup;

/**
 * Common info about startups
 * Author Dmitriy Liandres
 * Date 05.06.2016
 */
public class StartupDetails {

    private String name;
    private String url;
    private Double totalPrice;
    private Double minInvestment;
    private Double filledPercentage;
    private String startDate;
    /**
     * if it is possible to sell for 1.5$ and original price is 2, than value = 91.5-2)/2*100
     */
    private Double askBidDifferenceInPercentages;
    private StartupOfferRisk startupOfferRisk;

    public StartupDetails(String name,
                          String url,
                          Double totalPrice,
                          Double minInvestment,
                          Double filledPercentage,
                          Double askBidDifferenceInPercentages,
                          String startDate,
                          StartupOfferRisk startupOfferRisk) {
        this.name = name;
        this.url = url;
        this.totalPrice = totalPrice;
        this.minInvestment = minInvestment;
        this.filledPercentage = filledPercentage;
        this.askBidDifferenceInPercentages = askBidDifferenceInPercentages;
        this.startDate = startDate;
        this.startupOfferRisk = startupOfferRisk;
    }

    public StartupOfferRisk getStartupOfferRisk() {
        return startupOfferRisk;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Double getMinInvestment() {
        return minInvestment;
    }

    public Double getAskBidDifferenceInPercentages() {
        return askBidDifferenceInPercentages;
    }

    public Double getFilledPercentage() {
        return filledPercentage;
    }

    public String getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return "StartupDetails{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", totalPrice=" + totalPrice +
                ", minInvestment=" + minInvestment +
                ", filledPercentage=" + filledPercentage +
                ", startDate='" + startDate + '\'' +
                ", askBidDifferenceInPercentages=" + askBidDifferenceInPercentages +
                ", startupOfferRisk=" + startupOfferRisk +
                '}';
    }
}
