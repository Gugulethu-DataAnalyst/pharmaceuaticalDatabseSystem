package Panels;

//importing the pimsapplication
import app.PIMSApplication;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame{

    public AdminDashboard(){
        setTitle("HealthFirst Pharmacy Admin Dashboard");

        setSize(900, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        //main panel for the dashboard
        JPanel mainPanel = new JPanel(new BorderLayout());
        //header creation
        JLabel titleLabel = new JLabel(
            "HealthFirst Pharmacy administrator", SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        //TASKS CREATION
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab(
            "Manage Medicines", 
            new medicinePanel()
        );

        tabbedPane.addTab(
            "Manage Suppliers",
            new JPanel()
        );

        tabbedPane.addTab(
            "Manage Users",
            new JPanel()
        );

        tabbedPane.addTab(
            "Reports",
            new JPanel()
        );

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        //logout button for the panel
        JButton logoutButton = new JButton("Logout");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        bottomPanel.add(logoutButton);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);

        //main panel
        logoutButton.addActionListener(e -> {
            
            dispose();

            new PIMSApplication().showLogin();

        });
        add(mainPanel);

        setVisible(true);


    }
    
}
