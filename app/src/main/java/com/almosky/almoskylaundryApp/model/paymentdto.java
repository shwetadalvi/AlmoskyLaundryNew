package com.almosky.almoskylaundryApp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class paymentdto {

    @SerializedName("Transaction")
    @Expose
    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public class Transaction {

        @SerializedName("ResponseCode")
        @Expose
        private String responseCode;
        @SerializedName("ResponseClass")
        @Expose
        private String responseClass;
        @SerializedName("ResponseDescription")
        @Expose
        private String responseDescription;
        @SerializedName("ResponseClassDescription")
        @Expose
        private String responseClassDescription;
        @SerializedName("Language")
        @Expose
        private String language;
        @SerializedName("ApprovalCode")
        @Expose
        private String approvalCode;
        @SerializedName("Account")
        @Expose
        private String account;
        @SerializedName("Balance")
        @Expose
        private Balance balance;
        @SerializedName("OrderID")
        @Expose
        private String orderID;
        @SerializedName("Amount")
        @Expose
        private Amount amount;
        @SerializedName("Fees")
        @Expose
        private Fees fees;
        @SerializedName("CardNumber")
        @Expose
        private String cardNumber;
        @SerializedName("Payer")
        @Expose
        private Payer payer;
        @SerializedName("CardToken")
        @Expose
        private String cardToken;
        @SerializedName("CardBrand")
        @Expose
        private String cardBrand;
        @SerializedName("CardExpiry")
        @Expose
        private String cardExpiry;
        @SerializedName("CardType")
        @Expose
        private String cardType;
        @SerializedName("UniqueID")
        @Expose
        private String uniqueID;

        public String getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(String responseCode) {
            this.responseCode = responseCode;
        }

        public String getResponseClass() {
            return responseClass;
        }

        public void setResponseClass(String responseClass) {
            this.responseClass = responseClass;
        }

        public String getResponseDescription() {
            return responseDescription;
        }

        public void setResponseDescription(String responseDescription) {
            this.responseDescription = responseDescription;
        }

        public String getResponseClassDescription() {
            return responseClassDescription;
        }

        public void setResponseClassDescription(String responseClassDescription) {
            this.responseClassDescription = responseClassDescription;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getApprovalCode() {
            return approvalCode;
        }

        public void setApprovalCode(String approvalCode) {
            this.approvalCode = approvalCode;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public Balance getBalance() {
            return balance;
        }

        public void setBalance(Balance balance) {
            this.balance = balance;
        }

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public Amount getAmount() {
            return amount;
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Fees getFees() {
            return fees;
        }

        public void setFees(Fees fees) {
            this.fees = fees;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public Payer getPayer() {
            return payer;
        }

        public void setPayer(Payer payer) {
            this.payer = payer;
        }

        public String getCardToken() {
            return cardToken;
        }

        public void setCardToken(String cardToken) {
            this.cardToken = cardToken;
        }

        public String getCardBrand() {
            return cardBrand;
        }

        public void setCardBrand(String cardBrand) {
            this.cardBrand = cardBrand;
        }

        public String getCardExpiry() {
            return cardExpiry;
        }

        public void setCardExpiry(String cardExpiry) {
            this.cardExpiry = cardExpiry;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getUniqueID() {
            return uniqueID;
        }

        public void setUniqueID(String uniqueID) {
            this.uniqueID = uniqueID;
        }
    }




    public class Amount {

        @SerializedName("Value")
        @Expose
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }


    public class Balance {

        @SerializedName("Value")
        @Expose
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }





    public class Fees {

        @SerializedName("Value")
        @Expose
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    public class Payer {

        @SerializedName("Information")
        @Expose
        private String information;

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

    }
    }
