package com.example.healthbuddy.scrollview;

public class ScrollView2 {
    ScrollView2(){

    }
    private String ProductImage;
    private String ProductName;
    private long FinalPrice;
    private long Pils;
    private long Price;
    private String key;
    private String Description;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public long getFinalPrice() {
        return FinalPrice;
    }

    public void setFinalPrice(long finalPrice) {
        FinalPrice = finalPrice;
    }

    public long getPils() {
        return Pils;
    }

    public void setPils(long pils) {
        Pils = pils;
    }

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }

    public ScrollView2(String productImage, String productName, long finalPrice, long pils, long price, String key) {
        ProductImage = productImage;
        ProductName = productName;
        FinalPrice = finalPrice;
        Pils = pils;
        Price = price;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
