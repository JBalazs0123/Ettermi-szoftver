package com.example.vendeglatas.database;

import com.example.vendeglatas.modules.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DAO implements IDAO{

    Connection DAO() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
        return con;
    }

    public List<Bill> getBillByDate(LocalDate date){
        String sql ="SELECT * FROM szamla WHERE idopont='" + date + "'";
        List<Bill> bills = new ArrayList<>();
        try {
            Connection connection = DAO();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Bill tmp = new Bill(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getDate(4), resultSet.getString(5));
                bills.add(tmp);
            }
            resultSet.close();
            connection.close();
        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return bills;
    }

    public void updateProduct(String name, int price){
        String sql ="UPDATE termek SET ar = '" + price + "'WHERE termékNév='" + name + "'";
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

    public void deleteProduct(String name){
        String sql ="DELETE FROM termek WHERE termékNév='" + name + "'";
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

    public void saveProduct(Product product){
        String sql = "INSERT INTO termek VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = DAO();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setNull(1, java.sql.Types.INTEGER);
            statement.setString(2, product.getCategory());
            statement.setString(3, null);
            statement.setString(4, product.getName());
            statement.setInt(5, product.getPrice());
            statement.executeUpdate();
            statement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBill(Bill bill){
        String sql = "INSERT INTO szamla VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = DAO();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setNull(1, java.sql.Types.INTEGER);
            statement.setInt(2, bill.getOrderId());
            statement.setInt(3, bill.getPrice());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = sdf.format(bill.getTime());
            statement.setString(4, formattedDate);
            statement.setString(5, bill.getPayMethod());
            statement.executeUpdate();
            statement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletOrder(int orderId){
        String sql ="DELETE FROM rendeles WHERE rendelesazonosito='" + orderId + "'";
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

    public void deleteIncludesOnlyOneItem(int orderId, int productId) {
        String deleteSql = "DELETE FROM tartalmaz WHERE rendelesAzonosito = ? AND termekAzonosito = ? AND mennyiség = 1";
        String updateSql = "UPDATE tartalmaz SET mennyiség = mennyiség - 1 WHERE rendelesAzonosito = ? AND termekAzonosito = ? AND mennyiség > 1";

        try {
            Connection con = DAO();

            // Első lépés: DELETE végrehajtása
            PreparedStatement deleteStatement = con.prepareStatement(deleteSql);
            deleteStatement.setInt(1, orderId);
            deleteStatement.setInt(2, productId);
            deleteStatement.executeUpdate();
            deleteStatement.close();

            // Második lépés: UPDATE végrehajtása
            PreparedStatement updateStatement = con.prepareStatement(updateSql);
            updateStatement.setInt(1, orderId);
            updateStatement.setInt(2, productId);
            updateStatement.executeUpdate();
            updateStatement.close();

            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteIncludes(int orderId){
        String sql ="DELETE FROM tartalmaz WHERE rendelesAzonosito='" + orderId + "'";
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

    public boolean checkOrder(int orderId){
        String sql ="SELECT * FROM rendeles WHERE rendelesAzonosito='" + orderId + "'";
        try {
            Connection connection = DAO();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return true;
            }
            resultSet.close();
            connection.close();
        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Product getProductById(int id){
        String sql ="SELECT * FROM termek WHERE termekAzonosito='" + id + "'";
        Product product = new Product(id);
        try {
            Connection connection = DAO();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                product = new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
            }
            resultSet.close();
            connection.close();
        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    public int getOrderId(int tableNumber, int employeId){
        int orderId = -1;
        try {
            Connection connection = DAO();
            Statement statement = connection.createStatement();
            String sql = "SELECT MAX(rendelesAzonosito) FROM rendeles WHERE asztalszam='" + tableNumber + "' AND alkalmazottAzonosito='" + employeId + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                orderId = resultSet.getInt(1);
            }

            resultSet.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return orderId;
    }

    public List<Include> getIncludes(int orderId){
        String sql ="SELECT * FROM tartalmaz WHERE rendelesAzonosito='" + orderId + "'";
        List<Include> includes = new ArrayList<>();
        try {
            Connection connection = DAO();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Include tmp = new Include(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3));
                includes.add(tmp);
            }
            resultSet.close();
            connection.close();
        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return includes;
    }

    public void saveInclude(Include include){
        String sql = "INSERT INTO tartalmaz VALUES (?, ?, ?)";
        try {
            Connection connection = DAO();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, include.getOrderId());
            statement.setInt(2, include.getProductId());
            statement.setInt(3, include.getAmount());
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveOrder(Order order){
        String sql = "INSERT INTO rendeles VALUES (?, ?, ?, ?)";
        try {
            Connection con = DAO();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, order.getId());
            statement.setInt(2, order.getTableNumber());
            statement.setInt(3, order.getEmployeId());
            statement.setInt(4, order.getNumberOfProduct());
            statement.executeUpdate();
            statement.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNextOrderId(){
        int biggestOrderId = -1;
        try {
            Connection connection = DAO();
            Statement statement = connection.createStatement();
            String sql = "SELECT MAX(rendelesAzonosito) FROM rendeles";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                biggestOrderId = resultSet.getInt(1);
                biggestOrderId++;
            }

            resultSet.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return biggestOrderId;
    }

    public void saveEmploye(Employe employe) {
        String sql = "INSERT INTO alkalmazott(nev, alkalmazottNev, jelszo, beosztas) VALUES (?, ?, ?, ?)";

        try (Connection con = DAO();
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            preparedStatement.setString(1, employe.getRestaurantName());
            preparedStatement.setString(2, employe.getName());
            preparedStatement.setString(3, employe.getPassword());
            preparedStatement.setString(4, employe.getPost());

            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Employe getEmployeById(int id) {
        String sql = "SELECT * FROM alkalmazott WHERE alkalmazottAzonosito='" + id + "'";
        Employe employe = new Employe(id);
        try {
            Connection connection = DAO();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                employe = new Employe(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5));
            }
            resultSet.close();
            connection.close();
        }  catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return employe;
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
