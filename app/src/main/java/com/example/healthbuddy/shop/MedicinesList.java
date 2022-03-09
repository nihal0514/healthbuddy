package com.example.healthbuddy.shop;

import android.widget.ImageView;

public class MedicinesList {
    MedicinesList(){

    }

    public MedicinesList(String mediImage, String mediName, long pils, long actualprice, long finalprice,String key) {
        this.mediImage = mediImage;
        this.mediName = mediName;
        this.pils = pils;
        this.actualprice = actualprice;
        this.finalprice = finalprice;
        this.key=key;
    }

    public String getMediImage() {
        return mediImage;
    }

    public void setMediImage(String mediImage) {
        this.mediImage = mediImage;
    }

    public String getMediName() {
        return mediName;
    }

    public void setMediName(String mediName) {
        this.mediName = mediName;
    }

    public long getPils() {
        return pils;
    }

    public void setPils(long pils) {
        this.pils = pils;
    }

    public long getActualprice() {
        return actualprice;
    }

    public void setActualprice(long actualprice) {
        this.actualprice = actualprice;
    }

    public long getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(long finalprice) {
        this.finalprice = finalprice;
    }

    String mediImage;
    String mediName;
    long pils,actualprice,finalprice;
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
