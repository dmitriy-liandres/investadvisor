package com.toolformoney.investarget.pamm.fxopen.model;

import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 27.05.2016
 */
public class FxOpenOffersResult {

    private List<FxOpenOffersResultData> data;

    public List<FxOpenOffersResultData> getData() {
        return data;
    }

    public void setData(List<FxOpenOffersResultData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FxOpenOffersResult{" +
                "data=" + data +
                '}';
    }
}
