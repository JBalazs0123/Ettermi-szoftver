package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.database.DAO;
import com.example.vendeglatas.modules.Employe;
import com.example.vendeglatas.modules.Include;
import com.example.vendeglatas.modules.Order;
import com.example.vendeglatas.modules.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class OrderManagment implements Initializable {
    @FXML private HBox priceContainer = new HBox();
    @FXML private GridPane textContainerForBill = new GridPane();
    @FXML private GridPane buttonContainerForProductsCategory = new GridPane();
    @FXML private GridPane buttonContainerForProductsName = new GridPane();

    DAO dao = new DAO();
    private List<Include> currentIncludeList = new ArrayList<>();
    private Order currentOrder;
    private Employe currentEmploye;
    private int tableNumber = 0;
    private int amount = 0;
    private int rowForBill = 1;

    public Employe getCurrentEmploye() {
        return currentEmploye;
    }

    public void makeaNewOrder(){
        int nextOrderId = dao.getNextOrderId();
        currentOrder = new Order(nextOrderId, getTableNumber(), getCurrentEmploye().getId(), 0);
    }

    public void setCurrentEmploye(Employe currentEmploye, int tableNumber) {
        this.currentEmploye = currentEmploye;
        setTableNumber(tableNumber);
        makeaNewOrder();
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }


    //osszesen mezo
    private void handlePrice(int amount){
        Label amountGridPane = new Label("Összesen: " + amount + " Ft");
        amountGridPane.setFont(Font.font(14));
        priceContainer.getChildren().clear();
        priceContainer.getChildren().add(amountGridPane);
    }

    private void noMoreProductErrorMSG(){
        String errorMessage = "További termékek hozzáadása nem lehetséges!";

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Maximalis termékszám!");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }


    //Szamlahoz adja a termékeket, bőviti ennek megfelelően a currentOrder termekszam adattagjat
    //és a currentIncludeList-et is megfelelően bővíti
    private void handleProductName(Product product) {
        amount += product.getPrice();

        Label newProduct = new Label(product.getName());
        newProduct.setFont(Font.font(14));
        Label newProductPrice = new Label(product.getPrice() + "Ft");
        newProductPrice.setFont(Font.font(14));

        if(rowForBill>25){
            noMoreProductErrorMSG();
            return;
        }

        currentOrder.setNumberOfProduct(currentOrder.getNumberOfProduct() + 1);

        Include currentInclude = new Include(currentOrder.getId(), product.getId(), 1);
        currentIncludeList.add(currentInclude);

        textContainerForBill.add(newProduct, 0, rowForBill);
        textContainerForBill.add(newProductPrice, 1, rowForBill);
        rowForBill++;

        handlePrice(amount);
    }

    private List<String> buttonColors = Arrays.asList(
            "#ADD8E6", // világos kék
            "#90EE90", // világos zöld
            "#FFFFE0", // világos sárga
            "#FFA07A", // világos narancs
            "#FFB6C1",  // világos rózsaszín
            "#DDA0DD", // világos lila
            "#FFDEAD", // világos barackszín
            "#AFEEEE", // világos türkiz
            "#F5DEB3"  // világos bézs
    );

    private int colorIndex = 0;
    private void setButtonStyle(Button button) {
        button.setFont(Font.font(16));
        button.setPrefWidth(140);
        button.setPrefHeight(60);

        String color = buttonColors.get(colorIndex);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: black;");

        colorIndex = (colorIndex + 1) % buttonColors.size();
    }

    //Kategőria kattintás után listázza a termékeket
    public void handleProductCategory(List<Product> products, String category){
        buttonContainerForProductsName.getChildren().clear();
        Iterator<Product> iterator = products.iterator();
        int columnIndex = 0;
        int rowIndex = 0;
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getCategory().equals(category)){
                Button button = new Button(product.getName());
                setButtonStyle(button);
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

    //Termékek listázása, ez a függvény a termék kategóriákat írja ki
    public void listProducts(){
        List<Product> products = new DAO().getProducts();

        List<String> categoryOrder = Arrays.asList("Előételek", "Levesek", "Főételek", "Desszertek", "Üdítők", "Kávék", "Hosszú italok", "Rövid italok", "Egyéb");

        Set<String> uniqueCategories = products.stream()
                .map(Product::getCategory)
                .filter(categoryOrder::contains)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        List<String> sortedCategories = categoryOrder.stream()
                .filter(uniqueCategories::contains)
                .collect(Collectors.toList());


        int columnIndex = 0;
        int rowIndex = 0;

        // Rendezett kategóriák megjelenítése
        for (String category : sortedCategories) {
            Button button = new Button(category);
            setButtonStyle(button);
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


    //oldal létrehozásakor fut le
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

    public void saveOrder(){
        if(dao.checkOrder(currentOrder.getId())){
            dao.deletOrder(currentOrder.getId());
            dao.deleteIncludes(currentOrder.getId());
        }
        dao.saveOrder(currentOrder);

        Map<Integer, Integer> productIdAmountMap = new HashMap<>();
        boolean runCounter = true;
        int orderId = 0;
        for (Include include : currentIncludeList){
            System.out.println(include.toString());
            if(runCounter){
                orderId = include.getOrderId();
                runCounter = false;
            }
            int productId = include.getProductId();
            int amount = include.getAmount();

            productIdAmountMap.merge(productId, amount, Integer::sum);
        }
        for (Map.Entry<Integer, Integer> entry : productIdAmountMap.entrySet()){
            int productId = entry.getKey();
            int amount = entry.getValue();

            Include include = new Include(orderId, productId, amount);
            dao.saveInclude(include);
        }
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        saveOrder();

        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("Menu.fxml"));
        Parent root = loader.load();
        MenuController controller = loader.getController();
        controller.setCurrentEmploye(getCurrentEmploye());
        StartApplication.setRoot(root);
    }

    private void ordertoBill(String payMethod) throws IOException {
        this.currentOrder.setId(this.currentOrder.getId()-1);
        for(Include include: currentIncludeList){
            include.setOrderId(include.getOrderId()-1);
        }
        saveOrder();
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("BillHandler.fxml"));
        Parent root = loader.load();
        BillHandler controller = loader.getController();
        controller.setPayMethod(payMethod);
        controller.setOrder(currentOrder);
        StartApplication.setRoot(root);
    }

    private void questionForPayMethod() throws IOException {
        Alert paymentAlert = new Alert(Alert.AlertType.CONFIRMATION);
        paymentAlert.setTitle("Fizetési mód");
        paymentAlert.setHeaderText(null);
        paymentAlert.setContentText("Válassza ki a fizetési módot!");

        ButtonType cashButton = new ButtonType("Készpénz");
        ButtonType cardButton = new ButtonType("Bankkártya");

        paymentAlert.getButtonTypes().setAll(cashButton, cardButton);

        Optional<ButtonType> result = paymentAlert.showAndWait();
        if (result.isPresent() && result.get() == cashButton) {
            ordertoBill("Készpénz");
        } else {
            ordertoBill("Bankkártya");
        }
    }

    //Megkerdezi a user-t, valaszanak megfelelo oldalra iranyit
    private void questionForBillingBreakdown() throws IOException {
        String question = "Szeretné bontani a számlát?";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Számlabontás");
        alert.setHeaderText(null);
        alert.setContentText(question);

        ButtonType buttonTypeYes = new ButtonType("Igen");
        ButtonType buttonTypeNo = new ButtonType("Nem");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            saveOrder();
            FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("BillingBreakdown.fxml"));
            Parent root = loader.load();
            BillingBreakdown controller = loader.getController();
            controller.setCurrent(getCurrentEmploye(), getTableNumber(), currentOrder.getId());
            StartApplication.setRoot(root);
        } else {
            questionForPayMethod();
        }
    }

    public void onNextToPay(ActionEvent actionEvent) throws IOException {
        questionForBillingBreakdown();
    }

    public void onPreviousOrder(ActionEvent actionEvent) {
        List<Include> previousIncludes = dao.getIncludes(dao.getOrderId(getTableNumber(), getCurrentEmploye().getId()));
        currentOrder.setId(currentOrder.getId()-1);
        for(Include include : previousIncludes){
            for (int i=0; i<include.getAmount();i++){
                handleProductName(dao.getProductById(include.getProductId()));
            }
        }
    }
}
