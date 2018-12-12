package com.example.surfacepro2.gatorscupid.model;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class BrowseProfile implements Serializable {

    List<User> profiles;

    public List<User> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<User> profiles) {
        this.profiles = profiles;
    }

    @Override
    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
