package data.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MYSQL {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String CONNECTIONURL = "jdbc:mysql://localhost:3306/user";
    public Connection connection;

    public static void main(String[] args){
        MYSQL mysql = new MYSQL();
    }

    public MYSQL (){

    }

    // Method to retrieve list of users in database
    public List<User> getUsers(){
        List<User> users = new ArrayList<>();

        // Connection to mysql server to retrieve user data
        try {
            connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");

            while (resultSet.next()){
                User user = new User(resultSet.getInt(1),  resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                System.out.println(resultSet.getInt(1) + " \t\t" + resultSet.getString(2) + " \t" + resultSet.getString(3) + " \t" + resultSet.getString(4) + " \t" + resultSet.getInt(5));
                users.add(user);
            }
            System.out.println("It works");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
