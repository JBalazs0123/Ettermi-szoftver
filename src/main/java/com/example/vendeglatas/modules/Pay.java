package com.example.vendeglatas.modules;

public class Pay {
    private int billId;
    private int tableNumber;

    public Pay(int billId, int tableNumber) {
        this.billId = billId;
        this.tableNumber = tableNumber;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
}
