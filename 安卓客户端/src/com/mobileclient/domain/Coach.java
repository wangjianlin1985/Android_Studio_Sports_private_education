package com.mobileclient.domain;

import java.io.Serializable;

public class Coach implements Serializable {
    /*用户名*/
    private String coachUserName;
    public String getCoachUserName() {
        return coachUserName;
    }
    public void setCoachUserName(String coachUserName) {
        this.coachUserName = coachUserName;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*姓名*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*年龄范围*/
    private int ageRangeObj;
    public int getAgeRangeObj() {
        return ageRangeObj;
    }
    public void setAgeRangeObj(int ageRangeObj) {
        this.ageRangeObj = ageRangeObj;
    }

    /*年龄*/
    private int age;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    /*手机号*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*城市*/
    private String cityObj;
    public String getCityObj() {
        return cityObj;
    }
    public void setCityObj(String cityObj) {
        this.cityObj = cityObj;
    }

    /*现状态*/
    private int nowStateObj;
    public int getNowStateObj() {
        return nowStateObj;
    }
    public void setNowStateObj(int nowStateObj) {
        this.nowStateObj = nowStateObj;
    }

    /*收费价格区间*/
    private int priceRangeObj;
    public int getPriceRangeObj() {
        return priceRangeObj;
    }
    public void setPriceRangeObj(int priceRangeObj) {
        this.priceRangeObj = priceRangeObj;
    }

    /*价格(元/小时)*/
    private int price;
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    /*教练照片*/
    private String coachPhoto;
    public String getCoachPhoto() {
        return coachPhoto;
    }
    public void setCoachPhoto(String coachPhoto) {
        this.coachPhoto = coachPhoto;
    }

    /*教练简介*/
    private String coachDesc;
    public String getCoachDesc() {
        return coachDesc;
    }
    public void setCoachDesc(String coachDesc) {
        this.coachDesc = coachDesc;
    }

    /*审核状态*/
    private String shzt;
    public String getShzt() {
        return shzt;
    }
    public void setShzt(String shzt) {
        this.shzt = shzt;
    }

    /*注册时间*/
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

}