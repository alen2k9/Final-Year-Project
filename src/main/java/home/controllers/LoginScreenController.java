package home.controllers;

import data.mysql.MYSQL;
import data.mysql.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    List<User> people;

    //When Login button is pressed
    public void loginUser(MouseEvent mouseEvent) throws Exception{

        // Traverse through use
        for(User user : people){
           if(usernameField.getText().equals(user.userName) && passwordField.getText().equals(user.password)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Main.fxml"));
                Parent root = loader.load();
                Stage primaryStage = new Stage();
                primaryStage.setMaximized(true);
                primaryStage.setScene(new Scene(root));
                MainController mainController = loader.getController();
                mainController.setUser(user);

                primaryStage.show();

                closeLogin();
            }
            else{
                loginPrompt.setVisible(true);
            }
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

        User user = new User(choice1,map);
        user.password = "password";
        user.userName = "alen2k9";

        // "http://192.168.67.4:8080/papillonserver/rest/datacenters/266/floors/290/racks/293/hosts/286/power?starttime=0&endtime=1585427363"
        user.restService = "http://192.168.67.4:8080/papillonserver/rest/" ;
        user.datacenterId = "266";
        user.floorId = "290";
        user.rackId = "293";
        user.hostId = "286";

        people.add(user);
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
        //getUsers();
        MYSQL mysql = new MYSQL();
        people = mysql.getUsers();
    }


}
