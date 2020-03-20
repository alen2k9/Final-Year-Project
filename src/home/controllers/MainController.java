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

        List<String> choice1 = new ArrayList<>();
        choice1.add("School of Computer Science");
        choice1.add("School of Science");
        choice1.add("School of Health Science");

        List<String> choice2 = new ArrayList<>();
        choice2.add("Research Group A");
        choice2.add("Research Group B");
        choice2.add("Research Group C");

        List<String> choice3 = new ArrayList<>();
        choice3.add("Server #1");
        choice3.add("Server #2");
        choice3.add("Server #3");
        choice3.add("Server #4");
        choice3.add("Server #5");

        List<String> choice4 = new ArrayList<>();
        choice4.add("Server Project");
        choice4.add("Android Project");

        PersonTest personTest = new PersonTest(choice1, choice2, choice3, choice4);

        try {
            loader = new FXMLLoader(getClass().getResource("../fxml/"+tab+".fxml"));
            borderPane.setCenter(loader.load());
            DashboardController controller = loader.getController();
            controller.populate(personTest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

