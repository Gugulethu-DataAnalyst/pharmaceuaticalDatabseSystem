package Panels;

//importing the pimsapplication
import app.PIMSApplication;

import javax.swing.*;
import java.awt.*;

public class cashierDashboard extends JFrame{
    public cashierDashboard(){
        setTitle("HealthFirst Pharmacy - Cashier");

        setSize(900, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        
        /*======================
        Creating headers for the panel
        ========================*/
        JLabel titleLabel = new JLabel(
                "HealthFirst Pharmacy - Cashier",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        /*======================
        Creating tabs
        ========================*/
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab(
                "Point of Sale",
                new JPanel()
        );

        tabbedPane.addTab(
                "Stock Check",
                new JPanel()
        );

        tabbedPane.addTab(
                "Billing",
                new JPanel()
        );

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        /*======================
        Creating Logout button
        ========================*/
        JButton logoutButton = new JButton("Logout");

        JPanel bottomPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        bottomPanel.add(logoutButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        /*======================
        Creating Logout button actions
        ========================*/
        logoutButton.addActionListener(e -> {

            dispose();

            new PIMSApplication().showLogin();

        });

        add(mainPanel);

        setVisible(true);
    }
}
