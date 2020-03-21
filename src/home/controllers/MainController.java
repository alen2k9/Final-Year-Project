package home.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    public BorderPane borderPane;
    public LineChart costChart;

    public PersonTest currentUser;

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
    }

}

