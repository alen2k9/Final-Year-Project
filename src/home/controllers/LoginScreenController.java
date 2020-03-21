package home.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginScreenController {
    public TextField usernameField;
    public TextField passwordField;
    public Button loginButton;
    public Button signUpButton;

    public void loginUser(MouseEvent mouseEvent) throws Exception{
        if(usernameField.getText().equals("Alen") && passwordField.getText().equals("password")) {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/Main.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setMaximized(true);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            closeLogin();
        }
        else{

        }

    }

    private void closeLogin() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    public void signUpUser(MouseEvent mouseEvent) {
    }
}
