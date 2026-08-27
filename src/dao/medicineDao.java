package dao;

// Importing database connection
import database.DatabaseConnection;

// Importing Medicine model
import models.medicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class medicineDao {

    /*=========================================================
    CREATING A FUNCTION THAT WILL ADD MEDICINE
    =========================================================*/

    public boolean addMedicine(medicine medicine) {

        String sql = """
                INSERT INTO medicines
                (name, company, medicine_type, price,
                 quantity_in_stock, reorder_level,
                 expiry_date, supplier_id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, medicine.getName());
            statement.setString(2, medicine.getCompany());
            statement.setString(3, medicine.getMedicineType());
            statement.setDouble(4, medicine.getPrice());
            statement.setInt(5, medicine.getQuantityInStock());
            statement.setInt(6, medicine.getReorderLevel());

            statement.setObject(
                    7,
                    medicine.getExpiryDate()
            );

            statement.setInt(8, medicine.getSupplierId());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error adding medicine: " + e.getMessage()
            );

            return false;
        }
    }


    /* =========================================================
    CREATING A FUNCTION THAT WILL READ THE MEDICINE
    =========================================================*/ 

    public List<medicine> getAllMedicines() {

        List<medicine> medicines = new ArrayList<>();

        String sql = """
                SELECT medicine_ID,
                       name,
                       company,
                       medicine_type,
                       price,
                       quantity_in_stock,
                       reorder_level,
                       expiry_date,
                       supplier_id
                FROM medicines
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                medicine medicine = new medicine(

                        resultSet.getInt("medicine_ID"),

                        resultSet.getString("name"),

                        resultSet.getString("company"),

                        resultSet.getString("medicine_type"),

                        resultSet.getDouble("price"),

                        resultSet.getInt("quantity_in_stock"),

                        resultSet.getInt("reorder_level"),

                        resultSet.getObject(
                                "expiry_date",
                                LocalDate.class
                        ),

                        resultSet.getInt("supplier_id")
                );

                medicines.add(medicine);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error retrieving medicines: "
                    + e.getMessage()
            );
        }

        return medicines;
    }


    /*=========================================================
    GETTING MEDICINE FROM THE DATABASE BY THEIR ID
    =========================================================*/

    public medicine getMedicineById(int medicineId) {

        String sql = """
                SELECT medicine_ID,
                       name,
                       company,
                       medicine_type,
                       price,
                       quantity_in_stock,
                       reorder_level,
                       expiry_date,
                       supplier_id
                FROM medicines
                WHERE medicine_ID = ?
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, medicineId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new medicine(

                            resultSet.getInt("medicine_ID"),

                            resultSet.getString("name"),

                            resultSet.getString("company"),

                            resultSet.getString("medicine_type"),

                            resultSet.getDouble("price"),

                            resultSet.getInt("quantity_in_stock"),

                            resultSet.getInt("reorder_level"),

                            resultSet.getObject(
                                    "expiry_date",
                                    LocalDate.class
                            ),

                            resultSet.getInt("supplier_id")
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error finding medicine: "
                    + e.getMessage()
            );
        }

        return null;
    }


    /*=========================================================
    UPDATING THE MEDICINE STORED IN THE DATABASE
    =========================================================*/

    public boolean updateMedicine(medicine medicine) {

        String sql = """
                UPDATE medicines
                SET name = ?,
                    company = ?,
                    medicine_type = ?,
                    price = ?,
                    quantity_in_stock = ?,
                    reorder_level = ?,
                    expiry_date = ?,
                    supplier_id = ?
                WHERE medicine_ID = ?
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, medicine.getName());
            statement.setString(2, medicine.getCompany());
            statement.setString(3, medicine.getMedicineType());
            statement.setDouble(4, medicine.getPrice());
            statement.setInt(5, medicine.getQuantityInStock());
            statement.setInt(6, medicine.getReorderLevel());

            statement.setObject(
                    7,
                    medicine.getExpiryDate()
            );

            statement.setInt(8, medicine.getSupplierId());

            statement.setInt(
                    9,
                    medicine.getMedicineId()
            );

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error updating medicine: "
                    + e.getMessage()
            );

            return false;
        }
    }


    /*=========================================================
    DELETING THE MEDICINE IN THE TABLE
    =========================================================*/

    public boolean deleteMedicine(int medicineId) {

        String sql = """
                DELETE FROM medicines
                WHERE medicine_ID = ?
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, medicineId);

            int rowsDeleted = statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error deleting medicine: "
                    + e.getMessage()
            );

            return false;
        }
    }
}