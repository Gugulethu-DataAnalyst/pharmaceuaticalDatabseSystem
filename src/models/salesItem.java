package models;

public class salesItem {

    // CREATING SALE ITEM ATTRIBUTES

    private int saleItemId;

    private int saleId;

    private int medicineId;

    private int quantitySold;

    private double priceAtSale;


    /*=========================================
    CONSTRUCTOR
    =========================================*/

    public salesItem(
            int saleItemId,
            int saleId,
            int medicineId,
            int quantitySold,
            double priceAtSale
    ) {

        this.saleItemId = saleItemId;

        this.saleId = saleId;

        this.medicineId = medicineId;

        this.quantitySold = quantitySold;

        this.priceAtSale = priceAtSale;
    }


    /*=========================================
    GETTERS
    =========================================*/

    public int getSaleItemId() {

        return saleItemId;
    }


    public int getSaleId() {

        return saleId;
    }


    public int getMedicineId() {

        return medicineId;
    }


    public int getQuantitySold() {

        return quantitySold;
    }


    public double getPriceAtSale() {

        return priceAtSale;
    }


    /*=========================================
    SETTERS
    =========================================*/

    public void setSaleItemId(int saleItemId) {

        this.saleItemId = saleItemId;
    }


    public void setSaleId(int saleId) {

        this.saleId = saleId;
    }


    public void setMedicineId(int medicineId) {

        this.medicineId = medicineId;
    }


    public void setQuantitySold(int quantitySold) {

        this.quantitySold = quantitySold;
    }


    public void setPriceAtSale(double priceAtSale) {

        this.priceAtSale = priceAtSale;
    }
}