package com.almosky.almoskylaundryApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class OrderListdto {

    @SerializedName("result")
    @Expose
    private ArrayList<Result> result ;

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("orderId")
        @Expose
        private Integer orderId;
        @SerializedName("orderNo")
        @Expose
        private String orderNo;
        @SerializedName("delDate")
        @Expose
        private String delDate;
        @SerializedName("delTime")
        @Expose
        private String delTime;
        @SerializedName("pickupDate")
        @Expose
        private String pickupDate;
        @SerializedName("pickupTime")
        @Expose
        private String pickupTime;
        @SerializedName("deliveryType")
        @Expose
        private Integer deliveryType;

        @SerializedName("OrderStatus")
        @Expose
        private Integer OrderStatus;

        @SerializedName("PaymentStatus")
        @Expose
        private int payment_status;




        @SerializedName("result")
        @Expose
        private ArrayList<Result_> result = null;

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getDelDate() {
            return delDate;
        }

        public void setDelDate(String delDate) {
            this.delDate = delDate;
        }

        public String getDelTime() {
            return delTime;
        }

        public void setDelTime(String delTime) {
            this.delTime = delTime;
        }

        public String getPickupDate() {
            return pickupDate;
        }

        public void setPickupDate(String pickupDate) {
            this.pickupDate = pickupDate;
        }

        public String getPickupTime() {
            return pickupTime;
        }

        public void setPickupTime(String pickupTime) {
            this.pickupTime = pickupTime;
        }

        public Integer getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(Integer deliveryType) {
            this.deliveryType = deliveryType;
        }

        public ArrayList<Result_> getResult() {
            return result;
        }

        public void setResult(ArrayList<Result_> result) {
            this.result = result;
        }

        public Integer getOrderStatus() {
            return OrderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            OrderStatus = orderStatus;
        }

        public int getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(int payment_status) {
            this.payment_status = payment_status;
        }


        public class Result_ {

            @SerializedName("Service_ID")
            @Expose
            private String serviceID;
            @SerializedName("items")
            @Expose
            private ArrayList<Item> items;

            public String getServiceID() {
                return serviceID;
            }

            public void setServiceID(String serviceID) {
                this.serviceID = serviceID;
            }

            public ArrayList<Item> getItems() {
                return items;
            }

            public void setItems(ArrayList<Item> items) {
                this.items = items;
            }

            public class Item {

                @SerializedName("Order_ID")
                @Expose
                private Integer orderID;
                @SerializedName("Order_Sub_ID")
                @Expose
                private Integer orderSubID;
                @SerializedName("Item_ID")
                @Expose
                private Integer itemID;
                @SerializedName("Service_ID")
                @Expose
                private Integer serviceID;
                @SerializedName("Qty")
                @Expose
                private Integer qty;
                @SerializedName("PriceId")
                @Expose
                private Integer priceId;
                @SerializedName("Price")
                @Expose
                private Integer price;
                @SerializedName("Item_Remarks")
                @Expose
                private Integer itemRemarks;
                @SerializedName("Sl_No")
                @Expose
                private Integer slNo;
                @SerializedName("Ret_Qty")
                @Expose
                private Integer retQty;
                @SerializedName("Ret_Remarks")
                @Expose
                private Integer retRemarks;
                @SerializedName("Ret_date")
                @Expose
                private Integer retDate;
                @SerializedName("Rec_Qty")
                @Expose
                private Integer recQty;
                @SerializedName("Rec_Date")
                @Expose
                private String recDate;
                @SerializedName("Item_Name")
                @Expose
                private String itemName;
                @SerializedName("OS_Status")
                @Expose
                private Integer oSStatus;
                @SerializedName("Supplier_ID")
                @Expose
                private Integer supplierID;
                @SerializedName("Cost")
                @Expose
                private Integer cost;
                @SerializedName("HorF")
                @Expose
                private Integer horF;
                @SerializedName("CR_Date")
                @Expose
                private String cRDate;

                public Integer getOrderID() {
                    return orderID;
                }

                public void setOrderID(Integer orderID) {
                    this.orderID = orderID;
                }

                public Integer getOrderSubID() {
                    return orderSubID;
                }

                public void setOrderSubID(Integer orderSubID) {
                    this.orderSubID = orderSubID;
                }

                public Integer getItemID() {
                    return itemID;
                }

                public void setItemID(Integer itemID) {
                    this.itemID = itemID;
                }

                public Integer getServiceID() {
                    return serviceID;
                }

                public void setServiceID(Integer serviceID) {
                    this.serviceID = serviceID;
                }

                public Integer getQty() {
                    return qty;
                }

                public void setQty(Integer qty) {
                    this.qty = qty;
                }

                public Integer getPriceId() {
                    return priceId;
                }

                public void setPriceId(Integer priceId) {
                    this.priceId = priceId;
                }

                public Integer getPrice() {
                    return price;
                }

                public void setPrice(Integer price) {
                    this.price = price;
                }

                public Integer getItemRemarks() {
                    return itemRemarks;
                }

                public void setItemRemarks(Integer itemRemarks) {
                    this.itemRemarks = itemRemarks;
                }

                public Integer getSlNo() {
                    return slNo;
                }

                public void setSlNo(Integer slNo) {
                    this.slNo = slNo;
                }

                public Integer getRetQty() {
                    return retQty;
                }

                public void setRetQty(Integer retQty) {
                    this.retQty = retQty;
                }

                public Integer getRetRemarks() {
                    return retRemarks;
                }

                public void setRetRemarks(Integer retRemarks) {
                    this.retRemarks = retRemarks;
                }

                public Integer getRetDate() {
                    return retDate;
                }

                public void setRetDate(Integer retDate) {
                    this.retDate = retDate;
                }

                public Integer getRecQty() {
                    return recQty;
                }

                public void setRecQty(Integer recQty) {
                    this.recQty = recQty;
                }

                public String getRecDate() {
                    return recDate;
                }

                public void setRecDate(String recDate) {
                    this.recDate = recDate;
                }

                public String getItemName() {
                    return itemName;
                }

                public void setItemName(String itemName) {
                    this.itemName = itemName;
                }

                public Integer getoSStatus() {
                    return oSStatus;
                }

                public void setoSStatus(Integer oSStatus) {
                    this.oSStatus = oSStatus;
                }

                public Integer getSupplierID() {
                    return supplierID;
                }

                public void setSupplierID(Integer supplierID) {
                    this.supplierID = supplierID;
                }

                public Integer getCost() {
                    return cost;
                }

                public void setCost(Integer cost) {
                    this.cost = cost;
                }

                public Integer getHorF() {
                    return horF;
                }

                public void setHorF(Integer horF) {
                    this.horF = horF;
                }

                public String getcRDate() {
                    return cRDate;
                }

                public void setcRDate(String cRDate) {
                    this.cRDate = cRDate;
                }
            }
        }
    }

    }


    /*

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private ArrayList<Result> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("Order_ID")
        @Expose
        private Integer orderID;
        @SerializedName("Order_No")
        @Expose
        private String orderNo;
        @SerializedName("pickup_time")
        @Expose
        private String pickupTime;
        @SerializedName("delivery_time")
        @Expose
        private String deliveryTime;
        @SerializedName("Order_Status")
        @Expose
        private Integer orderStatus;
        @SerializedName("phone_number")
        @Expose
        private String phoneNumber;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("Last_Name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("Address_Name")
        @Expose
        private String addressName;
        @SerializedName("Area")
        @Expose
        private String area;
        @SerializedName("Street")
        @Expose
        private String street;
        @SerializedName("Block")
        @Expose
        private String block;
        @SerializedName("House")
        @Expose
        private String house;
        @SerializedName("Apartment")
        @Expose
        private String apartment;
        @SerializedName("Floor")
        @Expose
        private String floor;
        @SerializedName("CMobile")
        @Expose
        private String cMobile;
        @SerializedName("Additional")
        @Expose
        private String additional;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("latitude")
        @Expose
        private String latitude;

        public Integer getOrderID() {
            return orderID;
        }

        public void setOrderID(Integer orderID) {
            this.orderID = orderID;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPickupTime() {
            return pickupTime;
        }

        public void setPickupTime(String pickupTime) {
            this.pickupTime = pickupTime;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public String getApartment() {
            return apartment;
        }

        public void setApartment(String apartment) {
            this.apartment = apartment;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getcMobile() {
            return cMobile;
        }

        public void setcMobile(String cMobile) {
            this.cMobile = cMobile;
        }

        public String getAdditional() {
            return additional;
        }

        public void setAdditional(String additional) {
            this.additional = additional;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }
*/
