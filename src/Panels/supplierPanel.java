package Panels;

//creating the connection between the panel, model and doa
import dao.supplierDao;
import models.supplier;

//GUI initialization
import javax.swing.*;
import java.awt.*;

//importing swing to show table of the suppliers
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import java.util.List;


public class supplierPanel extends JPanel {

    /*========================================
    ESTABLISHING THE DATABASE OBJECT
    ==========================================*/
    private supplierDao supplierDAO;


    // CREATING THE FORM COMPONENTS
    private JTextField nameField;
    private JTextField contactPersonField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextArea addressField;
    //added variables for the table
    private JTable supplierTable;
    private DefaultTableModel tableModel;


    public supplierPanel() {

        // CREATING THE DAO OBJECT
        supplierDAO = new supplierDao();


        // CREATING THE MAIN LAYOUT
        setLayout(new BorderLayout(10, 10));


        /*================================
        CREATING THE HEADER
        ====================================*/
        JLabel titleLabel = new JLabel("Manage Suppliers", SwingConstants.CENTER);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        add(titleLabel, BorderLayout.NORTH);


        /*=====================================
        CREATING THE FORM PANEL
        =======================================*/

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 7));


        // SUPPLIER NAME
        formPanel.add(new JLabel("Supplier Name:"));

        nameField = new JTextField();

        formPanel.add(nameField);


        // CONTACT PERSON
        formPanel.add(new JLabel("Contact Person:"));

        contactPersonField = new JTextField();

        formPanel.add(contactPersonField);


        // PHONE
        formPanel.add(new JLabel("Phone:"));

        phoneField = new JTextField();

        formPanel.add(phoneField);


        // EMAIL
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);


        // ADDRESS
        formPanel.add(new JLabel("Address:"));

        addressField = new JTextArea(3, 20);

        formPanel.add(new JScrollPane(addressField));


        // ADD FORM PANEL
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));

        contentPanel.add(formPanel,BorderLayout.NORTH);

        /*===========================================
        CREATING THE  SUPPLIER TABLE 
        =============================================*/
        String[] columnNames = {
                "ID",
                "Name",
                "Contact Person",
                "Phone",
                "Email",
                "Address"
        };

        tableModel = new DefaultTableModel(columnNames,0);

        supplierTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(supplierTable);

        contentPanel.add(tableScrollPane,BorderLayout.CENTER);
        
        
        // ADDING CONTENT PANEL TO THE MAIN PANEL
        add(
                contentPanel,
                BorderLayout.CENTER
        );
        
        /*======================================
        BUTTON PANEL
        ======================================*/

        JPanel buttonPanel = new JPanel(
                new FlowLayout()
        );


        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");


        //ADDING BUTTONS

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);


        //ADD BUTTON PANEL
        add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        /*======================================
        BUTTON ACTIONS
        ======================================*/

        addButton.addActionListener(e -> addSupplier());
        clearButton.addActionListener(e -> clearForm());

        //LOADING THE SUPPLIER FROM THE DATABASE
        loadSuppliers();
}


    /*==========================================
     ADD SUPPLIER
    ==========================================*/
    private void addSupplier() {

        try {
            String name = nameField.getText().trim();
            String contactPerson = contactPersonField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();


            // CHECK REQUIRED FIELD
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the supplier name.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }


            /*======================================
            CREATING SUPPLIER OBJECT
            ======================================*/

            supplier supplier =
                    new supplier(
                            0,
                            name,
                            contactPerson,
                            phone,
                            email,
                            address
                    );


            /*======================================
            SEND SUPPLIER TO DAO
            ======================================*/

            boolean success = supplierDAO.addSupplier(supplier);


            //CHECK RESULT
            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Supplier added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearForm();
                //REFRESHING THE SUPPLIER TABLE
                loadSuppliers();

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add supplier.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error adding supplier: "
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /*==================================
    LOADING THE SUPPLIER INTO THE TABLE
    ======================================*/

    private void loadSuppliers() {

    //CLEARING EXISTING TABLE DATA
    tableModel.setRowCount(0);


    //GETTING SUPPLIERS FROM THE DATABASE
    List<supplier> suppliers = supplierDAO.getAllSuppliers();

    //ADD SUPPLIERS TO TABLE
    for (supplier supplier : suppliers) {

        tableModel.addRow(
                new Object[]{
                        supplier.getSupplierID(),
                        supplier.getName(),
                        supplier.getContactPerson(),
                        supplier.getPhone(),
                        supplier.getEmail(),
                        supplier.getAddress()
                }
        );
    }
}
        


    /*==========================================
    CLEAR FORM
    ==========================================*/

    private void clearForm() {

        nameField.setText("");
        contactPersonField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
    }
}