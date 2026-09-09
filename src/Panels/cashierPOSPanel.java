package Panels;

import dao.medicineDao;
import dao.salesDao;

import models.medicine;
import models.sales;
import models.salesItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class cashierPOSPanel extends JPanel {

    /*==========================================
    DAO objects
    ==========================================*/
    private medicineDao medicineDAO;
    private salesDao saleDAO;


    /* ==========================================
    POS COMPONENTS
    ==========================================*/

    private JTextField medicineIdField;
    private JTextField quantityField;
    private JLabel medicineNameLabel;
    private JLabel priceLabel;
    private JLabel stockLabel;
    private JLabel totalLabel;

    /*==========================================
    Cart table
    ==========================================*/
    private DefaultTableModel cartTableModel;
    private JTable cartTable;

    //cart data
    private List<salesItem> cartItems;
    private List<medicine> cartMedicines;

    //current medicine object
    private medicine selectedMedicine;

    //Current cashier ID
    private int userId;


    /*==========================================
    CONSTRUCTOR
    ==========================================*/
    public cashierPOSPanel(int userId) {
        //CURRENT CASHIER
        this.userId = userId;

        //INITIALISING DAO OBJECTS
        medicineDAO = new medicineDao();
        saleDAO = new salesDao();

        //INITIALIZING CART OBJECTS
        cartItems = new ArrayList<>();
        cartMedicines = new ArrayList<>();

        //MAIN LAYOUT
        setLayout(new BorderLayout(10, 10));


        /*==========================================
        HEADER
        ==========================================*/
        JLabel titleLabel = new JLabel("HealthFirst Pharmacy - Point of Sale",SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial",Font.BOLD,22));
        add(titleLabel,BorderLayout.NORTH);


        /*==========================================
        MAIN CONTENTS
        ==========================================*/
        JPanel mainContentPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainContentPanel.add( createMedicinePanel() );
        mainContentPanel.add( createCartPanel() );
        add( mainContentPanel, BorderLayout.CENTER );


        /*==========================================
        BOTTOM CHECKOUT PANEL
        ==========================================*/

        JPanel checkoutPanel = new JPanel( new FlowLayout( FlowLayout.RIGHT));

        totalLabel = new JLabel( "Total: R0.00");
        totalLabel.setFont(new Font( "Arial",Font.BOLD,18));
        JButton checkoutButton = new JButton( "Checkout");
        checkoutPanel.add(totalLabel);
        checkoutPanel.add(checkoutButton);

        add(
                checkoutPanel,
                BorderLayout.SOUTH
        );


        // CHECKOUT BUTTON
        checkoutButton.addActionListener(e -> checkout());
    }


    /*==========================================
    MEDICINE SEARCH PANEL
    ==========================================*/

    private JPanel createMedicinePanel() {
        JPanel panel = new JPanel(new GridLayout(7,2,10,10 ));


        // MEDICINE ID
        panel.add( new JLabel( "Medicine ID:" ));
        medicineIdField = new JTextField();
        panel.add( medicineIdField);


        // SEARCH BUTTON
        JButton searchButton = new JButton( "Search Medicine");
        panel.add( new JLabel());
        panel.add( searchButton);

        // MEDICINE NAME
        panel.add( new JLabel("Medicine:" ));
        medicineNameLabel = new JLabel( "-");
        panel.add(medicineNameLabel);


        // PRICE
        panel.add(new JLabel("Price:"));
        priceLabel =new JLabel("R0.00");
        panel.add(priceLabel);


        // STOCK
        panel.add( new JLabel(  "Available Stock:"));
        stockLabel =new JLabel("0");
        panel.add(stockLabel);


        // QUANTITY
        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField );


        // ADD TO CART
        JButton addToCartButton = new JButton("Add To Cart");
        panel.add(new JLabel());
        panel.add( addToCartButton);


        // BUTTON ACTIONS
        searchButton.addActionListener(  e -> searchMedicine());


        addToCartButton.addActionListener( e -> addToCart());

        return panel;
    }


    /*==========================================
    CART PANEL
    ==========================================*/

    private JPanel createCartPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );


        String[] columnNames = {
                "Medicine ID",
                "Medicine",
                "Price",
                "Quantity",
                "Subtotal"
        };


        cartTableModel = new DefaultTableModel( columnNames, 0);
        cartTable = new JTable(cartTableModel );
        JScrollPane scrollPane = new JScrollPane( cartTable);
        panel.add( scrollPane, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        JButton removeButton = new JButton( "Remove Selected");
        JButton clearButton = new JButton( "Clear Cart");
        buttonPanel.add(removeButton);
        buttonPanel.add( clearButton);
        panel.add( buttonPanel,BorderLayout.SOUTH);


        // BUTTON ACTIONS
        removeButton.addActionListener(e -> removeSelectedItem());


        clearButton.addActionListener( e -> clearCart());
        return panel;
    }


    /*==========================================
    SEARCH MEDICINE
    ==========================================*/

    private void searchMedicine() {

        try {
                int medicineId = Integer.parseInt(  medicineIdField.getText().trim() );
                
                selectedMedicine = medicineDAO.getMedicineById(medicineId);
                
                
                if ( selectedMedicine != null) {
                        
                        medicineNameLabel.setText(selectedMedicine.getName());
                        
                        priceLabel.setText("R" + String.format("%.2f",selectedMedicine.getPrice()));

                        stockLabel.setText(String.valueOf(selectedMedicine.getQuantityInStock() ));
        
                } else {
                        JOptionPane.showMessageDialog(  this,  "Medicine not found.",  "Search Error", JOptionPane.ERROR_MESSAGE);

                clearMedicineDetails();
            }
        
        } catch (NumberFormatException e) {
                
                JOptionPane.showMessageDialog(this,"Please enter a valid Medicine ID.", "Input Error",JOptionPane.ERROR_MESSAGE);
        }
    }


    /*==========================================
    ADD TO CART
    ==========================================*/

    private void addToCart() {

        // CHECK IF MEDICINE WAS SEARCHED

        if (selectedMedicine == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please search for a medicine first.",
                    "POS Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
        }try{
                int quantity =Integer.parseInt(quantityField.getText().trim());
                
                //CHECKING QUANTS
                if (quantity <= 0 ) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Quantity must be greater than zero.",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                }
                
                if (quantity > selectedMedicine.getQuantityInStock()) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Insufficient stock available.",
                                "Stock Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                }

                //SALE ITEMS
                salesItem saleItem = new salesItem(
                        0,
                        0,
                        selectedMedicine.getMedicineId(),
                        quantity,
                        selectedMedicine.getPrice()
                );
                
                cartItems.add(saleItem);
                cartMedicines.add(selectedMedicine);
                double subtotal =quantity * selectedMedicine.getPrice();


                //ADD TO TABLE
                cartTableModel.addRow(
                        new Object[] {
                                selectedMedicine.getMedicineId(),
                                selectedMedicine.getName(),
                                selectedMedicine.getPrice(),
                                quantity,
                                subtotal
                        }
                );


            //UPDATE TOTAL
            updateTotal();


            //CLEAR INPUT
            quantityField.setText("");


            JOptionPane.showMessageDialog(
                    this,
                    "Medicine added to cart.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );


        } catch (
                NumberFormatException e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid quantity.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    /*==========================================
    UPDATE TOTAL
    ==========================================*/

    private void updateTotal() {

        double total = 0;

        for (salesItem item: cartItems) {

            total += item.getQuantitySold() * item.getPriceAtSale();
        }


        totalLabel.setText("Total: R" +  String.format("%.2f",total));
    }


    /*==========================================
    REMOVE SELECTED ITEM
    ==========================================*/

    private void removeSelectedItem() {

        int selectedRow = cartTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an item to remove.",
                    "Cart Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // REMOVE FROM CART LIST
        cartItems.remove( selectedRow);
        cartMedicines.remove( selectedRow);

        // REMOVE FROM TABLE
        cartTableModel.removeRow(selectedRow);

        // UPDATE TOTAL
        updateTotal();
    }


    /*==========================================
    CLEAR CART
    ==========================================*/
    private void clearCart() { 
        cartItems.clear();
        cartMedicines.clear();
        cartTableModel.setRowCount(0);
        updateTotal();
    }


    /*==========================================
    CHECKOUT
    ==========================================*/
    private void checkout() {
        if (cartItems.isEmpty() ) {
                JOptionPane.showMessageDialog(
                        this,
                        "The cart is empty.",
                        "Checkout Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
        }


        //CALCULATE TOTAL
        double total = 0;
        for (salesItem item : cartItems ) {
                total += item.getQuantitySold() * item.getPriceAtSale();
        }


        //CREATING THE SALE
        sales sale = new sales(0, LocalDateTime.now(),total, userId);


        //PROCESSING THE SALE
        boolean success =saleDAO.processSale(sale,cartItems);


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Sale completed successfully!\n"
                    + "Total: R"
                    + String.format(
                            "%.2f",
                            total
                    ),
                    "Checkout Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );


            // CLEAR CART

            clearCart();

            clearMedicineDetails();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Sale could not be completed.",
                    "Checkout Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    /*==========================================
    CLEAR MEDICINE DETAILS
    ==========================================*/

    private void clearMedicineDetails() {

        selectedMedicine = null;

        medicineIdField.setText("");

        quantityField.setText("");

        medicineNameLabel.setText("-");

        priceLabel.setText("R0.00");

        stockLabel.setText("0");
    }
}