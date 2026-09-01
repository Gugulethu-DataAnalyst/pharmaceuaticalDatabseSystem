package models;

import java.time.LocalDateTime;

public class sales {

    // CREATING SALE ATTRIBUTES

    private int saleId;

    private LocalDateTime saleDate;

    private double totalAmount;

    private int userId;


    /*=========================================
    CONSTRUCTOR
    =========================================*/

    public sales(
            int saleId,
            LocalDateTime saleDate,
            double totalAmount,
            int userId
    ) {

        this.saleId = saleId;

        this.saleDate = saleDate;

        this.totalAmount = totalAmount;

        this.userId = userId;
    }


    /*=========================================
    GETTERS
    =========================================*/

    public int getSaleId() {

        return saleId;
    }


    public LocalDateTime getSaleDate() {

        return saleDate;
    }


    public double getTotalAmount() {

        return totalAmount;
    }


    public int getUserId() {

        return userId;
    }


    /*=========================================
    SETTERS
    =========================================*/

    public void setSaleId(int saleId) {

        this.saleId = saleId;
    }


    public void setSaleDate(LocalDateTime saleDate) {

        this.saleDate = saleDate;
    }


    public void setTotalAmount(double totalAmount) {

        this.totalAmount = totalAmount;
    }


    public void setUserId(int userId) {

        this.userId = userId;
    }
}