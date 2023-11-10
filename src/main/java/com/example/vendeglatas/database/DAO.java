package com.example.vendeglatas.database;

import com.example.vendeglatas.modules.Employe;
import com.example.vendeglatas.modules.Product;
import com.example.vendeglatas.modules.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DAO implements IDAO{

    Connection DAO() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        return con;
    }

    public void saveEmploye(Employe employe){
        String sql = "INSERT INTO alkalmazott(nev, alkalmazottNev, jelszo, beosztas)" +
                " VALUES ('" + employe.getRestaurantName() + "', '" + employe.getName() + "', '" + employe.getPassword() + "', '" + employe.getPost() + "')";
        try {
            Connection con = DAO();
            Statement statement = con.createStatement();
            statement.execute(sql);
            statement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employe> getEmploye() {
        String sql = "SELECT * FROM alkalmazott";
        List<Employe> employes = new ArrayList<>();
        try {
            Connection connection = DAO();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Employe tmp = new Employe(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5));
                employes.add(tmp);
            }
            resultSet.close();
            connection.close();
        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return employes;
    }

    public void deleteEmploye(String name) {
        String sql ="DELETE FROM alkalmazott WHERE alkalmazottNev='" + name + "'";
        try{
            Connection con = DAO();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEmploye(String name, String newPost){
        String sql ="UPDATE alkalmazott SET beosztas = '" + newPost + "'WHERE alkalmazottNev='" + name + "'";
        try{
            Connection con = DAO();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getProducts() {
        String sql = "SELECT * FROM termek";
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = DAO();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Product tmp = new Product(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
                products.add(tmp);
            }
            resultSet.close();
            connection.close();
        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public List<Restaurant> getRestaurants() {
        String sql = "SELECT * FROM etterem";
        List<Restaurant> restaurant = new ArrayList<>();
        try{
            Connection con = DAO();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Restaurant tmp = new Restaurant(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) );
                restaurant.add(tmp);
            }
            rs.close();
            con.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return restaurant;
    }

    public void addRestaurant(Restaurant restaurant){
        String sql = "INSERT INTO etterem VALUES (?, ? , ? ,?)";
        try {
            Connection con = DAO();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, restaurant.getName());
            statement.setString(2, restaurant.getAddress());
            statement.setString(3, restaurant.getEmail());
            statement.setString(4, restaurant.getPhoneNumber());
            statement.executeUpdate();
            statement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteRestaurant(String nev){
        String sql ="DELETE FROM etterem WHERE nev='" + nev + "'";
        try{
            Connection con = DAO();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
