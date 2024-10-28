package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.modules.Employe;
import com.example.vendeglatas.modules.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuController implements Initializable {

    public Button buttonOrderManagment;
    public Button buttonDailyRecap;
    public Button buttonCreateProfile;
    public Button buttonEditProduct;
    public Button editButtons;
    public Button newButton;
    public Pane buttonPane;
    private Employe currentEmploye;
    private List<Table> tables = new ArrayList<>();

    public Employe getCurrentEmploye() {
        return currentEmploye;
    }

    public void setCurrentEmploye(Employe currentEmploye) {
        this.currentEmploye = currentEmploye;
        if (this.currentEmploye.getPost().equals("Pincér")) {
            buttonEditProduct.setVisible(false);
            buttonCreateProfile.setVisible(false);
            buttonDailyRecap.setVisible(false);
        } else if (this.currentEmploye.getPost().equals("Főpincér")) {
            buttonEditProduct.setVisible(false);
            buttonCreateProfile.setVisible(false);
        }
    }

    public void onOrderManagment(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("OrderManagment.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    public void onDailyRecap(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("DailyRecap.fxml"));
        Parent root = loader.load();
        DailyRecap controller = loader.getController();
        controller.setCurrentEmploye(getCurrentEmploye());
        StartApplication.setRoot(root);
    }

    public void onCreateProfile(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("CreateProfile.fxml"));
        Parent root = loader.load();
        CreateProfile controller = loader.getController();
        controller.setCurrentEmploye(getCurrentEmploye());
        StartApplication.setRoot(root);
    }

    public void onEditProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("EditProduct.fxml"));
        Parent root = loader.load();
        EditProduct controller = loader.getController();
        controller.setCurrentEmploye(getCurrentEmploye());
        StartApplication.setRoot(root);
    }

    public void onExit(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("MainPage.fxml"));
        Parent root = loader.load();
        StartApplication.setRoot(root);
    }

    public boolean isEditable = false;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private static final String SAVE_FILE = "button_positions.json";

    public void onMouseDragged(MouseEvent mouseEvent, Button button) {
        if (!isEditable) {
            return;
        }
        double offsetX = mouseEvent.getSceneX() - orgSceneX;
        double offsetY = mouseEvent.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        // Check boundaries
        if (newTranslateX >= 0 && newTranslateX <= button.getParent().getLayoutBounds().getWidth() - button.getWidth()) {
            button.setTranslateX(newTranslateX);
        }
        if (newTranslateY >= 0 && newTranslateY <= button.getParent().getLayoutBounds().getHeight() - button.getHeight()) {
            button.setTranslateY(newTranslateY);
        }

        // Update table position
        Table table = (Table) button.getUserData();
        table.setTranslateX(button.getTranslateX());
        table.setTranslateY(button.getTranslateY());
    }
    public void onMousePressed(MouseEvent mouseEvent, Button button) {
        if(!isEditable){ return; }
        orgSceneX = mouseEvent.getSceneX();
        orgSceneY = mouseEvent.getSceneY();
        orgTranslateX = button.getTranslateX();
        orgTranslateY = button.getTranslateY();
    }

    public void onEditButtons(ActionEvent actionEvent) {
        isEditable = !isEditable;
        newButton.setVisible(isEditable);

        // Save positions
        savePositions();
    }

    private int tableNumber = 4;

    private List<String> buttonColors = Arrays.asList(
            "#ADD8E6", // világos kék
            "#90EE90", // világos zöld
            "#FFFFE0", // világos sárga
            "#FFA07A", // világos narancs
            "#FFB6C1"  // világos rózsaszín
    );
    public void onNewButton(ActionEvent actionEvent) {
        // Megkeressük a legnagyobb asztalszámot a tables listában
        int maxTableNumber = tables.stream()
                .mapToInt(Table::getTableNumber)
                .max()
                .orElse(0); // Ha nincs asztal, kezdjük 0-ról

        // Beállítjuk a következő asztalszámot
        tableNumber = maxTableNumber + 1;

        Button newButton = new Button("Asztal " + tableNumber);

        // Véletlenszerű szín kiválasztása a listából
        Random random = new Random();
        String randomColor = buttonColors.get(random.nextInt(buttonColors.size()));
        newButton.setStyle("-fx-background-color: " + randomColor + "; -fx-text-fill: black;");


        newButton.setTranslateX(0); // vagy bármi más kezdő pozíció
        newButton.setTranslateY(0); // vagy bármi más kezdő pozíció

        Table table = new Table(tableNumber++, newButton.getTranslateX(), newButton.getTranslateY());
        newButton.setUserData(table);  // Hozzárendeljük a Table objektumot a gombhoz

        buttonPane.getChildren().add(newButton);

        newButton.setOnMousePressed(event -> onMousePressed(event, newButton));
        newButton.setOnMouseDragged(event -> onMouseDragged(event, newButton));
        newButton.setOnMouseClicked(event -> {
            try {
                onButtonClicked(event, newButton);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        tables.add(table);
    }

    public void onButtonClicked(MouseEvent event, Button button) throws IOException {
        if(isEditable){ return; }

        // Extract the button's text
        String buttonText = button.getText();

        // Use a regex pattern to find the integer value in the button's text
        Pattern pattern = Pattern.compile("Asztal (\\d+)");
        Matcher matcher = pattern.matcher(buttonText);

        int tableNumber = 0;
        if (matcher.find()) {
            tableNumber = Integer.parseInt(matcher.group(1));
        } else {
            // Handle the case where the pattern does not match
            throw new IllegalArgumentException("Button text does not match the expected pattern 'Asztal X'");
        }

        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("OrderManagment.fxml"));
        Parent root = loader.load();
        OrderManagment controller = loader.getController();
        controller.setCurrentEmploye(this.currentEmploye, tableNumber);
        StartApplication.setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPositions();
        for (Table table : tables) {
            Button button = new Button("Asztal " + table.getTableNumber());

            // Véletlenszerű szín kiválasztása a listából
            Random random = new Random();
            String randomColor = buttonColors.get(random.nextInt(buttonColors.size()));
            button.setStyle("-fx-background-color: " + randomColor + "; -fx-text-fill: black;");

            button.setTranslateX(table.getTranslateX());
            button.setTranslateY(table.getTranslateY());
            button.setUserData(table);  // Hozzárendeljük a Table objektumot a gombhoz

            buttonPane.getChildren().add(button);
            button.setOnMousePressed(event -> onMousePressed(event, button));
            button.setOnMouseDragged(event -> onMouseDragged(event, button));
            button.setOnMouseClicked(event -> {
                try {
                    onButtonClicked(event, button);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        if (!isEditable) {
            newButton.setVisible(false);
        }
    }

    public void savePositions() {
        if (tables.isEmpty()) {
            System.out.println("No tables to save.");
            return;
        }
        System.out.println("Saving " + tables.size() + " tables.");

        for (Table table : tables) {
            System.out.println(table.getTableNumber() + ", " + table.getTranslateX() + ", " + table.getTranslateY());
            System.out.println();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(SAVE_FILE), tables);
            System.out.println("Positions saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving positions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadPositions() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(SAVE_FILE);
            if (file.exists()) {
                Table[] loadedTables = mapper.readValue(file, Table[].class);
                tables.clear();
                tables.addAll(Arrays.asList(loadedTables));
                System.out.println("Loaded " + tables.size() + " tables.");
            } else {
                System.out.println("No saved positions found.");
            }
        } catch (IOException e) {
            System.out.println("Positions load unsuccessfully.");
        }
    }
}
