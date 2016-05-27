package com.investadvisor.pamm.alpari.model;

import java.util.List;

/**
 * This reflects list structure with all alpari pamms
 * Author Dmitriy Liandres
 * Date 24.05.2016
 */
public class PAMMAlpariGeneral {

    private String total;

    private PAMMAlpariGeneralMap map;

    private List<List<Object>> elements;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public PAMMAlpariGeneralMap getMap() {
        return map;
    }

    public void setMap(PAMMAlpariGeneralMap map) {
        this.map = map;
    }

    public List<List<Object>> getElements() {
        return elements;
    }

    public void setElements(List<List<Object>> elements) {
        this.elements = elements;
    }
}
