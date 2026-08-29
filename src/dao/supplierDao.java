package dao;

// Importing database connection
import database.DatabaseConnection;

// Importing Supplier model
import models.supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class supplierDao {

    /*=========================================================
    CREATING A FUNCTION THAT WILL ADD SUPPLIER
    =========================================================*/

    public boolean addSupplier(supplier supplier) {

        String sql = """
                INSERT INTO suppliers
                (name, contact_person, phone, email, address)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            // Supplier name
            statement.setString(
                    1,
                    supplier.getName()
            );

            // Contact person
            statement.setString(
                    2,
                    supplier.getContactPerson()
            );

            // Phone
            statement.setString(
                    3,
                    supplier.getPhone()
            );

            // Email
            statement.setString(
                    4,
                    supplier.getEmail()
            );

            // Address
            statement.setString(
                    5,
                    supplier.getAddress()
            );

            int rowsInserted =
                    statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error adding supplier: "
                    + e.getMessage()
            );

            return false;
        }
    }


    /*=========================================================
    CREATING A FUNCTION THAT WILL READ ALL SUPPLIERS
    =========================================================*/

    public List<supplier> getAllSuppliers() {

        List<supplier> suppliers =
                new ArrayList<>();

        String sql = """
                SELECT supplier_ID,
                       name,
                       contact_person,
                       phone,
                       email,
                       address
                FROM suppliers
                """;

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet resultSet =
                    statement.executeQuery()
        ) {

            while (resultSet.next()) {

                supplier supplier =
                        new supplier(

                            resultSet.getInt(
                                    "supplier_ID"
                            ),

                            resultSet.getString(
                                    "name"
                            ),

                            resultSet.getString(
                                    "contact_person"
                            ),

                            resultSet.getString(
                                    "phone"
                            ),

                            resultSet.getString(
                                    "email"
                            ),

                            resultSet.getString(
                                    "address"
                            )
                        );

                suppliers.add(supplier);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error retrieving suppliers: "
                    + e.getMessage()
            );
        }

        return suppliers;
    }


    /*=========================================================
    GETTING SUPPLIER FROM THE DATABASE BY THEIR ID
    =========================================================*/

    public supplier getSupplierById(int supplierId) {

        String sql = """
                SELECT supplier_ID,
                       name,
                       contact_person,
                       phone,
                       email,
                       address
                FROM suppliers
                WHERE supplier_ID = ?
                """;

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    supplierId
            );

            try (
                ResultSet resultSet =
                        statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return new supplier(

                            resultSet.getInt(
                                    "supplier_ID"
                            ),

                            resultSet.getString(
                                    "name"
                            ),

                            resultSet.getString(
                                    "contact_person"
                            ),

                            resultSet.getString(
                                    "phone"
                            ),

                            resultSet.getString(
                                    "email"
                            ),

                            resultSet.getString(
                                    "address"
                            )
                    );
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error finding supplier: "
                    + e.getMessage()
            );
        }

        return null;
    }


    /*=========================================================
    UPDATING THE SUPPLIER STORED IN THE DATABASE
    =========================================================*/

    public boolean updateSupplier(supplier supplier) {

        String sql = """
                UPDATE suppliers
                SET name = ?,
                    contact_person = ?,
                    phone = ?,
                    email = ?,
                    address = ?
                WHERE supplier_ID = ?
                """;

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    supplier.getName()
            );

            statement.setString(
                    2,
                    supplier.getContactPerson()
            );

            statement.setString(
                    3,
                    supplier.getPhone()
            );

            statement.setString(
                    4,
                    supplier.getEmail()
            );

            statement.setString(
                    5,
                    supplier.getAddress()
            );

            statement.setInt(
                    6,
                    supplier.getSupplierID()
            );

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error updating supplier: "
                    + e.getMessage()
            );

            return false;
        }
    }


    /*=========================================================
    DELETING THE SUPPLIER IN THE TABLE
    =========================================================*/

    public boolean deleteSupplier(int supplierId) {

        String sql = """
                DELETE FROM suppliers
                WHERE supplier_ID = ?
                """;

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    supplierId
            );

            int rowsDeleted =
                    statement.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error deleting supplier: "
                    + e.getMessage()
            );

            return false;
        }
    }
}