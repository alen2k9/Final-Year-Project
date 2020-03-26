package home.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class LoginScreenController implements Initializable {

    // FXML values to edit application
    public TextField usernameField;
    public TextField passwordField;
    public Button loginButton;
    public Button signUpButton;
    public Label loginPrompt;

    //List of all Users
    List<PersonTest> people;

    //When Login button is pressed
    public void loginUser(MouseEvent mouseEvent) throws Exception{

        // Traverse through use
        for(PersonTest personTest : people){
            //if(usernameField.getText().equals(personTest.userName) && passwordField.getText().equals(personTest.password)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Main.fxml"));
                Parent root = loader.load();
                Stage primaryStage = new Stage();
                primaryStage.setMaximized(true);
                primaryStage.setScene(new Scene(root));
                MainController mainController = loader.getController();
                mainController.setUser(personTest);

                primaryStage.show();

                closeLogin();
           /* }
            else{
                loginPrompt.setVisible(true);
            } */
        }


    }

    private void getUsers() {

        people = new LinkedList<>();
        List<String> choice1 = new ArrayList<>();
        choice1.add("School of Computer Science");
        choice1.add("School of Science");
        choice1.add("School of Health Science");


        Map<String, List<String>> map = new HashMap<>();

        map.put("School of Computer Science", new ArrayList<String>());
        map.get("School of Computer Science").add("Research Group A");
        map.get("School of Computer Science").add("Research Group B");

        map.put("School of Science", new ArrayList<String>());
        map.get("School of Science").add("Research Group C");

        map.put("Research Group A", new ArrayList<String>());
        map.get("Research Group A").add("Server Project");
        map.get("Research Group A").add("Android Project");

        map.put("Research Group B", new ArrayList<String>());
        map.get("Research Group B").add("Server Project");

        map.put("Server Project", new ArrayList<String>());
        map.get("Server Project").add("Server #1");
        map.get("Server Project").add("Server #2");
        map.get("Server Project").add("Server #3");

        map.put("Android Project", new ArrayList<String>());
        map.get("Android Project").add("Server #4");
        map.get("Android Project").add("Server #5");

        // Empty value
        map.put("", new ArrayList<>());

        PersonTest personTest = new PersonTest(choice1,map);
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

    // Get User Data
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getUsers();
    }


}
