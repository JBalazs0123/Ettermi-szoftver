package com.example.vendeglatas.modules;

public class Include {
    private int orderId;
    private int productId;
    private int amount;

    public Include(int orderId, int productId, int amount) {
        this.orderId = orderId;
        this.productId = productId;
        this.amount = amount;
    }

    public Include(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Include{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", amount=" + amount +
                '}';
    }
}
