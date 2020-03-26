package home.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
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
    private PersonTest currentUser;

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
        if(tab.equals(HOME)) {
            //TODO : Setup Home
        }
        else if(tab.equals(DASHBOARD)){
            DashboardController controller = loader.getController();
            controller.populate(currentUser);
        }
        else if(tab.equals(SETTINGS)){
            // TODO: Setup Settings
        }
        else if(tab.equals(PROFILE)){
            // TODO: Setup user profile
        }

    }

    void setUser(PersonTest personTest){
        this.currentUser = personTest;
        userLabel.textProperty().setValue(currentUser.firstName + " " + currentUser.lastName);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadUI(HOME);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

