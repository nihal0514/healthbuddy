package com.example.healthbuddy.labtest;

public class LabtestSV2 {
    LabtestSV2(){

    }
    String image;
    String name;
    String totaltests;
    String key;

    public LabtestSV2(String image, String name, String totaltests, String key) {

        this.image = image;
        this.name = name;
        this.totaltests = totaltests;
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotaltests() {
        return totaltests;
    }

    public void setTotaltests(String totaltests) {
        this.totaltests = totaltests;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
