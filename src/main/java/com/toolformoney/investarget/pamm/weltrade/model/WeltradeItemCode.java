package com.toolformoney.investarget.pamm.weltrade.model;

/**
 * Author Dmitriy Liandres
 * Date 25.09.2016
 */
public class WeltradeItemCode {

    private String display_value;
    private String opened;
    private String detail_page_url;

    public String getDisplay_value() {
        return display_value;
    }

    public void setDisplay_value(String display_value) {
        this.display_value = display_value;
    }

    public String getOpened() {
        return opened;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }

    public String getDetail_page_url() {
        return detail_page_url;
    }

    public void setDetail_page_url(String detail_page_url) {
        this.detail_page_url = detail_page_url;
    }

    @Override
    public String toString() {
        return "WeltradeItemCode{" +
                "display_value='" + display_value + '\'' +
                ", opened=" + opened +
                ", detail_page_url='" + detail_page_url + '\'' +
                '}';
    }
}
