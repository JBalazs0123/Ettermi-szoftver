package com.example.vendeglatas.modules;

public class Product {
    private int id;
    private String category;
    private String descripiton;
    private String name;
    private int price;

    public Product(int id) {
        this.id = id;
    }

    public Product(int id, String category, String descripiton, String name, int price) {
        this.id = id;
        this.category = category;
        this.descripiton = descripiton;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripiton() {
        return descripiton;
    }

    public void setDescripiton(String descripiton) {
        this.descripiton = descripiton;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
