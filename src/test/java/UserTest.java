import data.mysql.MYSQL;
import data.mysql.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @org.junit.jupiter.api.Test
    void setUpUser() {
        MYSQL mysql = new MYSQL();
        List<User> users = mysql.getUsers();
        for(User user: users){
            assertNull(user.hosts);
        }

        for(User user: users){
            assertFalse(user.hosts instanceof ArrayList);
            user.setUpUser();
            assertTrue(user.hosts instanceof ArrayList);
        }

    }
}