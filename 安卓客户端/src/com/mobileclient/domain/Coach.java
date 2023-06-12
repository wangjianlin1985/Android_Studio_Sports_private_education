package com.mobileclient.domain;

import java.io.Serializable;

public class Coach implements Serializable {
    /*�û���*/
    private String coachUserName;
    public String getCoachUserName() {
        return coachUserName;
    }
    public void setCoachUserName(String coachUserName) {
        this.coachUserName = coachUserName;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*����*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*�Ա�*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*���䷶Χ*/
    private int ageRangeObj;
    public int getAgeRangeObj() {
        return ageRangeObj;
    }
    public void setAgeRangeObj(int ageRangeObj) {
        this.ageRangeObj = ageRangeObj;
    }

    /*����*/
    private int age;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    /*�ֻ���*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*����*/
    private String cityObj;
    public String getCityObj() {
        return cityObj;
    }
    public void setCityObj(String cityObj) {
        this.cityObj = cityObj;
    }

    /*��״̬*/
    private int nowStateObj;
    public int getNowStateObj() {
        return nowStateObj;
    }
    public void setNowStateObj(int nowStateObj) {
        this.nowStateObj = nowStateObj;
    }

    /*�շѼ۸�����*/
    private int priceRangeObj;
    public int getPriceRangeObj() {
        return priceRangeObj;
    }
    public void setPriceRangeObj(int priceRangeObj) {
        this.priceRangeObj = priceRangeObj;
    }

    /*�۸�(Ԫ/Сʱ)*/
    private int price;
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    /*������Ƭ*/
    private String coachPhoto;
    public String getCoachPhoto() {
        return coachPhoto;
    }
    public void setCoachPhoto(String coachPhoto) {
        this.coachPhoto = coachPhoto;
    }

    /*�������*/
    private String coachDesc;
    public String getCoachDesc() {
        return coachDesc;
    }
    public void setCoachDesc(String coachDesc) {
        this.coachDesc = coachDesc;
    }

    /*���״̬*/
    private String shzt;
    public String getShzt() {
        return shzt;
    }
    public void setShzt(String shzt) {
        this.shzt = shzt;
    }

    /*ע��ʱ��*/
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

}