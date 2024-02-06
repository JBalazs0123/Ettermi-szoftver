package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.database.DAO;
import com.example.vendeglatas.modules.Employe;
import com.example.vendeglatas.modules.Include;
import com.example.vendeglatas.modules.Order;
import com.example.vendeglatas.modules.Product;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class BillingBreakdown implements Initializable {

    @FXML private TableView tableListOfProduct;
    @FXML private TableView tableForNewProduct;
    @FXML private GridPane gridpaneForButton = new GridPane();
    @FXML private TableColumn<Product, String> listTableName;
    @FXML private TableColumn<Product, Integer> listTablePrice;
    @FXML private TableColumn<Product, Button> listTableMove;
    @FXML private TableColumn<Product, String> newTableName;
    @FXML private TableColumn<Product, Integer> newTablePrice;

    DAO dao = new DAO();

    private int orderId;

    private Employe currentEmploye;

    private int tableNumber;

    List<Product>newProducts = new ArrayList<>();


    public int getTableNumber() {
        return tableNumber;
    }

    public Employe getCurrentEmploye() {
        return currentEmploye;
    }

    public int getCurrentOrderId() {
        return orderId;
    }

    public void setCurrent(Employe currentEmploye, int tableNumber, int orderId) {
        this.currentEmploye = currentEmploye;
        this.tableNumber= tableNumber;
        this.orderId = orderId;
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("OrderManagment.fxml"));
        Parent root = loader.load();
        OrderManagment controller = loader.getController();
        controller.setCurrentEmploye(getCurrentEmploye(), getTableNumber());
        StartApplication.setRoot(root);
    }

    public void onToPay(ActionEvent actionEvent) {

    }

    public void setButtonForProductCategorie() {
        List<Product> products = dao.getProducts();
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
            //button.setOnAction(e -> handleProductCategory(products, category));
            gridpaneForButton.add(button, columnIndex, rowIndex);
            columnIndex++;
            if (columnIndex == 4) {
                columnIndex = 0;
                rowIndex++;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButtonForProductCategorie();
    }

    private List<Product> searchProductList() {
        List<Include> includes = dao.getIncludes(getCurrentOrderId());

        List<Product> products = new ArrayList<>();

        for (Include include : includes) {
            for(int i = 0; i < include.getAmount(); i++){
                products.add(dao.getProductById(include.getProductId()));
            }
        }
        return products;
    }

    public void onListProduct() {
        listTableName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        listTablePrice.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getPrice()).asObject());

        listTableMove.setCellValueFactory(new PropertyValueFactory<>("buttonProperty"));
        listTableMove.setCellFactory(tc -> {
            TableCell<Product, Button> cell = new TableCell<>() {
                final Button moveButton = new Button("->");
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setGraphic(moveButton);
                        moveButton.setOnAction(event -> handleMoveButtonClick(getTableView().getItems().get(getIndex())));
                    }
                }
            };
            return cell;
        });

        tableListOfProduct.getItems().setAll(searchProductList());
    }

    private void handleMoveButtonClick(Product product) {
        newProducts.add(product);
        dao.deleteIncludesOnlyOneItem(getCurrentOrderId(), product.getId());
        setNewTableData();
        onListProduct();
    }

    private void setNewTableData () {
        newTableName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        newTablePrice.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getPrice()).asObject());

        tableForNewProduct.getItems().setAll(newProducts);
    }
}
