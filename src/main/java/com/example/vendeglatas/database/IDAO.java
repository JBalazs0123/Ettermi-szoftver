package com.example.vendeglatas.database;

import com.example.vendeglatas.modules.Product;
import com.example.vendeglatas.modules.Restaurant;
import com.example.vendeglatas.database.DAO;

import java.util.List;

public interface IDAO {
    String DB_URL = "jdbc:mysql://localhost:3306/vendeglatas";
    String DRIVER = "com.mysql.cj.jdbc.Driver";
    String USER = "szakdoga";
    String PASS = "szakdoga!23";

    List<Restaurant> getRestaurants();
    void addRestaurant(Restaurant restaurant);

    void deleteRestaurant(String nev);

    List<Product> getProducts();
}
