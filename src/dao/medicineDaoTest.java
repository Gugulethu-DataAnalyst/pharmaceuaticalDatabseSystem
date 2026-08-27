package dao;

import models.medicine;

import java.time.LocalDate;
import java.util.List;

public class medicineDaoTest {

    public static void main(String[] args) {

        // Creating the MedicineDAO object
        medicineDao medicineDao = new medicineDao();


        /* =====================================================
         FIRST TEST: ADDING MEDICINE TO THE TABLE
        =====================================================*/

        System.out.println("=================================");
        System.out.println("TEST 1: ADD MEDICINE");
        System.out.println("=================================");

        medicine newMedicine = new medicine(
                0,
                "Test Medicine",
                "HealthFirst Pharma",
                "Tablet",
                45.00,
                50,
                10,
                LocalDate.of(2028, 12, 31),
                1
        );

        boolean added = medicineDao.addMedicine(newMedicine);

        if (added) {
            System.out.println("Medicine added successfully!");
        } else {
            System.out.println("Failed to add medicine.");
        }


        /*=====================================================
        SECOND TEST: READING ALL MEDICINES
        =====================================================*/

        System.out.println();
        System.out.println("=================================");
        System.out.println("TEST 2: DISPLAY ALL MEDICINES");
        System.out.println("=================================");

        List<medicine> medicines =
                medicineDao.getAllMedicines();

        for (medicine medicine : medicines) {

            System.out.println(
                    "ID: " + medicine.getMedicineId()
                    + " | Name: " + medicine.getName()
                    + " | Company: " + medicine.getCompany()
                    + " | Type: " + medicine.getMedicineType()
                    + " | Price: R" + medicine.getPrice()
                    + " | Stock: " + medicine.getQuantityInStock()
                    + " | Reorder Level: " + medicine.getReorderLevel()
                    + " | Expiry: " + medicine.getExpiryDate()
                    + " | Supplier ID: " + medicine.getSupplierId()
            );
        }


        /*=====================================================
        THIRD TEST: RFINDING THE MEDICINES
        =====================================================*/

        System.out.println();
        System.out.println("=================================");
        System.out.println("TEST 3: FIND MEDICINE BY ID");
        System.out.println("=================================");

        // Change this ID if necessary after checking your database
        int medicineId = 1;

        medicine medicine =
                medicineDao.getMedicineById(medicineId);

        if (medicine != null) {

            System.out.println(
                    "Medicine found: "
                    + medicine.getName()
            );

            System.out.println(
                    "Price: R"
                    + medicine.getPrice()
            );

            System.out.println(
                    "Stock: "
                    + medicine.getQuantityInStock()
            );

        } else {

            System.out.println(
                    "Medicine with ID "
                    + medicineId
                    + " was not found."
            );
        }


        /* =====================================================
        FOURTH TEST: UPDATING THE MEDICINE
        =====================================================*/

        System.out.println();
        System.out.println("=================================");
        System.out.println("TEST 4: UPDATE MEDICINE");
        System.out.println("=================================");

        if (medicine != null) {

            medicine.setPrice(60.00);

            medicine.setQuantityInStock(75);

            medicine.setReorderLevel(15);

            boolean updated =
                    medicineDao.updateMedicine(medicine);

            if (updated) {

                System.out.println(
                        "Medicine updated successfully!"
                );

                System.out.println(
                        "New price: R"
                        + medicine.getPrice()
                );

                System.out.println(
                        "New stock: "
                        + medicine.getQuantityInStock()
                );

            } else {

                System.out.println(
                        "Failed to update medicine."
                );
            }
        }


        // =====================================================
        // FIFTH TEST: DELETING MEDICINE 
        // =====================================================

        System.out.println();
        System.out.println("=================================");
        System.out.println("TEST 5: DELETE MEDICINE");
        System.out.println("=================================");

        if (medicine != null) {

            boolean deleted =
                    medicineDao.deleteMedicine(
                            medicine.getMedicineId()
                    );

            if (deleted) {

                System.out.println(
                        "Medicine deleted successfully!"
                );

            } else {

                System.out.println(
                        "Failed to delete medicine."
                );
            }
        }


        /*=====================================================
        CONFIRMATION OF A TEST COMPLETE
        =====================================================*/

        System.out.println();
        System.out.println("=================================");
        System.out.println("MEDICINE DAO TEST COMPLETE");
        System.out.println("=================================");
    }
}
