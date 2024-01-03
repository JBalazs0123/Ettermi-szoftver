package com.example.vendeglatas.modules;

import java.util.Date;

public class Bill {
    private int id;
    private int orderId;
    private int price;
    private Date time;
    private String payMethod;

    public Bill(int id, int orderId, int price, Date time, String payMethod) {
        this.id = id;
        this.orderId = orderId;
        this.price = price;
        this.time = time;
        this.payMethod = payMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }
}
