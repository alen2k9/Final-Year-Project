package home.controllers;

import java.util.List;
import java.util.Map;

/**
 * Class to pass values for drop down box
 *
 * */

class PersonTest{

    // Choices List for drop down box
    List<String> choice1;
    Map<String, List<String>> map;

    // User details
    String firstName;
    String lastName;
    String userName;
    String password;

    // constructor
    PersonTest(List<String> choice1, Map<String, List<String>> map) {
        this.choice1 = choice1;
        this.map = map;
    }
}
