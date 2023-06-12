package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Manager {
    /*管理员用户名*/
    private String managerUserName;
    public String getManagerUserName() {
        return managerUserName;
    }
    public void setManagerUserName(String managerUserName) {
        this.managerUserName = managerUserName;
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

    /*出生日期*/
    private Timestamp birthDate;
    public Timestamp getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}