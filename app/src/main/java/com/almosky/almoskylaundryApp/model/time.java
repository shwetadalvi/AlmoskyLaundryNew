package com.almosky.almoskylaundryApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class time {

    @SerializedName("Sl_No")
    @Expose
    private Integer slNo;
    @SerializedName("Time_Id")
    @Expose
    private Integer timeId;
    @SerializedName("Time_Day")
    @Expose
    private String timeDay;
    @SerializedName("FromTime")
    @Expose
    private String fromTime;
    @SerializedName("ToTime")
    @Expose
    private String toTime;

    public Integer getSlNo() {
        return slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
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

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

}