package com.example.vendeglatas.modules;

public class Table {
    private int tableNumber;
    private int numberOfGuests;

    public Table(int tableNumber, int guestsNumber) {
        this.tableNumber = tableNumber;
        this.numberOfGuests = guestsNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}
