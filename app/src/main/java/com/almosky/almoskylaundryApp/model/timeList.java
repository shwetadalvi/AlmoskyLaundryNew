package com.almosky.almoskylaundryApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class timeList {

    @SerializedName("Result")
    @Expose
    private List<time> result = null;

    public List<time> getResult() {
        return result;
    }

    public void setResult(List<time> result) {
        this.result = result;
    }

}