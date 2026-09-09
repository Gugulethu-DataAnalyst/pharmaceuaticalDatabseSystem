package Panels;

import dao.reportsDao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class reportPanel extends JPanel {

    // ESTABLISHING THE REPORT DAO OBJECT
    private reportsDao reportDAO;

    // CREATING TABLE MODELS
    private DefaultTableModel salesTableModel;
    private DefaultTableModel itemWiseTableModel;
    private DefaultTableModel lowStockTableModel;
    private DefaultTableModel expiryTableModel;

    public reportPanel() {

        // CREATING REPORT DAO OBJECT
        reportDAO = new reportsDao();


        // MAIN LAYOUT
        setLayout( new BorderLayout(10, 10));


        /*=====================================
        CREATING THE HEADER
        =====================================*/
        JLabel titleLabel = new JLabel(
                "HealthFirst Pharmacy Reports",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial",Font.BOLD, 22));

        add(
                titleLabel,
                BorderLayout.NORTH
        );


        /*=====================================
        CREATING THE REPORT TABS
        =====================================*/
        JTabbedPane reportTabs =
                new JTabbedPane();

        //CREATING THE TABLE
        reportTabs.addTab("Sales Report",createSalesReportPanel());
        reportTabs.addTab("Item-Wise Report", createItemWiseReportPanel());

        //LOW STOCK REPORT TAB
        reportTabs.addTab("Low Stock Report", createLowStockReportPanel());

        // EXPIRY REPORT TAB
        reportTabs.addTab( "Expiry Report",createExpiryReportPanel() );


        // ADD TABS TO MAIN PANEL
        add(
                reportTabs,
                BorderLayout.CENTER
        );


        // LOAD ALL REPORTS
        loadSalesReport();
        loadItemWiseReport();
        loadLowStockReport();
        loadExpiryReport();
    }


    /*=====================================================
    SALES REPORT PANEL
    =====================================================*/
    private JPanel createSalesReportPanel() {
        JPanel panel = new JPanel( new BorderLayout(10, 10));


        String[] columnNames = {
                "Sale ID",
                "Sale Date",
                "Cashier",
                "Total Amount"
        };


        salesTableModel = new DefaultTableModel(columnNames, 0);
        JTable salesTable = new JTable( salesTableModel );


        JScrollPane scrollPane = new JScrollPane( salesTable );
        panel.add( scrollPane, BorderLayout.CENTER );

        JButton refreshButton = new JButton("Refresh Sales Report");
        refreshButton.addActionListener( e -> loadSalesReport());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);

        panel.add( buttonPanel,BorderLayout.SOUTH);

        return panel;
    }


    /*=====================================================
    ITEM-WISE REPORT PANEL
    =====================================================*/
    private JPanel createItemWiseReportPanel() {
        JPanel panel = new JPanel( new BorderLayout(10, 10));

        String[] columnNames = {
                "Medicine",
                "Quantity Sold",
                "Total Revenue"
        };


        itemWiseTableModel = new DefaultTableModel( columnNames, 0);
        JTable itemWiseTable =new JTable(itemWiseTableModel);


        JScrollPane scrollPane = new JScrollPane( itemWiseTable);
        panel.add(scrollPane, BorderLayout.CENTER);


        JButton refreshButton = new JButton( "Refresh Item-Wise Report");
        refreshButton.addActionListener(e -> loadItemWiseReport() );


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);


        panel.add( buttonPanel, BorderLayout.SOUTH);

        return panel;
    }


    /*=====================================================
    LOW STOCK REPORT PANEL
    =====================================================*/
    private JPanel createLowStockReportPanel() {
        JPanel panel = new JPanel( new BorderLayout(10, 10));


        String[] columnNames = {
                "Medicine ID",
                "Medicine Name",
                "Company",
                "Quantity in Stock",
                "Reorder Level"
        };


        lowStockTableModel = new DefaultTableModel(columnNames, 0);
        JTable lowStockTable = new JTable( lowStockTableModel);


        JScrollPane scrollPane = new JScrollPane(lowStockTable);
        panel.add(scrollPane, BorderLayout.CENTER);


        JButton refreshButton = new JButton( "Refresh Low Stock Report" );
        refreshButton.addActionListener( e -> loadLowStockReport());


        JPanel buttonPanel =new JPanel();
        buttonPanel.add( refreshButton );
        panel.add( buttonPanel, BorderLayout.SOUTH);


        return panel;
    }


    /*=====================================================
    EXPIRY REPORT PANEL
    =====================================================*/
    private JPanel createExpiryReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));


        String[] columnNames = {
                "Medicine ID",
                "Medicine Name",
                "Company",
                "Quantity in Stock",
                "Expiry Date"
        };


        expiryTableModel =new DefaultTableModel( columnNames,0);
        JTable expiryTable = new JTable( expiryTableModel  );


        JScrollPane scrollPane = new JScrollPane(expiryTable);
        panel.add( scrollPane,BorderLayout.CENTER );


        JButton refreshButton =new JButton("Refresh Expiry Report");
        refreshButton.addActionListener( e -> loadExpiryReport());


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        panel.add( buttonPanel, BorderLayout.SOUTH);

        return panel;
    }


    /*=====================================================
    LOAD SALES REPORT
    =====================================================*/

    private void loadSalesReport() {

        // CLEAR EXISTING DATA

        salesTableModel.setRowCount(0);


        // GET DATA FROM DAO

        List<Object[]> reportData = reportDAO.getSalesReport();


        // ADD DATA TO TABLE

        for (Object[] row : reportData) {
            salesTableModel.addRow(
                    row
            );
        }
    }


    /*=====================================================
    LOAD ITEM-WISE REPORT
    =====================================================*/

    private void loadItemWiseReport() {
        itemWiseTableModel.setRowCount(0);

        List<Object[]> reportData = reportDAO.getItemWiseReport();


        for (Object[] row : reportData) {
            itemWiseTableModel.addRow(
                    row
            );
        }
    }


    /*=====================================================
    LOAD LOW STOCK REPORT
    =====================================================*/
    private void loadLowStockReport() {
        lowStockTableModel.setRowCount(0);

        List<Object[]> reportData = reportDAO.getLowStockReport();


        for (Object[] row : reportData) {
            lowStockTableModel.addRow(
                    row
            );
        }
    }


    /*=====================================================
    LOAD EXPIRY REPORT
    =====================================================*/
    private void loadExpiryReport() {
        expiryTableModel.setRowCount(0);
        List<Object[]> reportData = reportDAO.getSalesReport();

        for (Object[] row : reportData) {

            expiryTableModel.addRow(
                    row
            );
        }
    }
}