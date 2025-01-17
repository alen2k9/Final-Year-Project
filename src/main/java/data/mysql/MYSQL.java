package data.mysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MYSQL {
    // MySQL Server access settings
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String CONNECTIONURL = "jdbc:mysql://localhost:3306/user";
    public static Connection connection;

    public MYSQL (){

    }

    // Method to retrieve list of users in database
    public List<User> getUsers(){
        List<User> users = new ArrayList<>();

        // Connection to mysql server to retrieve user data
        try {
            connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user.users");

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
            ResultSet resultSet = statement.executeQuery("select userserver.serverid, school, researchgroup, project, server,annualBudget,carbonBudget, datacenterid, floorid, rackid, host from user.userserver , (select serverid, school, researchgroup, project, server,annualBudget, carbonBudget, datacenterid, floorid, rackid, host from user.servers left join user.hosts h on servers.hostid = h.hostid) as `as`" +
                    "where userserver.serverid = as.serverid and userid ="+ userId+";");

            getHosts(hosts, resultSet);
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
            ResultSet resultSet = statement.executeQuery("select school, researchgroup, project, server, annualBudget, carbonBudget from user.userserver join (select serverid, school, researchgroup, project, server, annualBudget,carbonBudget, floorid, rackid, datacenterid, host " +
                    "    from user.servers join user.hosts on servers.hostid = hosts.hostid) as `serverhost` on serverhost.serverid = userserver.serverid " +
                    "where userserver.userid = " + userId + ";");

            while (resultSet.next()) {
                ServerNames user = new ServerNames(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),  resultSet.getInt(5),  resultSet.getInt(6));
                System.out.println(resultSet.getString(1) + " \t\t" + resultSet.getString(2) + " \t" + resultSet.getString(3) + " \t" + resultSet.getString(4));
                serverNames.add(user);
            }
            System.out.println("It works");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return serverNames;
    }

    public static void setServerBudget(int serverId, int annualBudget, int carbonBudget){
        // TODO
        try {
            connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE user.servers SET annualBudget = " + annualBudget + ", carbonBudget = "+carbonBudget+
                    " WHERE serverid = "+serverId+";");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void addServer(int userId, Host newHost){
        // TODO Logic for if the server is there already -> add tag to user to that server
        // TODO if not there create new
        List<Host> hosts = new ArrayList<>();

        boolean serverAdded = false;

        try {
            connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select userserver.serverid, school, researchgroup, project, server,annualBudget, carbonBudget, datacenterid, floorid, rackid, host from user.userserver , (select serverid, school, researchgroup, project, server,annualBudget,carbonBudget, datacenterid, floorid, rackid, host from user.servers left join user.hosts h on servers.hostid = h.hostid) as `as`" +
                    "where userserver.serverid = as.serverid; ");

            getHosts(hosts, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        serverAdded = isServerAdded(userId, newHost, hosts, serverAdded);

        if(!serverAdded){
            try {
                connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                statement.executeUpdate("insert into user.hosts(floorid, rackid, datacenterid, host) values ('"+newHost.floorId+"', '"+newHost.rackId+"', '"+ newHost.datacenterId+"', '"+newHost.hostId+"');");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int hostid = 0;

            try {
                connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select hosts.hostid from user.hosts" +
                        " where datacenterid = "+newHost.datacenterId+" and floorid = "+newHost.floorId+" and rackid = "+newHost.rackId+" and host = "+newHost.hostId+";");

                while (resultSet.next()) {
                    hostid = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                statement.executeUpdate("insert into user.servers(school, researchgroup, project, server, hostid) values ('"+newHost.school+"', '"+newHost.researchGroup+"', '"+ newHost.projectName+"', '"+newHost.serverName+"', '"+hostid+"');");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int serverId = 0;

            try {
                connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select servers.serverid from user.servers" +
                        " where school = '"+newHost.school+"' and researchgroup = '"+newHost.researchGroup+"' and project = '"+newHost.projectName+"' and server = '"+newHost.serverName+"' and hostid = '"+ hostid +"';");

                while (resultSet.next()) {
                    serverId = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {

                connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                statement.executeUpdate("insert into user.userserver(userid, serverid) values ('"+userId+"', '"+serverId+"');");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static boolean isServerAdded(int userId, Host newHost, List<Host> hosts, boolean serverAdded) {
        for(Host eachHost:hosts){
            if(eachHost.serverName.equals(newHost.serverName) && eachHost.datacenterId == newHost.datacenterId && eachHost.floorId == newHost.floorId && eachHost.rackId == newHost.rackId && eachHost.hostId == newHost.hostId){
                // Add User id to serveruser
                try {

                    connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("insert into user.userserver(userid, serverid) values ('"+userId+"', '"+eachHost.serverId+"');");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                serverAdded = true;
            }
        }
        return serverAdded;
    }

    private static void getHosts(List<Host> hosts, ResultSet resultSet) throws SQLException {
        while (resultSet.next()){
            Host newHost = new Host(resultSet.getInt(1),  resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9) , resultSet.getInt(10), resultSet.getInt(11));
            System.out.println(resultSet.getInt(1) + " \t" +   resultSet.getString(2) + " \t" +  resultSet.getString(3) + " \t" +  resultSet.getString(4)+ " \t" +  resultSet.getString(5)+ " \t" +  resultSet.getInt(6)+ " \t" +  resultSet.getInt(7)+ " \t" +  resultSet.getInt(8)+ " \t" +  resultSet.getInt(9) + " \t" +  resultSet.getInt(10) + " \t" +  resultSet.getInt(11));
            hosts.add(newHost);
        }
        System.out.println("It works");
    }

    public static void main(String[] args){
        try {

            connection = DriverManager.getConnection(CONNECTIONURL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into user.userserver(userid, serverid) values ('"+1+"', '"+4+"');");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
