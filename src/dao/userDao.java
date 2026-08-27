package dao;

//importing the database connectivity and sql connection modules
import database.DatabaseConnection;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDao{

    public User login(String username, String password){
        String sql= """
                SELECT userID, username, password, role, full_name
                FROM userTable
                WHERE username =? AND password = ?
                """;

        //implement try and catch
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
                //having the username be inserted first
                statement.setString(1, username);

                //password
                statement.setString(2,password);

                //executing the sql query
                try(ResultSet resultSet = statement.executeQuery()){
                    //checking whether a matching user is found
                    if(resultSet.next()){
                        return new User(
                            resultSet.getInt("userID"),
                            resultSet.getString("username"),
                            //it was suggetested that I hide the password
                            null,
                            resultSet.getString("role"),
                            resultSet.getString("full_name")
                        );
                    }
                }
                
            } catch(SQLException e){
                
                System.err.println("Login failed: " + e.getMessage());
            }

            //No matching user was found
            return null;
    }
}