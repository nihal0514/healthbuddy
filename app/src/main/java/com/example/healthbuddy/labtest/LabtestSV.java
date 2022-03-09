package com.example.healthbuddy.labtest;

public class LabtestSV {
    String image;
    String name;
    String key;

    LabtestSV(){

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LabtestSV(String image, String name, String key) {
        this.image = image;
        this.name = name;
        this.key = key;
    }
}
