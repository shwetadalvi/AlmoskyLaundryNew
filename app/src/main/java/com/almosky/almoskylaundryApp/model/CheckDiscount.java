package com.almosky.almoskylaundryApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CheckDiscount {
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("discAmt")
    @Expose
    private double discAmt;
    @SerializedName("expFrom")
    @Expose
    private String expFrom;
    @SerializedName("expTo")
    @Expose
    private String expTo;
    @SerializedName("discountText")
    @Expose
    private String discountText;
    @SerializedName("discountImageURL")
    @Expose
    private String discountImageURL;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public double getDiscAmt() {
        return discAmt;
    }

    public void setDiscAmt(double discAmt) {
        this.discAmt = discAmt;
    }

    public String getExpFrom() {
        return expFrom;
    }

    public void setExpFrom(String expFrom) {
        this.expFrom = expFrom;
    }

    public String getExpTo() {
        return expTo;
    }

    public void setExpTo(String expTo) {
        this.expTo = expTo;
    }

    public String getDiscountText() {
        return discountText;
    }

    public void setDiscountText(String discountText) {
        this.discountText = discountText;
    }

    public String getDiscountImageURL() {
        return discountImageURL;
    }

    public void setDiscountImageURL(String discountImageURL) {
        this.discountImageURL = discountImageURL;
    }

}