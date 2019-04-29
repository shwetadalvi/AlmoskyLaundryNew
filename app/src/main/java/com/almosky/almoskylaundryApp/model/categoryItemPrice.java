package com.almosky.almoskylaundryApp.model;

public class categoryItemPrice {
    String name;
    String itemid;
    String normal;
    String fast;
    String categoryname;

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getFast() {
        return fast;
    }

    public void setFast(String fast) {
        this.fast = fast;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
