package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class BillHandler {
    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("OrderManagment.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }
}
