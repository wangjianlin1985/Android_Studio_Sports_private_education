package com.mobileclient.domain;

import java.io.Serializable;

public class AgeRange implements Serializable {
    /*���䷶Χid*/
    private int arId;
    public int getArId() {
        return arId;
    }
    public void setArId(int arId) {
        this.arId = arId;
    }

    /*��ʼ����*/
    private int startAge;
    public int getStartAge() {
        return startAge;
    }
    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }

    /*��������*/
    private int endAge;
    public int getEndAge() {
        return endAge;
    }
    public void setEndAge(int endAge) {
        this.endAge = endAge;
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