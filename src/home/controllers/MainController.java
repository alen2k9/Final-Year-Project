package home.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {

    public BorderPane borderPane;
    public LineChart costChart;

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
        DashboardController controller = null;
        try {
            loader = new FXMLLoader(getClass().getResource("../fxml/"+tab+".fxml"));
            controller = loader.getController();
            borderPane.setCenter(loader.load());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
