package home.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LoginScreenController {
    public TextField usernameField;
    public TextField passwordField;
    public Button loginButton;
    public Button signUpButton;

    List<PersonTest> people;

    public void loginUser(MouseEvent mouseEvent) throws Exception{

        people = new LinkedList<>();
        getUsers();

        for(PersonTest personTest : people){
            if(usernameField.getText().equals(personTest.userName) && passwordField.getText().equals(personTest.password)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Main.fxml"));
                Parent root = loader.load();
                Stage primaryStage = new Stage();
                primaryStage.setMaximized(true);
                primaryStage.setScene(new Scene(root));
                MainController mainController = loader.getController();
                mainController.setUser(personTest);

                primaryStage.show();

                closeLogin();
            }
            else{

            }
        }


    }

    private void getUsers() {
        List<String> choice1 = new ArrayList<>();
        choice1.add("School of Computer Science");
        choice1.add("School of Science");
        choice1.add("School of Health Science");

        List<String> choice2 = new ArrayList<>();
        choice2.add("Research Group A");
        choice2.add("Research Group B");
        choice2.add("Research Group C");

        List<String> choice3 = new ArrayList<>();
        choice3.add("Server #1");
        choice3.add("Server #2");
        choice3.add("Server #3");
        choice3.add("Server #4");
        choice3.add("Server #5");

        List<String> choice4 = new ArrayList<>();
        choice4.add("Server Project");
        choice4.add("Android Project");

        PersonTest personTest = new PersonTest(choice1, choice2, choice3, choice4);
        personTest.firstName = "Alen";
        personTest.lastName = "Thomas";
        personTest.password = "password";
        personTest.userName = "alen2k9";

        people.add(personTest);
    }

    private void closeLogin() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    public void signUpUser(MouseEvent mouseEvent) {
    }
}
