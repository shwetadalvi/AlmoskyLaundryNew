package com.almosky.almoskylaundryApp.utils.constants;


public interface ApiConstants {

    //String BaseUrl = "https://abrlaundryapp.herokuapp.com/";
    String BaseUrl = "http://148.72.64.138:3006/";
    String loginUrl = "login/userlogin";
    String discountUrl = "minorder/list";
    String signUpUrl = "register/new";
    String enquiryUrl = "queries/new";
    String placeOrderUrl = "order/new";
    String normalorderUrl = "order/new";
    String easyorderUrl = "orderNote/new";
    String orderListUrl = "orderList/allorders";
    String categoryUrl = "itemcategory/list";
    String getPriceListUrl = "price/list";
    String addresslistUrl="Addresslist/list";
    String deleteAddressUrl="deleteAddress/delete";
    String editAddressUrl="editAddress/edit";
    String addAddressUrl="Address/new";
    String general = "gen/general";
    String firebaseTokenId = "gen/update/token";
    String getDiscount = "customer/discount/listbycustomer";
    String forgotPassEmailVerification = "gen/forgot";
    String forgotPassCodeVerification = "gen/forgot/code";
    String forgotPasswordUpdation = "gen/forgot/update";
    String updateUrl = "user/edit" ;

    String areaListsUrl="area/list";
    String areaAddNew="Address/new/area";

    String getDays = "day/list";
    String getTimings = "time/list";
    String getNasabTimings = "time/nasablist";

    String email = "email";
    String uid="uid";
    String password = "password";
    String registrationToken = "registrationToken";
    String name = "name";
    String phone = "mobile";
    String query = "enquiry";
    String code = "code";
    String newemail ="newemail";

    String firstname = "firstname";
    String lastname = "lastname";




    String _id = "_id";
    String emirate = "emirate";
    String area = "area";
    String street = "street";
    String landmark = "landmark";
    String building = "building";
    String flat = "flat";
    String pickup = "pickup";
    String delivery = "delivery";

    String status = "status";



}
