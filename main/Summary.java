package com.mamoo.istproject;

public class Summary {
    String itemcode;
    stock_data data;

    public Summary() {
        itemcode = "";
        data = null;
    }
    public Summary(String itemcode, stock_data data) {
        this.itemcode = itemcode;
        this.data = data;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public stock_data getData() {
        return data;
    }

    public void setData(stock_data data) {
        this.data = data;
    }
}
