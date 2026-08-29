package Panels;

// Connecting the panel to the DAO and model
import dao.userDao;
import models.User;

// GUI
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class userPanel extends JPanel {

    /*=========================================================
    ESTABLISHING THE DATABASE OBJECT
    =========================================================*/
    private userDao userDAO;


    /*=========================================================
    CREATING THE FORM COMPONENTS
    =========================================================*/
    private JTextField usernameField;
    private JTextField fullNameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;


    /*=========================================================
    CREATING THE TABLE COMPONENTS
    =========================================================*/
    private JTable userJTable;
    private DefaultTableModel tableModel;


    /*=========================================================
    CONSTRUCTOR
    =========================================================*/
    public userPanel() {
        // Creating DAO object
        userDAO = new userDao();

        /*=====================================================
        MAIN LAYOUT
        =====================================================*/
        setLayout(new BorderLayout( 10,10));

        /*=====================================================
        HEADER
        =====================================================*/
        JLabel titleLabel = new JLabel("Manage Users",SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial",Font.BOLD,22));

        add(
                titleLabel,
                BorderLayout.NORTH
        );


        /*=====================================================
        CENTER PANEL
        =====================================================*/
        JPanel centerPanel =new JPanel(new BorderLayout(10,10));

        /*=====================================================
        FORM PANEL
        =====================================================*/
        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                10,
                                7
                        )
                );


        // USERNAME
        formPanel.add(new JLabel("Username:"));
        usernameField =new JTextField();
        formPanel.add(usernameField);


        // PASSWORD
        formPanel.add(new JLabel("Password:"));
        passwordField =new JPasswordField();
        formPanel.add(passwordField);


        // ROLE
        formPanel.add(new JLabel("Role:"));
        roleComboBox =
                new JComboBox<>(
                        new String[]{
                                "Admin",
                                "Pharmacist",
                                "Cashier"
                        }
                );

        formPanel.add(roleComboBox);


        // FULL NAME
        formPanel.add(new JLabel("Full Name:"));
        fullNameField = new JTextField();
        formPanel.add(fullNameField);


        /*=====================================================
        ADD FORM TO CENTER PANEL
        =====================================================*/

        centerPanel.add(formPanel, BorderLayout.NORTH);

        /*=====================================================
        CREATING USER TABLE
        =====================================================*/
        String[] columns = {
                "User ID",
                "Username",
                "Role",
                "Full Name"
        };


        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row,int column) {
                return false;
            }
        };


        userJTable = new JTable( tableModel);


        // Allow only one user to be selected
        userJTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION);


        // Add table to scroll pane
        JScrollPane tableScrollPane = new JScrollPane( userJTable );


        /*=====================================================
        ADD TABLE TO CENTER PANEL
        =====================================================*/
        centerPanel.add(
                tableScrollPane,
                BorderLayout.CENTER
        );


        /*=====================================================
        ADD CENTER PANEL TO MAIN PANEL
        =====================================================*/
        add(
                centerPanel,
                BorderLayout.CENTER
        );


        /*=====================================================
        BUTTON PANEL
        =====================================================*/
        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout()
                );


        JButton addButton =
                new JButton("Add");

        JButton deleteButton =
                new JButton("Delete");

        JButton clearButton =
                new JButton("Clear");


        buttonPanel.add(
                addButton
        );

        buttonPanel.add(
                deleteButton
        );

        buttonPanel.add(
                clearButton
        );


        add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        /*=====================================================
        BUTTON ACTIONS
        =====================================================*/

        addButton.addActionListener(e -> addUser());
        deleteButton.addActionListener( e -> deleteUser());
        clearButton.addActionListener( e -> clearForm());

        /*=====================================================
        TABLE ACTION
        =====================================================*/
        userJTable.getSelectionModel().addListSelectionListener( e -> loadSelectedUser());


        /*=====================================================
        LOAD USERS FROM DATABASE
        =====================================================*/
        loadUsers();
    }


    /*=========================================================
    ADD USER
    =========================================================*/
    private void addUser() {
        try {
            String username = usernameField.getText().trim();
            String password =new String(passwordField.getPassword());
            String role = roleComboBox.getSelectedItem().toString();
            String fullName = fullNameField.getText().trim();


            /*=================================================
            CHECK REQUIRED FIELDS
            =================================================*/

            if (
                    username.isEmpty()
                    || password.isEmpty()
                    || fullName.isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete all required fields.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }


            /*=================================================
            CREATE USER OBJECT
            =================================================*/

            User user =
                    new User(
                            0,
                            username,
                            password,
                            role,
                            fullName
                    );


            /*=================================================
            SEND USER TO DAO
            =================================================*/

            boolean success = userDAO.addUser(user);


            /*=================================================
            CHECK RESULT
            =================================================*/

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "User added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );


                clearForm();
                // Refresh table
                loadUsers();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add user.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error adding user: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    /*=========================================================
    GET ALL USERS AND DISPLAY THEM IN TABLE
    =========================================================*/

    private void loadUsers() {

        // Remove existing rows
        tableModel.setRowCount(0);

        // Get users from database
        List<User> users = userDAO.getAllUsers();

        /*=====================================================
        ADD EACH USER TO TABLE
        =====================================================*/

        for (User user : users) {

            tableModel.addRow(
                    new Object[]{
                            user.getUserID(),
                            user.getUsername(),
                            user.getRole(),
                            user.getFullName()
                    }
            );
        }
    }


    /*=========================================================
    LOAD SELECTED USER
    =========================================================*/

    private void loadSelectedUser() {

        int selectedRow =
                userJTable.getSelectedRow();


        if (selectedRow == -1) {
            return;
        }


        usernameField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString()
        );


        roleComboBox.setSelectedItem(
                tableModel
                        .getValueAt(
                                selectedRow,
                                2
                        )
                        .toString()
        );


        fullNameField.setText(
                tableModel
                        .getValueAt(
                                selectedRow,
                                3
                        )
                        .toString()
        );


        // We do not load the password
        passwordField.setText("");
    }


    /*=========================================================
    DELETE USER
    =========================================================*/

    private void deleteUser() {

        int selectedRow =
                userJTable.getSelectedRow();


        /*=====================================================
        CHECK WHETHER USER WAS SELECTED
        =====================================================*/

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a user to delete.",
                    "Selection Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        /*=====================================================
        GET USER ID
        =====================================================*/

        int userId =
                Integer.parseInt(
                        tableModel
                                .getValueAt(
                                        selectedRow,
                                        0
                                )
                                .toString()
                );


        /*=====================================================
        CONFIRM DELETE
        =====================================================*/

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this user?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                confirmation
                        != JOptionPane.YES_OPTION
        ) {
            return;
        }


        /*=====================================================
        DELETE FROM DATABASE
        =====================================================*/

        boolean success =
                userDAO.deleteUser(
                        userId
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "User deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );


            clearForm();


            // Refresh table
            loadUsers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete user.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    /*=========================================================
    CLEAR FORM
    =========================================================*/

    private void clearForm() {

        usernameField.setText("");

        passwordField.setText("");

        fullNameField.setText("");

        roleComboBox.setSelectedIndex(0);

        userJTable.clearSelection();
    }
}