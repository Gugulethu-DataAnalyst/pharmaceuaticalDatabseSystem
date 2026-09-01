package models;

public class User{
    //Creating a private instances of the users attributes
    private int userID;
    private String username;
    private String password;
    private String role;
    private String fullName;

    public User(int userID, String username, String password, 
        String role, String fullName){

            this.userID = userID;
            this.username = username;
            this.password = password;
            this.role = role;
            this.fullName = fullName;
    }

    //setting up getters and setters for the variables
    /*=============================
    GET AND SET FOR USERID
    ===============================*/
    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID){
        this.userID= userID;
    }

    /*=============================
    GET AND SET FOR USERNAME
    ===============================*/
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    

    /*=============================
    GET AND SET FOR PASSWORD
    ===============================*/
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }


    /*=============================
    GET AND SET FOR ROLE
    ===============================*/
    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }


    /*=============================
    GET AND SET FOR FULL NAME
    ===============================*/
    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }
}
