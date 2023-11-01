package com.example.vendeglatas.modules;

import java.util.Date;

public class bill {
    private int id;
    private int employeId;
    private int price;
    private Date time;
    private String payMethod;

    public bill(int id, int employeId, int price, Date time, String payMethod) {
        this.id = id;
        this.employeId = employeId;
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

    public int getEmployeId() {
        return employeId;
    }

    public void setEmployeId(int employeId) {
        this.employeId = employeId;
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
