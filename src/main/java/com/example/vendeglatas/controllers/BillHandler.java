package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.database.DAO;
import com.example.vendeglatas.modules.Employe;
import com.example.vendeglatas.modules.Include;
import com.example.vendeglatas.modules.Order;
import com.example.vendeglatas.modules.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class BillHandler implements Initializable {

    @FXML private GridPane firstGrid = new GridPane();
    private Order order;
    private String payMethod;

    private Date date = new Date();

    private Employe employe;

    private DAO dao = new DAO();

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        System.out.println(getOrder().getId() + ", " + getPayMethod());
        System.exit(1000);
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("OrderManagment.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onBacktoMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("Menu.fxml"));
        Parent root = loader.load();
        MenuController controller = loader.getController();
        controller.setCurrentEmploye(dao.getEmployeById(getOrder().getEmployeId()));
        StartApplication.setRoot(root);
    }

    public void onLogout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("MainPage.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    public void saveBill(){

    }

    public void onList(ActionEvent actionEvent) {

        employe = dao.getEmployeById(getOrder().getEmployeId());
        firstGrid.add(new Label("Étterem: "), 0, 0);
        firstGrid.add(new Label(employe.getRestaurantName()), 1, 0);
        firstGrid.add(new Label("Felszolgáló: "), 0, 1);
        firstGrid.add(new Label(employe.getName()), 1, 1);
        firstGrid.add(new Label("Beosztása: "), 0, 2);
        firstGrid.add(new Label(employe.getPost()), 1, 2);
        firstGrid.add(new Label("Asztal: "), 0, 3);
        firstGrid.add(new Label(getOrder().getTableNumber() + ""), 1, 3);
        firstGrid.add(new Label("Tételek: "), 0, 4);
        List<Include> includes = dao.getIncludes(order.getId());
        int row = 4;
        int amount = 0;
        for (Include include: includes){
            for(int i=0;i<include.getAmount();i++){
                Product product = dao.getProductById(include.getProductId());
                firstGrid.add(new Label(product.getName()), 1, row);
                firstGrid.add(new Label(product.getPrice() + " Ft"), 2, row);
                amount += product.getPrice();
                row++;
            }
        }
        /*firstGrid.add(new Label("Végösszeg: "), 0, (row));
        firstGrid.add(new Label(amount + " Ft"), 1, (row));
        firstGrid.add(new Label("Fizetési mód"), 0, (row+1));
        firstGrid.add(new Label(getPayMethod()), 1, (row+1));
        firstGrid.add(new Label("Dátum: "), 0, (row+2));
        firstGrid.add(new Label(date.toString()), 1, (row+2));
        firstGrid.add(new Label("Rendelés azonosító: " + getOrder().getId()), 0, (row+3));
        firstGrid.add(new Label(""), 1, (row+3)); //szamla azonosito todo*/
        firstGrid.add(new Label("Végösszeg: "), 0, 29);
        firstGrid.add(new Label(amount + " Ft"), 1, 29);
        firstGrid.add(new Label("Fizetési mód"), 0, 30);
        firstGrid.add(new Label(getPayMethod()), 1, 30);
        firstGrid.add(new Label("Dátum: "), 0, 31);
        firstGrid.add(new Label(date.toString()), 1, 31);
        firstGrid.add(new Label("Rendelés:  " + getOrder().getId()), 0, 32);
        firstGrid.add(new Label(""), 1, 32); //szamla azonosito todo
    }
}
