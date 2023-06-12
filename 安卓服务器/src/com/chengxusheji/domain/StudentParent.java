package com.chengxusheji.domain;

import java.sql.Timestamp;
public class StudentParent {
    /*用户名*/
    private String spUserName;
    public String getSpUserName() {
        return spUserName;
    }
    public void setSpUserName(String spUserName) {
        this.spUserName = spUserName;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*家长姓名*/
    private String parentName;
    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
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
    private City cityObj;
    public City getCityObj() {
        return cityObj;
    }
    public void setCityObj(City cityObj) {
        this.cityObj = cityObj;
    }

    /*学生性别*/
    private String studentSex;
    public String getStudentSex() {
        return studentSex;
    }
    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex;
    }

    /*年龄范围*/
    private AgeRange ageRangeObj;
    public AgeRange getAgeRangeObj() {
        return ageRangeObj;
    }
    public void setAgeRangeObj(AgeRange ageRangeObj) {
        this.ageRangeObj = ageRangeObj;
    }

    /*学生年龄*/
    private int age;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    /*学生学校*/
    private String school;
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }

    /*现状态*/
    private NowState nowStateObj;
    public NowState getNowStateObj() {
        return nowStateObj;
    }
    public void setNowStateObj(NowState nowStateObj) {
        this.nowStateObj = nowStateObj;
    }

    /*学生照片*/
    private String studentPhoto;
    public String getStudentPhoto() {
        return studentPhoto;
    }
    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

    /*学生介绍*/
    private String studentDesc;
    public String getStudentDesc() {
        return studentDesc;
    }
    public void setStudentDesc(String studentDesc) {
        this.studentDesc = studentDesc;
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