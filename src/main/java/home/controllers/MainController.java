package home.controllers;

import data.mysql.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // Constants
    private final String HOME  = "Home";
    private final String DASHBOARD  = "Dashboard";
    private final String SETTINGS  = "Settings";
    private final String PROFILE  = "Profile";

    // FXML Variables
    public BorderPane borderPane;
    public Label userLabel;
    public ImageView userPicture;

    // Current User of application
    private User currentUser;

    // When home button clicked, load it's UI
    public void homeClicked(MouseEvent mouseEvent) throws Exception {
        loadUI(HOME);
    }

    // When dashboard button clicked, load it's UI
    public void dashboardClicked(MouseEvent mouseEvent) throws Exception {
        loadUI(DASHBOARD);
    }

    // When settings button clicked, load it's UI
    public void settingsClicked(MouseEvent mouseEvent) throws Exception {
        loadUI(SETTINGS);
    }

    // When user profile button clicked, load it's UI
    public void openProfile(MouseEvent mouseEvent) throws Exception {
        loadUI(PROFILE);
    }

    // Load UI function, takes which tab to change to and loads that fxml in
    private void loadUI(String tab) throws Exception{

        FXMLLoader loader = null;
        loader = new FXMLLoader(getClass().getResource("../fxml/"+tab+".fxml"));
        borderPane.setCenter(loader.load());
        switch (tab) {
            case HOME:
                // Setup Home
                HomeController homeController = loader.getController();
                homeController.setUser(currentUser);
                break;
            case DASHBOARD:
                DashboardController controller = loader.getController();
                controller.populate(currentUser);
                break;
            case SETTINGS:
                // TODO: Setup Settings
                break;
            case PROFILE:
                // TODO: Setup Profile
                break;
        }
    }

    void setUser(User user){

        this.currentUser = user;
        this.currentUser.setUpUser();
        userLabel.textProperty().setValue(currentUser.name);
        try {
            loadUI(HOME);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       /* try {
            loadUI(HOME);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    // Exit application
    public void logoutUser(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/LoginScreen.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Stage stage = (Stage) userLabel.getScene().getWindow();
        stage.close();
    }

    // UI methods to alter view
    public void mouseEnter(MouseEvent mouseEvent) {
        // make image less opaque
        userPicture.setOpacity(0.5);
    }

    public void mouseLeave(MouseEvent mouseEvent) {
        // make image more opaque
        userPicture.setOpacity(1);
    }
}

