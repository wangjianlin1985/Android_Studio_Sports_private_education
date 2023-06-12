package com.mobileclient.domain;

import java.io.Serializable;

public class PriceRange implements Serializable {
    /*价格范围id*/
    private int prId;
    public int getPrId() {
        return prId;
    }
    public void setPrId(int prId) {
        this.prId = prId;
    }

    /*起始价*/
    private int startPrice;
    public int getStartPrice() {
        return startPrice;
    }
    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    /*结束价*/
    private int endPrice;
    public int getEndPrice() {
        return endPrice;
    }
    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    /*显示信息*/
    private String showText;
    public String getShowText() {
        return showText;
    }
    public void setShowText(String showText) {
        this.showText = showText;
    }

}