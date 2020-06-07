package com.toolformoney.investarget.pamm.alpari.model;

import java.util.List;

/**
 * Created by Lenovo on 07.06.2020.
 */
public class PammDaily {
    private List<List<String>> data;

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
