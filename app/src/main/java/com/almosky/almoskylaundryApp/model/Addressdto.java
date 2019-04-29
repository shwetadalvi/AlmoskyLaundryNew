package com.almosky.almoskylaundryApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class Addressdto {

    @SerializedName("Result")
    @Expose
    private ArrayList<Result> result = null;

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("User_ID")
        @Expose
        private Integer userID;
        @SerializedName("Address_Name")
        @Expose
        private String addressName;
        @SerializedName("Area")
        @Expose
        private String area;
        @SerializedName("Street")
        @Expose
        private String street;
        @SerializedName("Block")
        @Expose
        private String block;
        @SerializedName("House")
        @Expose
        private String house;
        @SerializedName("Apartment")
        @Expose
        private String apartment;
        @SerializedName("Floor")
        @Expose
        private String floor;
        @SerializedName("Mobile")
        @Expose
        private String mobile;
        @SerializedName("Additional")
        @Expose
        private String additional;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;

        @SerializedName("offer_Location")
        @Expose
        private int offer_Location;

        public int getOffer_Location() {
            return offer_Location;
        }

        public void setOffer_Location(int offer_Location) {
            this.offer_Location = offer_Location;
        }

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public String getApartment() {
            return apartment;
        }

        public void setApartment(String apartment) {
            this.apartment = apartment;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAdditional() {
            return additional;
        }

        public void setAdditional(String additional) {
            this.additional = additional;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

    }

}