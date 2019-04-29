package com.almosky.almoskylaundryApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Discount {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Discount")
    @Expose
    private double discount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}