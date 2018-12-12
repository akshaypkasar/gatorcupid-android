package com.example.surfacepro2.gatorscupid.model;

import com.google.gson.Gson;
import java.io.Serializable;



public class User implements Serializable {

    private Long id;
    private String name;
    private String email;
    private Integer gender;
    private String birthYear;
    private Integer interestedIn;
    private Integer intention;
    private String about;
    private String major;
    private Integer isProfileCreated;
    private String profilePic;
    private Integer age;
    private Integer profilePicAction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
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

    public void setIsProfileCreated(Integer isProfileCreated) { this.isProfileCreated = isProfileCreated; }

    public String getProfilePic() { return profilePic; }

    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age = age; }

    public Integer getProfilePicAction() {
        return profilePicAction;
    }

    public void setProfilePicAction(Integer profilePicAction) {
        this.profilePicAction = profilePicAction;
    }

    @Override
    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
