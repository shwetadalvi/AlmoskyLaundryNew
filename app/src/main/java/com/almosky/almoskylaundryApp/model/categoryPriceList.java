package com.almosky.almoskylaundryApp.model;

import java.util.ArrayList;

public class categoryPriceList {
    String categoryName;
    String categoryId;
    public ArrayList<categoryItemPrice> drycleanprice;
    public ArrayList<categoryItemPrice> washironprice;
    public ArrayList<categoryItemPrice> ironingprice;
    public ArrayList<categoryItemPrice> CategoryItemPrices;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<categoryItemPrice> getDrycleanprice() {
        return drycleanprice;
    }

    public void setDrycleanprice(ArrayList<categoryItemPrice> drycleanprice) {
        this.drycleanprice = drycleanprice;
    }

    public ArrayList<categoryItemPrice> getWashironprice() {
        return washironprice;
    }

    public void setWashironprice(ArrayList<categoryItemPrice> washironprice) {
        this.washironprice = washironprice;
    }

    public ArrayList<categoryItemPrice> getIroningprice() {
        return ironingprice;
    }

    public void setIroningprice(ArrayList<categoryItemPrice> ironingprice) {
        this.ironingprice = ironingprice;
    }

    public ArrayList<categoryItemPrice> getCategoryItemPrices() {
        return CategoryItemPrices;
    }

    public void setCategoryItemPrices(ArrayList<categoryItemPrice> categoryItemPrices) {
        CategoryItemPrices = categoryItemPrices;
    }
}
