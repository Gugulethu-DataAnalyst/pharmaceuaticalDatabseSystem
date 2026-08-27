package Panels;

import dao.medicineDao;
import models.medicine;

import javax.swing.*;
import java.awt.*;

public class medicinePanel extends JPanel{
    
    private medicineDao medicineDAO;

    public medicinePanel(){
        medicineDAO = new medicineDao();

        setLayout(new BorderLayout());

        /*================================
        CREATING THE HEADER 
        ====================================*/
        JLabel titleLabel = new JLabel("Manage Medicine",SwingConstants.CENTER);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        add(titleLabel, BorderLayout.NORTH);

        /*=====================================
        Creating the content panel
        ========================================*/
        JPanel contentPanel = new JPanel();

        contentPanel.add(
            new JLabel("Medicine Management")
        );
        add(contentPanel, BorderLayout.CENTER);

    }
}
