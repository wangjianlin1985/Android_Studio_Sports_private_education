package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Manager {
    /*����Ա�û���*/
    private String managerUserName;
    public String getManagerUserName() {
        return managerUserName;
    }
    public void setManagerUserName(String managerUserName) {
        this.managerUserName = managerUserName;
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

    /*��������*/
    private Timestamp birthDate;
    public Timestamp getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}