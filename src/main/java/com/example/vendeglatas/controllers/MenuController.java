package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MenuController {

    public void onTestTable1(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("OrderManagment.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    public void onTestTable2(ActionEvent actionEvent) {
    }

    public void onTestTable3(ActionEvent actionEvent) {

    }

    public void onOrderManagment(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("OrderManagment.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    public void onDailyRecap(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("DailyRecap.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    public void onCreateProfile(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("CreateProfile.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    public void onEditProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("EditProduct.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("MainPage.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

}
