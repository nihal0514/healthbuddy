package com.example.healthbuddy.consult;

public class DoctorsList {

    String name;
    String about;
    String key;
    String profileimage;

    public DoctorsList(String name, String about, String key, String profileimage) {
        this.name = name;
        this.about = about;
        this.key = key;
        this.profileimage = profileimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    DoctorsList(){

    }
}
