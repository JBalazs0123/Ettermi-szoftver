package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MainPageContorller {
    public void onCRUD(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("CRUD.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    public void onLogin(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("Menu.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }
}
