package com.almosky.almoskylaundryApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class discountdto {

    @SerializedName("Result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("Min_Amt")
        @Expose
        private Integer minAmt;
        @SerializedName("Nasab_Min_Amount")
        @Expose
        private Integer nasabMinAmount;

        public Integer getMinAmt() {
            return minAmt;
        }

        public void setMinAmt(Integer minAmt) {
            this.minAmt = minAmt;
        }

        public Integer getNasabMinAmount() {
            return nasabMinAmount;
        }

        public void setNasabMinAmount(Integer nasabMinAmount) {
            this.nasabMinAmount = nasabMinAmount;
        }

    }
}
