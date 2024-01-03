package com.example.vendeglatas.modules;


import java.util.Date;

public class Order {
    private int id;
    private int tableNumber;
    private int employeId;
    private int numberOfProduct;

    public Order(int id, int tableNumber, int employeId, int numberOfProduct) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.employeId = employeId;
        this.numberOfProduct = numberOfProduct;
    }

    public Order(int tableNumber, int employeId, int numberOfProduct) {
        this.tableNumber = tableNumber;
        this.employeId = employeId;
        this.numberOfProduct = numberOfProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getEmployeId() {
        return employeId;
    }

    public void setEmployeId(int employeId) {
        this.employeId = employeId;
    }

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }
}
