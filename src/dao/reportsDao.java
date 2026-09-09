package dao;

//IMPORTING THE DATABASE CONNECTIION FROM THE DATABASE FOLDER
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class reportsDao {

    /*=========================================================
    SALES REPORT
    =========================================================*/

    public List<Object[]> getSalesReport() {
    List<Object[]> reportData = new ArrayList<>();

        String sql = """
                SELECT
                    s.sale_id,
                    s.sale_data,
                    u.full_name,
                    s.total_amount
                FROM sales s
                JOIN userTable u
                    ON s.userID = u.userID
                ORDER BY s.sale_date DESC
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt( "sale_id" ),
                        resultSet.getTimestamp("sale_date" ),
                        resultSet.getString("full_name" ),
                        resultSet.getDouble("total_amount")
                };
                reportData.add(row);
            }

        } catch (SQLException e) {
            System.err.println( "Error generating sales report: " + e.getMessage());
        }
        return reportData;
    }

    /*=========================================================
    ITEM-WISE SALES REPORT
    =========================================================*/
    public List<Object[]> getItemWiseReport() {
        List<Object[]> reportData = new ArrayList<>();
        
        String sql = """
                SELECT
                    m.name AS medicine_name,
                    SUM(si.quantity_sold) AS total_quantity_sold,
                    SUM(si.quantity_sold * si.price_at_sale)
                        AS total_revenue
                FROM sale_items si
                JOIN medicines m
                    ON si.medicine_id =
                    m.medicine_ID
                GROUP BY
                    m.medicine_ID,
                    m.name
                ORDER BY
                    total_quantity_sold DESC
                """;
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getString("medicine_name"),
                    resultSet.getInt("total_quantity_sold" ),
                    resultSet.getDouble( "total_revenue")
                };
                reportData.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Error generating item-wise report: " + e.getMessage());
        }
         return reportData;
    }

    /*=========================================================
    LOW STOCK REPORT
    =========================================================*/
    public List<Object[]> getLowStockReport() {

        List<Object[]> reportData = new ArrayList<>();

        String sql = """
                SELECT
                    medicine_ID,
                    name,
                    company,
                    quantity_in_stock,
                    reorder_level
                FROM medicines
                WHERE quantity_in_stock <= reorder_level
                ORDER BY quantity_in_stock ASC
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("medicine_ID"),
                        resultSet.getString("name" ),
                        resultSet.getString( "company"),
                        resultSet.getInt( "quantity_in_stock"),
                        resultSet.getInt("reorder_level")
                };
                reportData.add(row);
            }

        } catch (SQLException e) {
            System.err.println("Error generating low stock report: " + e.getMessage());
        }
        return reportData;
    }

    /*=========================================================
    EXPIRY REPORT
    MEDICINES EXPIRING WITHIN THE NEXT MONTH
    =========================================================*/

    public List<Object[]> getExpiryReport() {
        List<Object[]> reportData =new ArrayList<>();

        String sql = """
                SELECT
                    medicine_ID,
                    name,
                    company,
                    quantity_in_stock,
                    expiry_date
                FROM medicines
                WHERE expiry_date
                    BETWEEN CURDATE()
                    AND DATE_ADD(
                        CURDATE(),
                        INTERVAL 1 MONTH
                    )
                ORDER BY expiry_date ASC
                """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement =connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt( "medicine_ID"),
                    resultSet.getString( "name"),
                    resultSet.getString( "company"),
                    resultSet.getInt( "quantity_in_stock" ),
                    resultSet.getDate("expiry_date")
                };
                reportData.add(row);
            }
        } catch (SQLException e) {
            System.err.println( "Error generating expiry report: " + e.getMessage() );
        }        
        return reportData;
    }
}
