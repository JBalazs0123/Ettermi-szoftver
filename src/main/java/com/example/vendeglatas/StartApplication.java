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
        scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Vendeglatas!");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(Parent resource) throws IOException{
        scene.setRoot(resource);
    }

    public static void main(String[] args) {
        launch();
    }
}