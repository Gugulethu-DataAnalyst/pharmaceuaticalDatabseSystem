package models;

import java.time.LocalDate;

public class medicine{
    //creating private variables
    private int medicineID;
    private String name;
    private String company;
    private String medicineType;
    private double price;
    private int quantityInStock;
    private int reorderLevel;
    private LocalDate expiryDate;
    private int supplierID;

    //Creating default constructor
    public medicine(){

    }
    //constructor that will hot variables
    public medicine(int medicineID, String name, String company, 
            String medicineType, double price, int quantityInStock,
            int reorderLevel, LocalDate expiryDate, int supplierID){

                this.medicineID = medicineID;
                this.name = name;
                this.company = company;
                this.medicineType = medicineType;
                this.price = price;
                this.quantityInStock = quantityInStock;
                this.reorderLevel = reorderLevel;
                this.expiryDate = expiryDate;
                this.supplierID = supplierID;
            }

            /*=======================
            Creating the getter and setter section
            GETTER AND SETTER FOR MEDICINE ID
            ==========================*/
             public int getMedicineId() {
                return medicineID;
            }
            
            public void setMedicineId(int medicineID) {
                this.medicineID = medicineID;
            }
            
            /*============================
            GETTER AND SETTER FOR NAME
            ==============================*/
            public String getName() {
                return name;
            }
            
            public void setName(String name) {
                this.name = name;
            }

            /*============================
            GETTER AND SETTER FOR COMPANY
            ==============================*/
            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            /*============================
            GETTER AND SETTER MEDICIME TYPE
            ==============================*/
            public String getMedicineType() {
                return medicineType;
            }

            public void setMedicineType(String medicineType) {
                this.medicineType = medicineType;
            }

            /*============================
            GETTER AND SETTER FOR PRICE
            ==============================*/
            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            /*============================
            GETTER AND SETTER FOR THE QUAMTITY
            ==============================*/
            public int getQuantityInStock() {
                return quantityInStock;
            }

            public void setQuantityInStock(int quantityInStock) {
                this.quantityInStock = quantityInStock;
            }

            /*============================
            GETTER AND SETTER FOR THE REORDER
            ==============================*/
            public int getReorderLevel() {
                return reorderLevel;
            }

            public void setReorderLevel(int reorderLevel) {
                this.reorderLevel = reorderLevel;
            }

            /*============================
            GETTER AND SETTER EXPIRY DATE
            ==============================*/
            public LocalDate getExpiryDate() {
                return expiryDate;
            }

            public void setExpiryDate(LocalDate expiryDate) {
                this.expiryDate = expiryDate;
            }

            /*============================
            GETTER AND SETTER SUPPLIER ID
            ==============================*/
            public int getSupplierId() {
                return supplierID;
            }

            public void setSupplierId(int supplierID) {
                this.supplierID = supplierID;
            }


}