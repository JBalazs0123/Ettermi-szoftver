package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.database.DAO;
import com.example.vendeglatas.modules.Employe;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.Console;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.*;

public class CreateProfile implements Initializable {
    @FXML private ComboBox nameBox = new ComboBox<String>();
    @FXML private ComboBox postBox = new ComboBox<String>();
    @FXML private Label messageRegist1;
    @FXML private TableColumn<Employe, String> nameTable;
    @FXML private TableColumn<Employe, String> postTable;
    @FXML private TableView listTable;
    @FXML private TextField pswRegistAgain;
    @FXML private Label messageRegist;
    @FXML private TextField nameRegist;
    @FXML private TextField pswRegist;
    @FXML private ComboBox postRegist = new ComboBox<String>();

    private Employe currentEmploye;

    public Employe getCurrentEmploye() {
        return currentEmploye;
    }

    public void setCurrentEmploye(Employe currentEmploye) {
        this.currentEmploye = currentEmploye;
    }

    public void onSaveRegist(ActionEvent actionEvent) throws IOException {
        String name = this.nameRegist.getText();
        String password = this.pswRegist.getText();
        String passwordAgain = this.pswRegistAgain.getText();
        String post = this.postRegist.getValue().toString();
        if(!password.equals(passwordAgain)){
            messageRegist.setText("A jelszó nem egyezik!");
            return;
        }
        new DAO().saveEmploye(new Employe("Etterem", name, password, post));
        reload("Sikeres regisztráció", 0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> posts = FXCollections.observableArrayList("Pincér", "Főpincér", "Vezető");
        postRegist.setItems(posts);
        postRegist.setValue("Pincér");

        nameTable.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        postTable.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPost()));
        listTable.getItems().setAll(new DAO().getEmploye());

        ArrayList<String> employeName = new ArrayList<>();
        List<Employe> employes = new DAO().getEmploye();
        Iterator<Employe> iterator = employes.iterator();
        while (iterator.hasNext()){
            Employe employe = iterator.next();
            employeName.add(employe.getName());
        }
        ObservableList<String> names = FXCollections.observableArrayList(employeName);
        nameBox.setItems(names);
        postBox.setItems(posts);
    }

    public void onCancelRegist(ActionEvent actionEvent) throws IOException {
        /*
        List<Employe> employes = new DAO().getEmploye();
        Iterator<Employe> iterator = employes.iterator();
        while (iterator.hasNext()){
            Employe employe1 = iterator.next();
            if(employe1.getPassword().equals("asd")){
                System.out.println("ugyanaz");
            }
        }
        */
        reload();
    }

    public void reload() throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("CreateProfile.fxml"));
        Parent root = loader.load();
        CreateProfile controller = loader.getController();
        controller.setCurrentEmploye(getCurrentEmploye());
        StartApplication.setRoot(root);
    }
    public void reload(String message, int id) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("CreateProfile.fxml"));
        Parent root = loader.load();
        CreateProfile controller = loader.getController();
        controller.setCurrentEmploye(getCurrentEmploye());
        StartApplication.setRoot(root);
        if(id == 0){
            messageRegist.setText(message);
        } else {
            messageRegist1.setText(message);
        }
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("Menu.fxml"));
        Parent root = loader.load();
        MenuController controller = loader.getController();
        controller.setCurrentEmploye(getCurrentEmploye());
        StartApplication.setRoot(root);
    }

    public void onPostUpdate(ActionEvent actionEvent) throws IOException {
        String name = this.nameBox.getValue().toString();
        String post = this.postBox.getValue().toString();
        new DAO().updateEmploye(name, post);
        reload("Sikeres módosítás!", 1);
    }

    public void onDelete(ActionEvent actionEvent) throws IOException {
        String name = this.nameBox.getValue().toString();
        new DAO().deleteEmploye(name);
        new DAO().deleteEmploye(name);
        reload("Sikeres törlés!", 1);
    }
}
