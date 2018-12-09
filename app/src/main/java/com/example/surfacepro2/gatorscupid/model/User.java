package com.example.surfacepro2.gatorscupid.model;

import com.example.surfacepro2.gatorscupid.constant.Gender;
import com.example.surfacepro2.gatorscupid.constant.Intention;
import com.example.surfacepro2.gatorscupid.constant.InterestedIn;
import com.example.surfacepro2.gatorscupid.constant.State;
import com.google.gson.Gson;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String email;
    private Integer gender;
    private Integer birthYear;
    private Integer interestedIn;
    private Integer intention;
    private String about;
    private String major;
    private Integer isProfileCreated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(Integer interestedIn) {
        this.interestedIn = interestedIn;
    }

    public Integer getIntention() {
        return intention;
    }

    public void setIntention(Integer intention) {
        this.intention = intention;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getIsProfileCreated() {
        return isProfileCreated;
    }

    public void setIsProfileCreated(Integer isProfileCreated) {
        this.isProfileCreated = isProfileCreated;
    }

    @Override
    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }


}
