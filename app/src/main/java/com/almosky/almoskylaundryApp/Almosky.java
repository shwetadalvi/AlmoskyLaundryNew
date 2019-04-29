package com.almosky.almoskylaundryApp;


import com.almosky.almoskylaundryApp.model.Addressdto;
import com.almosky.almoskylaundryApp.model.OrderListdto;
import com.almosky.almoskylaundryApp.model.categoryPriceList;
import com.almosky.almoskylaundryApp.model.categorydto;
import com.almosky.almoskylaundryApp.model.data;
import com.almosky.almoskylaundryApp.model.data1;
import com.almosky.almoskylaundryApp.model.priceListdto;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

public class Almosky {
    public static Almosky inst;



    public ArrayList<data.Detail.Item> drycleanList;
    public ArrayList<data.Detail.Item> ironList;
    public ArrayList<data.Detail.Item> washList;
    public ArrayList<data1.Result> drycleanList1;
    public ArrayList<data1.Result> ironList1;
    public ArrayList<data1.Result> washList1;
    public ArrayList<categoryPriceList> drycleanpriceList;
    public ArrayList<categoryPriceList> washironpriceList;
    public ArrayList<categoryPriceList> ironingpriceList;
    public ArrayList<priceListdto.Detail> itempriceList;
    public RequestParams mRequestParams;
    ArrayList<priceListdto.Detail> priceDetails;
    public boolean updatePriceList;
    public ArrayList<String> pickdaysname;
    public ArrayList<String> deliverydaysname;
    public ArrayList<categorydto.Detail> categoryList;
    public OrderListdto.Result selectedOrder;
    public Addressdto.Result selectedAddress;
    public String orderType;
    public String address;
    public String deliveryType;
    public String priceList;
    public String pickuptime;
    public String deliverytime;
    public String deliverydate;
    public String pickupdate;
    public int pickserviceType;
    public int addressId;
    public int delserviceType;


    public int cartcount;
    public int cartamount;

    public boolean offer;

    public double vatRate,nasabRate,adminDiscount;

    public String addressType; // 0 represents normal address, 1 represents address for new order
    public boolean NisabClub;

    public String nasabAddress1;
    public String nasabAddress2;
    public String nasabTiming;
    public String pickupToTime;

    public int minAmount;
    public int nasabAmount;



    private Almosky(){

    }



    public static Almosky getInst(){
        if(inst==null){
            inst=new Almosky();
        }
        return  inst;
    }
    public String getPickupToTime() {
        return pickupToTime;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getNasabAmount() {
        return nasabAmount;
    }

    public void setNasabAmount(int nasabAmount) {
        this.nasabAmount = nasabAmount;
    }

    public void setPickupToTime(String pickupToTime) {
        this.pickupToTime = pickupToTime;
    }
    public OrderListdto.Result getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(OrderListdto.Result selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public ArrayList<data1.Result> getDrycleanList1() {
        return drycleanList1;
    }

    public void setDrycleanList1(ArrayList<data1.Result> drycleanList1) {
        this.drycleanList1 = drycleanList1;
    }

    public ArrayList<data1.Result> getIronList1() {
        return ironList1;
    }

    public void setIronList1(ArrayList<data1.Result> ironList1) {
        this.ironList1 = ironList1;
    }

    public ArrayList<data1.Result> getWashList1() {
        return washList1;
    }

    public void setWashList1(ArrayList<data1.Result> washList1) {
        this.washList1 = washList1;
    }

    public boolean isNisabClub() {
        return NisabClub;
    }



    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }
    public double getAdminDiscount() {
        return adminDiscount;
    }

    public void setAdminDiscount(double adminDiscount) {
        this.adminDiscount = adminDiscount;
    }
    public double getNasabRate() {
        return nasabRate;
    }

    public void setNasabRate(double nasabRate) {
        this.nasabRate = nasabRate;
    }

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public void setNisabClub(boolean nisabClub) {
        NisabClub = nisabClub;
    }

    public ArrayList<priceListdto.Detail> getPriceDetails() {
        return priceDetails;
    }

    public void setPriceDetails(ArrayList<priceListdto.Detail> priceDetails) {
        this.priceDetails = priceDetails;
    }

    public boolean isUpdatePriceList() {
        return updatePriceList;
    }

    public void setUpdatePriceList(boolean updatePriceList) {
        this.updatePriceList = updatePriceList;
    }

    public ArrayList<String> getPickdaysname() {
        return pickdaysname;
    }

    public void setPickdaysname(ArrayList<String> pickdaysname) {
        this.pickdaysname = pickdaysname;
    }

    public ArrayList<String> getDeliverydaysname() {
        return deliverydaysname;
    }

    public void setDeliverydaysname(ArrayList<String> deliverydaysname) {
        this.deliverydaysname = deliverydaysname;
    }

    public Addressdto.Result getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Addressdto.Result selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public ArrayList<priceListdto.Detail> getItempriceList() {
        return itempriceList;
    }

    public void setItempriceList(ArrayList<priceListdto.Detail> itempriceList) {
        this.itempriceList = itempriceList;
    }

    public ArrayList<categorydto.Detail> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<categorydto.Detail> categoryList) {
        this.categoryList = categoryList;
    }

    public ArrayList<categoryPriceList> getDrycleanpriceList() {
        return drycleanpriceList;
    }

    public void setDrycleanpriceList(ArrayList<categoryPriceList> drycleanpriceList) {
        this.drycleanpriceList = drycleanpriceList;
    }

    public ArrayList<categoryPriceList> getWashironpriceList() {
        return washironpriceList;
    }

    public void setWashironpriceList(ArrayList<categoryPriceList> washironpriceList) {
        this.washironpriceList = washironpriceList;
    }

    public ArrayList<categoryPriceList> getIroningpriceList() {
        return ironingpriceList;
    }

    public void setIroningpriceList(ArrayList<categoryPriceList> ironingpriceList) {
        this.ironingpriceList = ironingpriceList;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getPriceList() {
        return priceList;
    }

    public void setPriceList(String priceList) {
        this.priceList = priceList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(String pickuptime) {
        this.pickuptime = pickuptime;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public String getPickupdate() {
        return pickupdate;
    }

    public void setPickupdate(String pickupdate) {
        this.pickupdate = pickupdate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getPickserviceType() {
        return pickserviceType;
    }

    public void setPickserviceType(int pickserviceType) {
        this.pickserviceType = pickserviceType;
    }

    public int getDelserviceType() {
        return delserviceType;
    }

    public void setDelserviceType(int delserviceType) {
        this.delserviceType = delserviceType;
    }

    public int getCartcount() {
        return cartcount;
    }



    public void setCartcount(int cartcount) {
        this.cartcount = cartcount;
    }

    public int getCartamount() {
        return cartamount;
    }

    public void setCartamount(int cartamount) {
        this.cartamount = cartamount;
    }

    public ArrayList<data.Detail.Item> getDrycleanList() {
        return drycleanList;
    }

    public void setDrycleanList(ArrayList<data.Detail.Item> drycleanList) {
        this.drycleanList = drycleanList;
    }

    public ArrayList<data.Detail.Item> getIronList() {
        return ironList;
    }

    public void setIronList(ArrayList<data.Detail.Item> ironList) {
        this.ironList = ironList;
    }

    public ArrayList<data.Detail.Item> getWashList() {
        return washList;
    }

    public void setWashList(ArrayList<data.Detail.Item> washList) {
        this.washList = washList;
    }

    public String getNasabAddress1() {
        return nasabAddress1;
    }

    public void setNasabAddress1(String nasabAddress1) {
        this.nasabAddress1 = nasabAddress1;
    }

    public String getNasabAddress2() {
        return nasabAddress2;
    }

    public void setNasabAddress2(String nasabAddress2) {
        this.nasabAddress2 = nasabAddress2;
    }

    public String getNasabTiming() {
        return nasabTiming;
    }

    public void setNasabTiming(String nasabTiming) {
        this.nasabTiming = nasabTiming;
    }

    public RequestParams getRequestParams() {
        return mRequestParams;
    }

    public void setRequestParams(RequestParams requestParams) {
        mRequestParams = requestParams;
    }
}
