package com.almosky.almoskylaundryApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CheckDiscount {
    @SerializedName("discount")
    @Expose
    private String discount;
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