package com.example.healthbuddy.consult;

public class DoctorAppint {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getabout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    String name;
    DoctorAppint(){

    }

    public DoctorAppint(String name, String about,String key) {
        this.name = name;
        this.about = about;
        this.key=key;

    }

    String about;
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

