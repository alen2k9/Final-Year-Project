package home.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class MainController {

    public BorderPane borderPane;

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
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../fxml/"+tab+".fxml"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        borderPane.setCenter(root);
    }
}
