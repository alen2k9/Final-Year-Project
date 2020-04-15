package data.mysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MYSQL {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String CONNECTIONURL = "jdbc:mysql://localhost:3306/user";
    public static Connection connection;

    public static void main(String[] args){
        MYSQL mysql = new MYSQL();
        mysql.getServers(1);
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
                System.out.println(resultSet.getInt(1) + " \t\t" + resultSet.getString(2) + " \t" + resultSet.getString(3) + " \t" + resultSet.getString(4));
                users.add(user);
            }
            System.out.println("It works");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User createNewUser(String userName, String password, String name){
        User user = new User();

        try {

            connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into user.users(username, password, name) values ('"+userName+"', '"+password+"', '"+name+"');");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user.users where username = '"+ userName+"';  ");
            while (resultSet.next()) {
                user = new User(resultSet.getInt(1),  resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static List<Host> getServers(int userId){
        List<Host> hosts = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select userserver.serverid, school, researchgroup, project, server, datacenterid, floorid, rackid, host from userserver , (select serverid, school, researchgroup, project, server, datacenterid, floorid, rackid, host from servers left join hosts h on servers.hostid = h.hostid) as `as`" +
                    "where userserver.serverid = as.serverid and userid ="+ userId+";");

            while (resultSet.next()){
                Host host = new Host(resultSet.getInt(1),  resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9) );
                System.out.println(resultSet.getInt(1) + " \t" +   resultSet.getString(2) + " \t" +  resultSet.getString(3) + " \t" +  resultSet.getString(4)+ " \t" +  resultSet.getString(5)+ " \t" +  resultSet.getInt(6)+ " \t" +  resultSet.getInt(7)+ " \t" +  resultSet.getInt(8)+ " \t" +  resultSet.getInt(9) );
                hosts.add(host);
            }
            System.out.println("It works");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hosts;
    }

    public ObservableList<ServerNames> getTable(int userId) {
        ObservableList<ServerNames> serverNames = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select school, researchgroup, project, server from user.userserver join (select serverid, school, researchgroup, project, server, floorid, rackid, datacenterid, host " +
                    "    from user.servers join user.hosts on servers.hostid = hosts.hostid) as `serverhost` on serverhost.serverid = userserver.serverid " +
                    "where userserver.userid = " + userId + ";");

            while (resultSet.next()) {
                ServerNames user = new ServerNames(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                System.out.println(resultSet.getString(1) + " \t\t" + resultSet.getString(2) + " \t" + resultSet.getString(3) + " \t" + resultSet.getString(4));
                serverNames.add(user);
            }
            System.out.println("It works");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return serverNames;
    }

}
