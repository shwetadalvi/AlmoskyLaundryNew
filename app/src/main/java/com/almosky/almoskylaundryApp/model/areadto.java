package com.almosky.almoskylaundryApp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class areadto {
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

        @SerializedName("Area_Id")
        @Expose
        private Integer areaId;
        @SerializedName("Area_Name")
        @Expose
        private String areaName;

        public Integer getAreaId() {
            return areaId;
        }

        public void setAreaId(Integer areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

    }

}
