package com.example.vendeglatas.modules;

public class Product {
    private int id;
    private String descripiton;
    private int amount;
    private int price;

    public Product(int id, String descripiton, int amount, int price) {
        this.id = id;
        this.descripiton = descripiton;
        this.amount = amount;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripiton() {
        return descripiton;
    }

    public void setDescripiton(String descripiton) {
        this.descripiton = descripiton;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
