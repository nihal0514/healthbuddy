package com.example.healthbuddy.shop;

public class mycart {
    private String priceperquantity,quantity,productName;
    private int TotalPrice;
    private String medicineimage;

    public mycart(String priceperquantity, String quantity, String productName, int totalPrice, String medicineimage) {
        this.priceperquantity = priceperquantity;
        this.quantity = quantity;
        this.productName = productName;
        TotalPrice = totalPrice;
        this.medicineimage = medicineimage;
    }
    mycart(){

    }

    public String getPriceperquantity() {
        return priceperquantity;
    }

    public void setPriceperquantity(String priceperquantity) {
        this.priceperquantity = priceperquantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getMedicineimage() {
        return medicineimage;
    }

    public void setMedicineimage(String medicineimage) {
        this.medicineimage = medicineimage;
    }
}
