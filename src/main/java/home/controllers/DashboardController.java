package home.controllers;

import com.google.gson.Gson;
import data.power.MainPower;
import data.power.Power;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private PersonTest currentUser;


    // Graph Setup
    private void setupLinechart() throws IOException {


        Map<String, Double> powerGraph = test();

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

        //setupLinechart();
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
    void populate(PersonTest personTest) {

        // Set Current User
        this.currentUser = personTest;

        //Adding Drop Down Values
        choice1.getItems().setAll(personTest.choice1);
        choice1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            choice2.getSelectionModel().clearSelection();
            choice3.getSelectionModel().clearSelection();
            choice4.getSelectionModel().clearSelection();
            if(personTest.map.containsKey(newValue)){
                choice2.getItems().setAll(personTest.map.get(newValue));
            }
            else{
                choice2.getItems().setAll(personTest.map.get(""));
            }
            //choice2.getSelectionModel().selectFirst();
            choice2.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) -> {
                choice3.getSelectionModel().clearSelection();
                choice4.getSelectionModel().clearSelection();
                if(personTest.map.containsKey(newValue1)){
                    choice3.getItems().setAll(personTest.map.get(newValue1));
                }
                else{
                    choice3.getItems().setAll(personTest.map.get(""));
                }
                //choice3.getSelectionModel().selectFirst();
                choice3.getSelectionModel().selectedItemProperty().addListener((observable2, oldValue2, newValue2) -> {
                    choice4.getSelectionModel().clearSelection();
                    if(personTest.map.containsKey(newValue2)){
                        choice4.getItems().setAll(personTest.map.get(newValue2));
                    }
                    else{
                        choice4.getItems().setAll(personTest.map.get(""));
                    }
                    //choice4.getSelectionModel().selectFirst();
                });
            });
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setupLinechart();
    }

    private Map<String, Double> test() throws IOException {
        URL url = new URL(currentUser.restService + "datacenters/266/floors/290/racks/293/hosts/286/power?starttime=15854276363&endtime=1585427363");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + connection.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));

        String output = br.readLine();

        Gson gson = new Gson();

        MainPower mainPower = gson.fromJson(output, MainPower.class);

        Map<String, Double> powerGraph = doMapping(mainPower);

        System.out.println("Output from Server .... \n");
        return powerGraph;
    }

    private Map<String, Double> doMapping(MainPower mainPower) {
        Map<String, Double> powerMap = new TreeMap<>();
        for (Power power : mainPower.getPower()) {
            // Europe/Dublin
            long secondsSinceEpoch = Long.parseLong(power.getTimeStamp());
            Instant instant = Instant.ofEpochSecond(secondsSinceEpoch);
            LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Dublin"));
            String month = ldt.getMonth().toString() + " " + ldt.getYear();

            Double powerValue = Double.valueOf(power.getPower());
            if (powerMap.containsKey(month)) {
                powerMap.computeIfPresent(month, (key, val) -> val = val + powerValue);
            } else {
                powerMap.put(month, powerValue);
            }

        }
        return powerMap;
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
