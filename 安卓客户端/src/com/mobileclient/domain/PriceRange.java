package com.mobileclient.domain;

import java.io.Serializable;

public class PriceRange implements Serializable {
    /*�۸�Χid*/
    private int prId;
    public int getPrId() {
        return prId;
    }
    public void setPrId(int prId) {
        this.prId = prId;
    }

    /*��ʼ��*/
    private int startPrice;
    public int getStartPrice() {
        return startPrice;
    }
    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    /*������*/
    private int endPrice;
    public int getEndPrice() {
        return endPrice;
    }
    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    /*��ʾ��Ϣ*/
    private String showText;
    public String getShowText() {
        return showText;
    }
    public void setShowText(String showText) {
        this.showText = showText;
    }

}