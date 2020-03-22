package home.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public BorderPane borderPane;
    public LineChart costChart;

    public PersonTest currentUser;
    public Label userLabel;

    public void homeClicked(MouseEvent mouseEvent) {
        loadUI("Home");
    }

    public void dashboardClicked(MouseEvent mouseEvent) {
        loadUI("Dashboard");
    }

    public void settingsClicked(MouseEvent mouseEvent) {
        loadUI("Settings");
    }

    private void loadUI(String tab){
        FXMLLoader loader = null;

        try {
            loader = new FXMLLoader(getClass().getResource("../fxml/"+tab+".fxml"));
            borderPane.setCenter(loader.load());
            DashboardController controller = loader.getController();
            controller.populate(currentUser);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUser(PersonTest personTest){
        this.currentUser = personTest;
        userLabel.textProperty().setValue(currentUser.firstName + " " + currentUser.lastName);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void openProfile(MouseEvent mouseEvent) {
    }

    public void logoutUser(MouseEvent mouseEvent) {
    }
}

