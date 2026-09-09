package Panels;
import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class billingPanel extends JPanel {

    /*==========================================
    Table component
    ==========================================*/
    private JTable billingTable;
    private DefaultTableModel tableModel;


    /*============================================
    Creating a constructor
    ==============================================*/
    public billingPanel() {
        setLayout(
                new BorderLayout( 10,10)
        );

        /*==========================================
        Creating a header
        ==========================================*/
        JLabel titleLabel = new JLabel("Billing History",SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial",Font.BOLD,22));
        add(
            titleLabel,
            BorderLayout.NORTH
        );


        /*==========================================
        BILLING TABLE
        ==========================================*/

        String[] columnNames = {
                "Sale ID",
                "Date",
                "Cashier",
                "Total Amount"
        };


        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable( int row,int column) {
                return false;
            }
        };


        billingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(billingTable);

        add(
            scrollPane,
            BorderLayout.CENTER
        );

        /*==========================================
        BUTTON PANEL
        ==========================================*/
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Refresh");
        buttonPanel.add(refreshButton);

        add(
            buttonPanel,
            BorderLayout.SOUTH
        );


        /*==========================================
        BUTTON ACTION
        ==========================================*/
        refreshButton.addActionListener(e -> loadBillingHistory());

        // LOAD SALES WHEN PANEL OPENS
        loadBillingHistory();
    }


    /*==========================================
    LOAD BILLING HISTORY
    ==========================================*/
    private void loadBillingHistory() {
        // CLEAR TABLE
        tableModel.setRowCount(0);

        String sql = """
                SELECT
                    s.sale_id,
                    s.sale_data,
                    u.full_name,
                    s.total_amount
                FROM sales s
                JOIN userTable u
                    ON s.userID = u.userID
                ORDER BY
                    s.sale_data DESC
                """;
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
            ) {

            while (
                    resultSet.next()
            ) {
                tableModel.addRow(
                        new Object[] {
                                resultSet.getInt( "sale_id"),
                                resultSet.getTimestamp( "sale_data"),
                                resultSet.getString("full_name"),
                                "R" + String.format( "%.2f", resultSet.getDouble("total_amount"))
                        }
                    );
                }
            } catch ( SQLException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error loading billing history: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
