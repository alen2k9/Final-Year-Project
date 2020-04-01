package home.controllers;

import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class for handling Dashboard Scene : Dashboard.fxml
 * */

public class DashboardController implements Initializable {

    // Line Charts
    public LineChart costChart;
    public LineChart carbonChart;

    // Drop down boxes for choosing stats
    public ChoiceBox<String> choice1;
    public ChoiceBox<String> choice2;
    public ChoiceBox<String> choice3;
    public ChoiceBox<String> choice4;

    // current User
    private User currentUser;

    // Graph Setup
    private void setupLinechart() throws IOException {

        // Get Graph Details
        Map<String, Double> powerGraph = currentUser.getPowerData();

        // Tests for graph
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Portfolio 1");

        List<String> months = new ArrayList<>();
        for (String month: powerGraph.keySet() ) {
            months.add(month);
            //series1.getData().add(new XYChart.Data(month, powerGraph.get(month)));
        }

        Collections.sort(months, dateCompare);

        for(String month:months){
            series1.getData().add(new XYChart.Data(month, powerGraph.get(month)));
        }

        /*series1.getData().add(new XYChart.Data("Jan", 23));
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
        series1.getData().add(new XYChart.Data("Dec", 25));*/
        costChart.getXAxis().setAnimated(false);
        costChart.getData().setAll(series1);

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


        carbonChart.getData().setAll(series2);
    }

    // Functionality for button pressed
    // TODO : Check items in drop down box and get Information
    public void dashboardClicked(MouseEvent mouseEvent) {

        // setup Line Chart
        if(!choice1.getSelectionModel().isEmpty() && !choice2.getSelectionModel().isEmpty() && !choice3.getSelectionModel().isEmpty() && !choice4.getSelectionModel().isEmpty()){
            System.out.println(choice1.getSelectionModel().getSelectedItem() + "->" + choice2.getSelectionModel().getSelectedItem() + "->" + choice3.getSelectionModel().getSelectedItem() + "->" + choice4.getSelectionModel().getSelectedItem());
        }
        else if(!choice1.getSelectionModel().isEmpty() && !choice2.getSelectionModel().isEmpty() && !choice3.getSelectionModel().isEmpty()){
            System.out.println(choice1.getSelectionModel().getSelectedItem() + "->" + choice2.getSelectionModel().getSelectedItem() + "->" + choice3.getSelectionModel().getSelectedItem());
        }
        else if(!choice1.getSelectionModel().isEmpty() && !choice2.getSelectionModel().isEmpty()){
            System.out.println(choice1.getSelectionModel().getSelectedItem() + "->" + choice2.getSelectionModel().getSelectedItem());
        }
        else if(!choice1.getSelectionModel().isEmpty()){
            System.out.println(choice1.getSelectionModel().getSelectedItem());
        }

        try {
            setupLinechart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get data from Current User who is logged in
    void populate(User currentUser) {
        // Set Current User
        this.currentUser = currentUser;

        //Adding Drop Down Values
        choice1.getItems().setAll(currentUser.choice1);
        choice1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            choice2.getSelectionModel().clearSelection();
            choice3.getSelectionModel().clearSelection();
            choice4.getSelectionModel().clearSelection();
            if(currentUser.map.containsKey(newValue)){
                choice2.getItems().setAll(currentUser.map.get(newValue));
            }
            else{
                choice2.getItems().setAll(currentUser.map.get(""));
            }
            choice2.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) -> {
                choice3.getSelectionModel().clearSelection();
                choice4.getSelectionModel().clearSelection();
                if(currentUser.map.containsKey(newValue1)){
                    choice3.getItems().setAll(currentUser.map.get(newValue1));
                }
                else{
                    choice3.getItems().setAll(currentUser.map.get(""));
                }
                choice3.getSelectionModel().selectedItemProperty().addListener((observable2, oldValue2, newValue2) -> {
                    choice4.getSelectionModel().clearSelection();
                    if(currentUser.map.containsKey(newValue2)){
                        choice4.getItems().setAll(currentUser.map.get(newValue2));
                    }
                    else{
                        choice4.getItems().setAll(currentUser.map.get(""));
                    }
                });
            });
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setupLinechart();
    }


    private final Comparator<String> dateCompare = (o1, o2) -> {

        SimpleDateFormat s = new SimpleDateFormat("MMM yyyy");
        Date s1 = null;
        Date s2 = null;
        try {
            s1 = s.parse(o1);
            s2 = s.parse(o2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s1.compareTo(s2);
    };
}
