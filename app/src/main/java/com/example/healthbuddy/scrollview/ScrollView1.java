package com.example.healthbuddy.scrollview;

public class ScrollView1 {
    ScrollView1(){

    }
    public ScrollView1(String name, String imageview,String key) {
        this.name = name;
        this.imageview = imageview;
        this.key=key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageview() {
        return imageview;
    }

    public void setImageview(String imageview) {
        this.imageview = imageview;
    }

    String name;
    String imageview;
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
