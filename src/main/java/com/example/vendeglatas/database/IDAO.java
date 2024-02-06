package com.example.vendeglatas.database;

import com.example.vendeglatas.modules.Product;
import com.example.vendeglatas.modules.Restaurant;
import com.example.vendeglatas.database.DAO;

import java.util.List;

public interface IDAO {
    String DB_URL = "jdbc:mysql://localhost:3307/vendeglatas";
    String DRIVER = "com.mysql.cj.jdbc.Driver";
    String USER = "root";
    String PASS = "";

    List<Restaurant> getRestaurants();
    void addRestaurant(Restaurant restaurant);

    void deleteRestaurant(String nev);

    List<Product> getProducts();
}
