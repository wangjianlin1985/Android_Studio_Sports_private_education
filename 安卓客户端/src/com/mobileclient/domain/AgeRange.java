package com.mobileclient.domain;

import java.io.Serializable;

public class AgeRange implements Serializable {
    /*年龄范围id*/
    private int arId;
    public int getArId() {
        return arId;
    }
    public void setArId(int arId) {
        this.arId = arId;
    }

    /*开始年龄*/
    private int startAge;
    public int getStartAge() {
        return startAge;
    }
    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }

    /*结束年龄*/
    private int endAge;
    public int getEndAge() {
        return endAge;
    }
    public void setEndAge(int endAge) {
        this.endAge = endAge;
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