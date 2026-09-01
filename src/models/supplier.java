package models;

public class supplier {
    //creating supplier attributes
    private int supplierID;
    private String name;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;


    /*==============================
    CREATING THE CONSTRUTOR
    ================================*/
    public supplier(
        int supplierID,
        String name,
        String contactPerson,
        String phone,
        String email,
        String address
    ){
        this.supplierID = supplierID;
        this.name = name;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    /*===================================
    CREATING GETTER AND SETTERS
    ===================================== */
    //Getters
    public int getSupplierID() {
        return supplierID;
    }

    public String getName() {
        return name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    //setters
    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
