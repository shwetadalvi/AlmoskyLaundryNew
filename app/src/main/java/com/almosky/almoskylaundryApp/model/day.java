package com.almosky.almoskylaundryApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class day {


    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Time_Id")
    @Expose
    private Integer timeId;
    @SerializedName("Time_Day")
    @Expose
    private String timeDay;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    public String getTimeDay() {
        return timeDay;
    }

    public void setTimeDay(String timeDay) {
        this.timeDay = timeDay;
    }

}