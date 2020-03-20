package home.controllers;

import java.util.List;

/**
 * Class to pass values for drop down box
 *
 * */

class PersonTest{

    // Choices List for drop down box
    List<String> choice1;
    List<String> choice2;
    List<String> choice3;
    List<String> choice4;

    // constructor
    PersonTest(List<String> choice1, List<String> choice2, List<String> choice3, List<String> choice4){
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
    }
}
