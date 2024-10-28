package com.example.vendeglatas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("MainPage.fxml"));
        Parent root = fxmlLoader.load();

        // A Stage beállítása teljes képernyőre
        stage.setMaximized(true);

        scene = new Scene(root);
        stage.setTitle("Éttermi Szoftver");
        stage.setScene(scene);
        stage.show();

        // Az ablak szélességének és magasságának lekérése, miután megjelent
        double width = stage.getWidth();
        double height = stage.getHeight();
        System.out.println("Width: " + width + " Height: " + height);
    }

    public static void setRoot(Parent resource) throws IOException{
        scene.setRoot(resource);
    }

    public static void main(String[] args) {
        launch();
    }
}