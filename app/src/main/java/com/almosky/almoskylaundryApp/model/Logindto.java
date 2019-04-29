package com.almosky.almoskylaundryApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Logindto {

    @SerializedName("Result")
    @Expose
    private ArrayList<Result> result;

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
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("Admin")
        @Expose
        private Integer admin;
        @SerializedName("Mobile")
        @Expose
        private String mobile;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getAdmin() {
            return admin;
        }

        public void setAdmin(Integer admin) {
            this.admin = admin;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

    }
}


