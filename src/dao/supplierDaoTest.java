package dao;

//importing the supplier model
import models.supplier;
import java.util.List;

public class supplierDaoTest {
    //creating a main window to run the program

    public static void main(String[] args) {
        // Creating the SupplierDAO object
        supplierDao supplierDAO = new supplierDao();

        /*=====================================================
        TEST 1: ADD SUPPLIER
        =====================================================*/

        System.out.println("=================================");
        System.out.println("TEST 1: ADD SUPPLIER");
        System.out.println("=================================");

        supplier newSupplier = new supplier(
                0,
                "Test Pharmaceuticals",
                "John Smith",
                "0712345678",
                "john@testpharma.co.za",
                "25 Test Street, Johannesburg"
        );

        boolean added = supplierDAO.addSupplier(newSupplier);

        if (added) {
            System.out.println("Supplier added successfully!");

        } else {
            System.out.println("Failed to add supplier.");
        }


        /*=====================================================
        TEST 2: READ ALL SUPPLIERS
        =====================================================*/

        System.out.println();
        System.out.println("=================================");
        System.out.println("TEST 2: DISPLAY ALL SUPPLIERS");
        System.out.println("=================================");

        List<supplier> suppliers = supplierDAO.getAllSuppliers();

        for (supplier supplier : suppliers) {

            System.out.println(
                    "ID: " + supplier.getSupplierID()
                    + " | Name: " + supplier.getName()
                    + " | Contact Person: "
                    + supplier.getContactPerson()
                    + " | Phone: " + supplier.getPhone()
                    + " | Email: " + supplier.getEmail()
                    + " | Address: " + supplier.getAddress()
            );
        }


        // =====================================================
        // TEST 3: FIND SUPPLIER BY ID
        // =====================================================

        System.out.println();
        System.out.println("=================================");
        System.out.println("TEST 3: FIND SUPPLIER BY ID");
        System.out.println("=================================");

        /*
          We use the ID of the supplier we just added.
          Because supplier_ID is AUTO_INCREMENT, we need to find the latest supplier ID.
         */

        if (!suppliers.isEmpty()) {

            supplier lastSupplier = suppliers.get(suppliers.size() - 1);

            int supplierId = lastSupplier.getSupplierID();

            supplier foundSupplier = supplierDAO.getSupplierById(supplierId);

            if (foundSupplier != null) {

                System.out.println( "Supplier found: " + foundSupplier.getName());

                System.out.println( "Contact Person: " + foundSupplier.getContactPerson());

                System.out.println( "Phone: " + foundSupplier.getPhone());

                System.out.println( "Email: " + foundSupplier.getEmail());

            } else {

                System.out.println( "Supplier was not found.");
            }


            /*=================================================
            TEST 4: UPDATE SUPPLIER
            =================================================*/

            System.out.println();
            System.out.println("=================================");
            System.out.println("TEST 4: UPDATE SUPPLIER");
            System.out.println("=================================");

            foundSupplier.setPhone("0798765432");

            foundSupplier.setEmail( "updated@testpharma.co.za" );

            boolean updated = supplierDAO.updateSupplier( foundSupplier);

            if (updated) {

                System.out.println( "Supplier updated successfully!");

                System.out.println( "New phone: " + foundSupplier.getPhone());

                System.out.println( "New email: "  + foundSupplier.getEmail());

            } else {

                System.out.println("Failed to update supplier.");
            }

            /*=================================================
            TEST 5: DELETE SUPPLIER
            =================================================*/

            System.out.println();
            System.out.println("=================================");
            System.out.println("TEST 5: DELETE SUPPLIER");
            System.out.println("=================================");

            boolean deleted =
                    supplierDAO.deleteSupplier(
                            foundSupplier.getSupplierID()
                    );

            if (deleted) {

                System.out.println(
                        "Supplier deleted successfully!"
                );

            } else {

                System.out.println(
                        "Failed to delete supplier."
                );
            }

        } else {

            System.out.println(
                    "No suppliers were found."
            );
        }


        // =====================================================
        // TEST COMPLETE
        // =====================================================

        System.out.println();
        System.out.println("=================================");
        System.out.println("SUPPLIER DAO TEST COMPLETE");
        System.out.println("=================================");
    }
}