package Panels;


import dao.userDao;
import models.User;

import javax.swing.*;
import java.awt.*;

import Panels.AdminDashboard;
import Panels.cashierDashboard;


//creating the the login panel extended by JPanel
public class LoginPanels extends JPanel{
    //creating private variables for the username, password, and button
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    //creating a private userDao
    private userDao userDao;


    //creating a public login panel function
    public LoginPanels(){

        //create user doa
        userDao = new userDao();

        
        //creating components
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Login");

        //creating the layout for the GUI
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5,5);

        //username labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel,gbc);

        //username Field
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField,gbc);

        
        //password labels
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel,gbc);

        //password Fields
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField,gbc);

        //button
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(loginButton,gbc);

        //LOGIN
        loginButton.addActionListener(e ->login());
    }

    private void login(){
        String username = usernameField.getText();

        String password = new String(
            passwordField.getPassword()
        );

        //if statement that will check the field if they are empty
        if(username.isEmpty() || password.isEmpty()){

            JOptionPane.showMessageDialog(this, 
                "Please enter your username and password.",
                "Login Error",
                JOptionPane.ERROR_MESSAGE
            );

            return;
        }
        
        //creating a login attempt
        User user = userDao.login(username,password);
        
        if(user != null){

            JOptionPane.showMessageDialog(this,
                "Welcome" + user.getFullName() + "!", 
                "Login Successful", 
                JOptionPane.INFORMATION_MESSAGE
            );
            //checking the user role
            if(user.getRole().equals("Admin")){
                System.out.println("Admin logged in.");
                
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                currentFrame.dispose();
                new AdminDashboard();

            } else if(user.getRole().equals("Cashier")){
                System.out.println("Cashier logged in");

                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                currentFrame.dispose();
                new cashierDashboard();

            }
        } else{
            
            JOptionPane.showMessageDialog(this,"Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}