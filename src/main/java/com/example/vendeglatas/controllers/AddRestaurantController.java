package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.database.DAO;
import com.example.vendeglatas.modules.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddRestaurantController implements Initializable {

    @FXML private TextField nev;
    @FXML private TextField cim;
    @FXML private TextField email;
    @FXML private TextField telefonszam;

    public void onSaveRestaurant(ActionEvent actionEvent) throws IOException {
        String nev = this.nev.getText();
        String cim = this.cim.getText();
        String email = this.email.getText();
        String telefonszam = this.telefonszam.getText();

         new DAO().addRestaurant(new Restaurant(nev, cim, email, telefonszam));
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("CRUD.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("CRUD.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }
}
