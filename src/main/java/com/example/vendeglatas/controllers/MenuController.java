package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.modules.Employe;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuController {

    public Button buttonOrderManagment;
    public Button buttonDailyRecap;
    public Button buttonCreateProfile;
    public Button buttonEditProduct;
    private Employe currentEmploye;

    public Employe getCurrentEmploye() {
        return currentEmploye;
    }

    public void setCurrentEmploye(Employe currentEmploye) {
        this.currentEmploye = currentEmploye;
        if(this.currentEmploye.getPost().equals("Pincér")){
            buttonEditProduct.setVisible(false);
            buttonCreateProfile.setVisible(false);
            buttonDailyRecap.setVisible(false);
        } else if (this.currentEmploye.getPost().equals("Főpincér")) {
            buttonEditProduct.setVisible(false);
            buttonCreateProfile.setVisible(false);
        }
    }

    public void onTestTable1(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("OrderManagment.fxml"));
        Parent root = loader.load();
        OrderManagment controller = loader.getController();
        controller.setCurrentEmploye(this.currentEmploye, 1);
        StartApplication.setRoot(root);
    }

    public void onTestTable2(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("OrderManagment.fxml"));
        Parent root = loader.load();
        OrderManagment controller = loader.getController();
        controller.setCurrentEmploye(this.currentEmploye, 2);
        StartApplication.setRoot(root);
    }

    public void onTestTable3(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("OrderManagment.fxml"));
        Parent root = loader.load();
        OrderManagment controller = loader.getController();
        controller.setCurrentEmploye(this.currentEmploye, 3);
        StartApplication.setRoot(root);
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

    public void onExit(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("MainPage.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }
}
