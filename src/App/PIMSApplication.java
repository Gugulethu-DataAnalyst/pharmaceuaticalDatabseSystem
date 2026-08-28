package app;

import Panels.AdminDashboard;
import Panels.LoginPanels;

import javax.swing.*;

public class PIMSApplication {

    private JFrame frame;

    public PIMSApplication() {
        frame = new JFrame("HealthFirst Pharmacy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
   
    }

    public void showLogin() {
        frame.getContentPane().removeAll();
        frame.add(new LoginPanels());
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);

    }

    public void showAdminDashboard() {
        frame.dispose();
        new AdminDashboard();

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            PIMSApplication application =
                    new PIMSApplication();

            application.showLogin();
        });
    }
}