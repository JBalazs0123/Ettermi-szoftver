package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.database.DAO;
import com.example.vendeglatas.modules.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class OrderManagment implements Initializable {
    @FXML private HBox priceContainer = new HBox();
    @FXML private GridPane textContainerForBill = new GridPane();
    @FXML private GridPane buttonContainerForProductsCategory = new GridPane();
    @FXML private GridPane buttonContainerForProductsName = new GridPane();

    private int amount = 0;
    private int rowForBill = 1;

    private void handlePrice(int amount){
        Label amountGridPane = new Label("Összesen: " + amount + " Ft");
        amountGridPane.setFont(Font.font(14));
        priceContainer.getChildren().clear();
        priceContainer.getChildren().add(amountGridPane);
    }

    private void handleProductName(Product product) {
        amount += product.getPrice();

        Label newProduct = new Label(product.getName());
        newProduct.setFont(Font.font(14));
        Label newProductPrice = new Label(product.getPrice() + "Ft");
        newProductPrice.setFont(Font.font(14));

        if(rowForBill>25){
            String errorMessage = "További termékek hozzáadása nem lehetséges!";

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Maximalis termékszám!");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return;
        }

        textContainerForBill.add(newProduct, 0, rowForBill);
        textContainerForBill.add(newProductPrice, 1, rowForBill);
        rowForBill++;

        handlePrice(amount);

    }

    public void handleProductCategory(List<Product> products, String category){
        buttonContainerForProductsName.getChildren().clear();
        Iterator<Product> iterator = products.iterator();
        int columnIndex = 0;
        int rowIndex = 0;
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getCategory().equals(category)){
                Button button = new Button(product.getName());
                button.setFont(Font.font(14));
                button.setPrefWidth(140);
                button.setPrefHeight(60);
                button.setOnAction(e -> handleProductName(product));
                buttonContainerForProductsName.add(button, columnIndex, rowIndex);
                columnIndex++;
                if (columnIndex == 5) {
                    columnIndex = 0;
                    rowIndex++;
                }
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
        int columnIndex = 0;
        int rowIndex = 0;
        for (String category : uniqueCategory){
            Button button = new Button(category);
            button.setFont(Font.font(16));
            button.setPrefWidth(140);
            button.setPrefHeight(60);
            button.setOnAction(e -> handleProductCategory(products, category));
            buttonContainerForProductsCategory.add(button, columnIndex, rowIndex);
            columnIndex++;
            if (columnIndex == 5) {
                columnIndex = 0;
                rowIndex++;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Label labelProducts = new Label("Termékek");
        labelProducts.setFont(Font.font(16));
        Label labelPrice = new Label("Ár");
        labelPrice.setFont(Font.font(16));
        textContainerForBill.add(labelProducts, 0, 0);
        textContainerForBill.add(labelPrice, 1, 0);

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
