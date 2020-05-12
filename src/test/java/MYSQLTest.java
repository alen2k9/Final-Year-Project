import data.mysql.Host;
import data.mysql.MYSQL;
import data.mysql.ServerNames;
import data.mysql.User;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MYSQLTest {

    @Test
    void getUsers() {
        MYSQL mysql = new MYSQL();
        List<User> users = mysql.getUsers();
        assertFalse(users.isEmpty());
    }

    @Test
    void createNewUser() {
        MYSQL mysql = new MYSQL();

        String userName = "testUsername";
        String password = "testUsername";
        String name = "testName";

        mysql.createNewUser(userName, password, name);

        List<User> users = mysql.getUsers();

        assertTrue(checkUser(userName, password, name, users));
    }

    private boolean checkUser(String userName, String password, String name, List<User> users) {
        for (User user:users) {
            if(user.userName.equals(userName) && user.password.equals(password) && user.name.equals(name)){
                return true;
            }
        }
        return false;
    }

    @Test
    void getServers() {
        MYSQL mysql = new MYSQL();
        List<Host> hosts = mysql.getServers(1);
        assertFalse(hosts.isEmpty());
    }

    @Test
    void getTable() {
        MYSQL mysql = new MYSQL();
        ObservableList<ServerNames> tables = mysql.getTable(1);
        assertFalse(tables.isEmpty());
    }
}