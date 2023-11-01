package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.database.DAO;
import com.example.vendeglatas.modules.Restaurant;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CRUDcontroller implements Initializable {
    @FXML private TextField deleteRestaurant;
    @FXML private TableView table;
    @FXML private TableColumn<Restaurant, String> nameCol;
    @FXML private TableColumn<Restaurant, String> addressCol;
    @FXML private TableColumn<Restaurant, String> emailCol;
    @FXML private TableColumn<Restaurant, String> phoneNumberCol;

    public void OnAddNewRestaurant(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("AddRestaurant.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    public void onDeleteRestaurant(ActionEvent actionEvent) throws IOException {
        String RestaurantName = this.deleteRestaurant.getText();
        new DAO().deleteRestaurant(RestaurantName);
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("CRUD.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        addressCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAddress()));
        emailCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        phoneNumberCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPhoneNumber()));

        table.getItems().setAll(new DAO().getRestaurants());
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("MainPage.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }
}