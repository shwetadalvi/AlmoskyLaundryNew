package com.almosky.almoskylaundryApp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class data1 {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private ArrayList<Result> result;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("Order_Sub_ID")
        @Expose
        private Integer orderSubID;
        @SerializedName("Order_ID")
        @Expose
        private Integer orderID;
        @SerializedName("Item_ID")
        @Expose
        private Integer itemID;
        @SerializedName("Service_ID")
        @Expose
        private Integer serviceID;
        @SerializedName("Qty")
        @Expose
        private Integer qty;

        @SerializedName("Price")
        @Expose
        private Integer price;

        @SerializedName("Item_Name")
        @Expose
        private String itemName;

        @SerializedName("CR_Date")
        @Expose
        private String cRDate;

        public Integer getOrderSubID() {
            return orderSubID;
        }

        public void setOrderSubID(Integer orderSubID) {
            this.orderSubID = orderSubID;
        }

        public Integer getOrderID() {
            return orderID;
        }

        public void setOrderID(Integer orderID) {
            this.orderID = orderID;
        }

        public Integer getItemID() {
            return itemID;
        }

        public void setItemID(Integer itemID) {
            this.itemID = itemID;
        }

        public Integer getServiceID() {
            return serviceID;
        }

        public void setServiceID(Integer serviceID) {
            this.serviceID = serviceID;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getcRDate() {
            return cRDate;
        }

        public void setcRDate(String cRDate) {
            this.cRDate = cRDate;
        }
    }
}
