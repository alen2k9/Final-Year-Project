package home.controllers;

import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class for handling Dashboard Scene : Dashboard.fxml
 * */

public class DashboardController implements Initializable {

    // Line Charts
    public LineChart costChart;
    public LineChart carbonChart;

    // Drop down boxes for choosing stats
    public ChoiceBox choice1;
    public ChoiceBox choice2;
    public ChoiceBox choice3;
    public ChoiceBox choice4;


    // Graph Setup
    private void setupLinechart(){

        // Tests for graph
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Portfolio 1");

        series1.getData().add(new XYChart.Data("Jan", 23));
        series1.getData().add(new XYChart.Data("Feb", 14));
        series1.getData().add(new XYChart.Data("Mar", 15));
        series1.getData().add(new XYChart.Data("Apr", 24));
        series1.getData().add(new XYChart.Data("May", 34));
        series1.getData().add(new XYChart.Data("Jun", 36));
        series1.getData().add(new XYChart.Data("Jul", 22));
        series1.getData().add(new XYChart.Data("Aug", 45));
        series1.getData().add(new XYChart.Data("Sep", 43));
        series1.getData().add(new XYChart.Data("Oct", 17));
        series1.getData().add(new XYChart.Data("Nov", 29));
        series1.getData().add(new XYChart.Data("Dec", 25));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Portfolio 2");
        series2.getData().add(new XYChart.Data("Jan", 33));
        series2.getData().add(new XYChart.Data("Feb", 34));
        series2.getData().add(new XYChart.Data("Mar", 25));
        series2.getData().add(new XYChart.Data("Apr", 44));
        series2.getData().add(new XYChart.Data("May", 39));
        series2.getData().add(new XYChart.Data("Jun", 16));
        series2.getData().add(new XYChart.Data("Jul", 55));
        series2.getData().add(new XYChart.Data("Aug", 54));
        series2.getData().add(new XYChart.Data("Sep", 48));
        series2.getData().add(new XYChart.Data("Oct", 27));
        series2.getData().add(new XYChart.Data("Nov", 37));
        series2.getData().add(new XYChart.Data("Dec", 29));

        costChart.getData().addAll(series1);
        carbonChart.getData().addAll(series2);
    }

    // Functionality for button pressed
    // TODO : Check items in drop down box and get Information
    public void dashboardClicked(MouseEvent mouseEvent) {
        setupLinechart();
    }

    // Method to get data from Current User who is logged in
    void populate(PersonTest personTest){
        //Adding Drop Down Values
        choice1.getItems().addAll(personTest.choice1);
        choice2.getItems().addAll(personTest.choice2);
        choice3.getItems().addAll(personTest.choice3);
        choice4.getItems().addAll(personTest.choice4);

        choice1.getSelectionModel().selectFirst();
        choice2.getSelectionModel().selectFirst();
        choice3.getSelectionModel().selectFirst();
        choice4.getSelectionModel().selectFirst();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupLinechart();
    }
}
