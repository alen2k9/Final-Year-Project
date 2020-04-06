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

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LoginScreenController implements Initializable {

    // FXML values for Login application
    public Button loginButton;
    public Label loginPrompt;
    public TextField loginpPasswordField;
    public TextField loginUsernameField;

    // FXML values for Sign Up application
    public TextField signupPassword;
    public TextField signupUsername;
    public Label signupPrompt;
    public TextField signUpName;
    public Button signUpButton;

    //List of all Users
    List<User> people;

    //When Login button is pressed
    public void loginUser(MouseEvent mouseEvent) throws Exception{

        // Traverse through use
        for(User user : people){
           if(loginUsernameField.getText().equals(user.userName) && loginpPasswordField.getText().equals(user.password)) {
               loadMain(user);
           }
            else{
                loginPrompt.setVisible(true);
            }
        }


    }

    private void closeLogin() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    public void signUpUser(MouseEvent mouseEvent) throws IOException {
        if(signUpName.getText().isEmpty() || signupUsername.getText().isEmpty() || signupPassword.getText().isEmpty()){
            signupPrompt.setText("Please fill all values");
            signupPrompt.setVisible(true);
        }
        else{
            for(User user : people){
                if(signupUsername.getText().equals(user.userName)) {

                    signupPrompt.setText("Username already in use, please choose another");
                    signupPrompt.setVisible(true);

                }
                else{
                    User newUser = new User(signupUsername.getText(), signupPassword.getText(), signUpName.getText());
                    loadMain(newUser);
                }
            }
        }

    }

    private void loadMain(User newUser) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Main.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root));
        MainController mainController = loader.getController();
        mainController.setUser(newUser);

        primaryStage.show();

        closeLogin();
    }

    // Get User Data
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //getUsers();
        MYSQL mysql = new MYSQL();
        people = mysql.getUsers();
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

}
