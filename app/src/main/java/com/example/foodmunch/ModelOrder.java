package com.example.foodmunch;

public class ModelOrder {
    String buyerUid,orderDetails,orderStatus,totalPrice;


    public ModelOrder() {
    }

    public ModelOrder(String buyerUid, String orderDetails, String orderStatus, String totalPrice) {
        this.buyerUid = buyerUid;
        this.orderDetails = orderDetails;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }


    public String getBuyerUid() {
        return buyerUid;
    }

    public void setBuyerUid(String buyerUid) {
        this.buyerUid = buyerUid;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
