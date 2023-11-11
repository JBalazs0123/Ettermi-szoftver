package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.database.DAO;
import com.example.vendeglatas.modules.Employe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class MainPageContorller {
    @FXML private TextField nameLogin;
    @FXML private TextField pswLogin;
    @FXML private Label messageBox;

    public void onLogin(ActionEvent actionEvent) throws IOException {
        String name = this.nameLogin.getText();
        String password = this.pswLogin.getText();
        List<Employe> users = new DAO().getEmploye();
        for (Employe employe: users){
            if(name.equals(employe.getName()) && password.equals(employe.getPassword())){
                FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("Menu.fxml"));
                Parent root = loader.load();
                MenuController controller = loader.getController();
                controller.setCurrentEmploye(employe);
                StartApplication.setRoot(root);
            }
        }
        messageBox.setText("Hibás név vagy jelszó!");
    }


    public void onCRUD(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("CRUD.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

}
