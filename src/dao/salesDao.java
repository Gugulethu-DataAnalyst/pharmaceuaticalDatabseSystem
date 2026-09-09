package dao;

import database.DatabaseConnection;

import models.sales;
import models.salesItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class salesDao {
    /*=========================================================
    PROCESSING A COMPLETE SALE
    =========================================================*/
    public boolean processSale(
            sales sale,
            List<salesItem> saleItems
    ) {

        Connection connection = null;

        try {
            // GET DATABASE CONNECTION
            connection =
                    DatabaseConnection.getConnection();
            // START DATABASE TRANSACTION
            connection.setAutoCommit(false);
            
            /*=================================================
            CREATING SALE
            =================================================*/
            String saleSql = """
                    INSERT INTO sales
                    (total_amount, userID)
                    VALUES (?, ?)
                    """;
            int saleId;

            try (
                PreparedStatement statement =
                        connection.prepareStatement(
                                saleSql,
                                PreparedStatement.RETURN_GENERATED_KEYS
                        )
            ) {

                statement.setDouble(
                        1,
                        sale.getTotalAmount()
                );

                statement.setInt(
                        2,
                        sale.getUserId()
                );


                statement.executeUpdate();

                // GET GENERATED SALE ID
                try (
                    ResultSet resultSet =
                            statement.getGeneratedKeys()
                ) {

                    if (resultSet.next()) { 
                        saleId = resultSet.getInt(1);

                    } else {
                        throw new SQLException( "Failed to create sale." );
                    }
                }
            }


            /*=================================================
            INSERT SALE ITEMS
            =================================================*/
            String saleItemSql = """
                    INSERT INTO sales_Item
                    (sale_id,
                     medicine_id,
                     quantity_sold,
                     price_at_sale)
                    VALUES (?, ?, ?, ?)
                    """;


            /*=================================================
            UPDATE MEDICINE STOCK
            =================================================*/
            String stockSql = """
                    UPDATE medicines
                    SET quantity_in_stock =
                        quantity_in_stock - ?
                    WHERE medicine_ID = ?
                    AND quantity_in_stock >= ?
                    """;


            for (salesItem saleItem : saleItems) {

                /*=============================================
                INSERT SALE ITEM
                =============================================*/
                try (
                    PreparedStatement statement =
                            connection.prepareStatement(saleItemSql )
                ) {

                    statement.setInt( 1, saleId);
                    statement.setInt(2, saleItem.getMedicineId());
                    statement.setInt(3,saleItem.getQuantitySold());
                    statement.setDouble(4,  saleItem.getPriceAtSale());

                    statement.executeUpdate();
                }
 

                /*=============================================
                UPDATE MEDICINE STOCK
                =============================================*/
                try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    stockSql
                            )
                ) {

                    statement.setInt(
                            1,
                            saleItem.getQuantitySold()
                    );

                    statement.setInt(
                            2,
                            saleItem.getMedicineId()
                    );

                    statement.setInt(
                            3,
                            saleItem.getQuantitySold()
                    );


                    int rowsUpdated =
                            statement.executeUpdate();


                    // CHECK IF ENOUGH STOCK EXISTS

                    if (rowsUpdated == 0) {

                        throw new SQLException(
                                "Insufficient stock for medicine ID: "
                                + saleItem.getMedicineId()
                        );
                    }
                }
            }


            /*=================================================
            STEP 4: COMMIT TRANSACTION
            =================================================*/
            connection.commit();

            System.out.println(
                    "Sale processed successfully!"
            );

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "Error processing sale: "
                    + e.getMessage()
            );


            try {

                if (connection != null) {

                    connection.rollback();

                    System.out.println(
                            "Transaction rolled back."
                    );
                }

            } catch (SQLException rollbackException) {

                System.err.println(
                        "Rollback failed: "
                        + rollbackException.getMessage()
                );
            }


            return false;


        } finally {

            try {

                if (connection != null) {

                    connection.setAutoCommit(true);

                    connection.close();
                }

            } catch (SQLException e) {

                System.err.println(
                        "Error closing connection: "
                        + e.getMessage()
                );
            }
        }
    }
}