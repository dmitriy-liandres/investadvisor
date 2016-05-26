package com.investadvisor.fxopen;

import java.util.List;

/**
 * Author Dmitriy Liandres
 * Date 25.05.2016
 */
public class FXOpenManagerGeneralList {
    private List<FXOpenManagerGeneral> data;

    public List<FXOpenManagerGeneral> getData() {
        return data;
    }

    public void setData(List<FXOpenManagerGeneral> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FXOpenManagerGeneralList{" +
                "data=" + data +
                '}';
    }
}
