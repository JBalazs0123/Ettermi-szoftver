package com.example.vendeglatas.modules;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Table implements Serializable {
    @JsonProperty("tableNumber")
    private int tableNumber;

    @JsonProperty("translateX")
    private double translateX;

    @JsonProperty("translateY")
    private double translateY;

    public Table() {
    }

    public Table(@JsonProperty("tableNumber") int tableNumber,
                 @JsonProperty("translateX") double translateX,
                 @JsonProperty("translateY") double translateY) {
        this.tableNumber = tableNumber;
        this.translateX = translateX;
        this.translateY = translateY;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public double getTranslateX() {
        return translateX;
    }

    public void setTranslateX(double translateX) {
        this.translateX = translateX;
    }

    public double getTranslateY() {
        return translateY;
    }

    public void setTranslateY(double translateY) {
        this.translateY = translateY;
    }
}
