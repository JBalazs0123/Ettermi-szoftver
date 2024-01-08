package com.example.vendeglatas.controllers;

import com.example.vendeglatas.StartApplication;
import com.example.vendeglatas.database.DAO;
import com.example.vendeglatas.modules.Bill;
import com.example.vendeglatas.modules.Employe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class DailyRecap {

    @FXML private DatePicker datePicker = new DatePicker();
    @FXML private Label billCounter = new Label();
    @FXML private Label profitCounter = new Label();
    @FXML private Label cashCounter = new Label();
    @FXML private Label cardCounter = new Label();
    private Employe currentEmploye;

    DAO dao = new DAO();

    public Employe getCurrentEmploye() {
        return currentEmploye;
    }

    public void setCurrentEmploye(Employe currentEmploye) {
        this.currentEmploye = currentEmploye;
    }

    public void onBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("Menu.fxml"));
        Parent root = loader.load();
        MenuController controller = loader.getController();
        controller.setCurrentEmploye(getCurrentEmploye());
        StartApplication.setRoot(root);
    }

    public void onDatePicker(ActionEvent actionEvent) {
        LocalDate date = this.datePicker.getValue();
        List<Bill> bills = dao.getBillByDate(date);
        int numberOfBills=0;
        int numberOfProfits=0;
        int numberOfCash=0;
        int numberOfCard=0;
        for (Bill bill: bills){
            numberOfBills++;
            numberOfProfits+=bill.getPrice();
            if(bill.getPayMethod().equals("Készpénz")){
                numberOfCash++;
            } else {
                numberOfCard++;
            }
        }
        billCounter.setText(numberOfBills + "");
        profitCounter.setText(numberOfProfits + " Ft");

        double percentage = ((double) numberOfCash / numberOfBills ) * 100;
        cashCounter.setText(percentage + "%");

        percentage = ((double) numberOfCard / numberOfBills ) * 100;
        cardCounter.setText(percentage + "%");

    }
}
