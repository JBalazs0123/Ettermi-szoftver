package com.example.vendeglatas.modules;

public class Employe {
    private int id;
    private String restaurantName;
    private String name;
    private String password;
    private String post;

    public Employe(int id) {
        this.id = id;
    }

    public Employe(String restaurantName, String name, String password, String post) {
        this.restaurantName = restaurantName;
        this.name = name;
        this.password = password;
        this.post = post;
    }

    public Employe(int id, String restaurantName, String name, String password, String post) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.name = name;
        this.password = password;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
