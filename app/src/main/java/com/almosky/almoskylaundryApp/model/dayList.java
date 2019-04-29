package com.almosky.almoskylaundryApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class dayList {

    @SerializedName("Day")
    @Expose
    private List<day> day = null;

    public List<day> getDay() {
        return day;
    }

    public void setDay(List<day> day) {
        this.day = day;
    }

}