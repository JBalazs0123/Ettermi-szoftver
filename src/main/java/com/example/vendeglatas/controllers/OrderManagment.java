package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.database.DAO;
import com.example.vendeglatas.modules.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class OrderManagment implements Initializable {
    @FXML private VBox textContainerForPrice = new VBox();
    @FXML private VBox textContainerForBill = new VBox();
    @FXML private VBox buttonContainerForProductsCategory = new VBox();
    @FXML private VBox buttonContainerForProductsName = new VBox();
    private int amount = 0;

    private void handlePrice(int amount){
        textContainerForPrice.getChildren().clear();
        Label price = new Label("Összesen: " + amount);
        textContainerForPrice.getChildren().addLast(price);
    }

    private void handleProductName(Product product) {
        amount += product.getPrice();
        Label newProduct = new Label(product.getName() + ", " + product.getPrice() + "Ft");
        textContainerForBill.getChildren().add(newProduct);
        handlePrice(amount);

    }

    public void handleProductCategory(List<Product> products, String category){
        buttonContainerForProductsName.getChildren().clear();
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getCategory().equals(category)){
                Button button = new Button(product.getName());
                button.setOnAction(e -> handleProductName(product));
                buttonContainerForProductsName.getChildren().add(button);
            }
        }
    }

    public void listProducts(){
        List<Product> products = new DAO().getProducts();
        Iterator<Product> iterator = products.iterator();
        Set<String> uniqueCategory = new HashSet<>();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            uniqueCategory.add(product.getCategory());
        }
        for (String category : uniqueCategory){
            Button button = new Button(category);
            button.setOnAction(e -> handleProductCategory(products, category));
            buttonContainerForProductsCategory.getChildren().add(button);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Label label = new Label("xyz asztal számlája");
        textContainerForBill.getChildren().add(label);
        listProducts();
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("Menu.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    public void onNextToPay(ActionEvent actionEvent) {
    }
}
