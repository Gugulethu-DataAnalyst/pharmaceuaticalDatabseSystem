package dao;
import models.User;

public class userDaoTest{
    //main window
    public static  void main(String[] args){
        //initializing the userDao 
        userDao userDao = new userDao();

        //testing the admin account of the database
        User first_user = userDao.login("gugulethud@Pharma", "#80christalclear");

        //if control statement for the login system
        if(first_user != null){

            System.out.println("Login successful!");
            System.out.println("User ID: " + first_user.getUserID());
            System.out.println("Username: " + first_user.getUsername());
            System.out.println("Role: " + first_user.getRole());
            System.out.println("Full Name: " + first_user.getFullName());
        }else{
            System.out.println("Login Failed");
        }
    }
}