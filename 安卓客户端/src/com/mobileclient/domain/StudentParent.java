package com.mobileclient.domain;

import java.io.Serializable;

public class StudentParent implements Serializable {
    /*�û���*/
    private String spUserName;
    public String getSpUserName() {
        return spUserName;
    }
    public void setSpUserName(String spUserName) {
        this.spUserName = spUserName;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*�ҳ�����*/
    private String parentName;
    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
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

    /*ѧ���Ա�*/
    private String studentSex;
    public String getStudentSex() {
        return studentSex;
    }
    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }

    /*���䷶Χ*/
    private int ageRangeObj;
    public int getAgeRangeObj() {
        return ageRangeObj;
    }
    public void setAgeRangeObj(int ageRangeObj) {
        this.ageRangeObj = ageRangeObj;
    }

    /*ѧ������*/
    private int age;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    /*ѧ��ѧУ*/
    private String school;
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }

    /*��״̬*/
    private int nowStateObj;
    public int getNowStateObj() {
        return nowStateObj;
    }
    public void setNowStateObj(int nowStateObj) {
        this.nowStateObj = nowStateObj;
    }

    /*ѧ����Ƭ*/
    private String studentPhoto;
    public String getStudentPhoto() {
        return studentPhoto;
    }
    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

    /*ѧ������*/
    private String studentDesc;
    public String getStudentDesc() {
        return studentDesc;
    }
    public void setStudentDesc(String studentDesc) {
        this.studentDesc = studentDesc;
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