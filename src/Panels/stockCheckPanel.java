package Panels;
//importing the medicine model and dao files
import dao.medicineDao;
import models.medicine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

public class stockCheckPanel extends JPanel{
    /*================================
    DOA object variable must be created,
     which will be used for the function
     ==================================== */
     private medicineDao medicineDao;

     //creating the components
     private JTable medicineTable;
     private DefaultTableModel tableModel;
     private JTextField searchField;

     //Creating a constructor
     public stockCheckPanel() {

        // initializing the dao
        medicineDao = new medicineDao();


        // creating the main layout
        setLayout(new BorderLayout(10,10));

        //header creation
        JLabel titleLabel = new JLabel("Medicine Stock Check", SwingConstants.CENTER);

        //creating the title label
        titleLabel.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                22
            )
        );

        add(
            titleLabel,
            BorderLayout.NORTH
        );

        /*====================================
        creating a search panel section
        this will include flow layout, search button and refresh option
        =================================*/

        JPanel searchPanel = new JPanel( new FlowLayout());
        searchPanel.add(new JLabel("Search Medicine:"));
 

        searchField = new JTextField(20 );
        searchPanel.add(searchField);

        //creating the button objects
        JButton searchButton = new JButton("Search");
        JButton refreshButton = new JButton("Refresh");

        //adding the buuttons to the panels
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        
        /*Creating the table*/
         String[] columnNames = {
                "Medicine ID",
                "Medicine Name",
                "Company",
                "Type",
                "Price",
                "Stock",
                "Reorder Level",
                "Expiry Date"
        };

        tableModel = new DefaultTableModel(columnNames,0) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };


        medicineTable =new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(medicineTable);

        /*========================================
        
        Creating the center panel for the window
        
        ======================================*/
        JPanel centerPanel = new JPanel( new BorderLayout());

        centerPanel.add( searchPanel, BorderLayout.NORTH);

        centerPanel.add( scrollPane,BorderLayout.CENTER);

        add(
            centerPanel,
            BorderLayout.CENTER
        );

        /*========================================
        
        Creating the button actions
        
        ======================================*/
        searchButton.addActionListener( e -> searchMedicine());


        refreshButton.addActionListener(
                e -> {
                    searchField.setText("");
                    loadMedicines();
                }
        );

        loadMedicines();

     }


    /*==================================
    
     Creating a loading medicine funtion

    ====================================*/

    private void loadMedicines() {

        //clear the data in table
        tableModel.setRowCount(0);

        //get the data from the database

        List<medicine> medicines = medicineDao.getAllMedicines();

        //add the medicine
            for (medicine medicine : medicines) {
                tableModel.addRow(new Object[] {
                    medicine.getMedicineId(),
                    medicine.getName(),
                    medicine.getCompany(),
                    medicine.getMedicineType(),
                    "R" + String.format("%.2f",medicine.getPrice()),
                    
                    medicine.getQuantityInStock(),
                    medicine.getReorderLevel(),
                    medicine.getExpiryDate()
                }
            );
        }
    }


    /*==========================================
    SEARCH MEDICINE
    ==========================================*/

        private void searchMedicine() {

            String searchText = searchField.getText().trim().toLowerCase();

            //creating an if statement to search if empty
            if (searchText.isEmpty()) {
                loadMedicines();
                return;
            }

            tableModel.setRowCount(0);

            //get all the medicine
            List<medicine> medicines = medicineDao.getAllMedicines();


            //Try to searcg through for the medicine
            for (medicine medicine : medicines) {
                String medicineName = medicine.getName().toLowerCase();
                String company = medicine.getCompany().toLowerCase();

                //matching the searched medicine to the company
                if (medicineName.contains(searchText) || company.contains(searchText)) {
                    tableModel.addRow(new Object[] {
                        medicine.getMedicineId(),
                        medicine.getName(),
                        medicine.getCompany(),
                        medicine.getMedicineType(),
                        "R" + String.format("%.2f", medicine.getPrice()),
                        medicine.getQuantityInStock(),
                        medicine.getReorderLevel(),
                        medicine.getExpiryDate()
                    }
                );
            }
        }
    }

}