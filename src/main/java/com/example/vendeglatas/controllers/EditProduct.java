package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.database.DAO;
import com.example.vendeglatas.modules.Employe;
import com.example.vendeglatas.modules.Product;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class EditProduct implements Initializable {
    @FXML private Spinner<Integer> editPrice = new Spinner<>();
    @FXML private ComboBox editName = new ComboBox<String>();
    @FXML private TextField newName;
    @FXML private Spinner<Integer> newPrice = new Spinner<>();
    @FXML private ComboBox newCategory = new ComboBox<String>();
    @FXML private TableView tableForProducts;
    @FXML private TableColumn<Product, String> tableCategory;
    @FXML private TableColumn<Product, String> tableProduct;
    @FXML private TableColumn<Product, Integer> tablePrice;

    DAO dao = new DAO();

    public void reload() throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("EditProduct.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("Menu.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableCategory.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategory()));
        tableProduct.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        tablePrice.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getPrice()).asObject());
        tableForProducts.getItems().setAll(dao.getProducts());

        ObservableList<String> categories = FXCollections.observableArrayList("Előételek", "Levesek", "Főátelek", "Desszertek", "Üdítők", "Kávék", "Hosszú italok", "Rövid italok", "Egyéb");
        newCategory.setItems(categories);

        ArrayList<String> productName = new ArrayList<>();
        List<Product> productList = dao.getProducts();
        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()){
            Product product = iterator.next();
            productName.add(product.getName());
        }
        ObservableList<String> names = FXCollections.observableArrayList(productName);
        editName.setItems((names));

        SpinnerValueFactory<Integer> spinnerValueFactoryForNew = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999, 0);
        SpinnerValueFactory<Integer> spinnerValueFactoryForEdit = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99999, 0);
        newPrice.setValueFactory(spinnerValueFactoryForNew);
        editPrice.setValueFactory(spinnerValueFactoryForEdit);
        newPrice.setEditable(true);
        editPrice.setEditable(true);
    }

    public void onSaveNewProduct(ActionEvent actionEvent) throws IOException {
        String name = this.newName.getText();
        int price = this.newPrice.getValue();
        String category = this.newCategory.getValue().toString();
        dao.saveProduct(new Product(category, name, price));
        reload();
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        reload();
    }

    public void onEditEdit(ActionEvent actionEvent) throws IOException {
        String name = this.editName.getValue().toString();
        int price = this.editPrice.getValue();
        dao.updateProduct(name, price);
        reload();
    }

    public void onEditDelete(ActionEvent actionEvent) throws IOException {
        String name = this.editName.getValue().toString();
        dao.deleteProduct(name);
        reload();
    }
}
