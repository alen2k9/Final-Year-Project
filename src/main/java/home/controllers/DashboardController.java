package home.controllers;

import data.mysql.MYSQL;
import data.mysql.User;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
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
    public DatePicker startDate;
    public DatePicker endDate;
    public Label emptyData;
    public Button displayDataButton;

    //Bar Charts
    public BarChart costBarChart;
    public BarChart carbonBarChart;

    // Current User
    private User currentUser;

    // Carbon value calculator
    private static final double CARBONPERKWH = 0.367;

    // Cost Calculator
    private static final double COSTPERKWH = 0.1611;

    // Month value
    private static final long MONTH = 2629743;

    // Graph Setup
    private void setupLinechart() throws IOException {

        // Set invisible
        emptyData.setOpacity(0);

        // Check to see if user has chosen dates
        if(startDate.getValue() == null || endDate.getValue() == null){
            emptyData.setText("Correct Date/s not chosen");
            emptyData.setOpacity(1.0);
        }
        else{

            // Get dates in valid format (In String from long as epoch)
            String startDateValue = getStartDate();
            String endDateValue = getEndDate();

            // check if the values have been chosen by drop down and is valid
            // If graph empty dont do add to graph  as it would give error
            if(Long.parseLong(startDateValue) >= Long.parseLong(endDateValue)) {

                // Prompt User
                emptyData.setText("start date is after end date");
                emptyData.setOpacity(1.0);

            }else{

                // Get HashMap of monthly power stats from JSON
                Map<String, Double> powerGraph = currentUser.getPowerData(startDateValue, endDateValue ,currentUser.serverMap.get(choice4.getValue()));

                // Budget Values of User
                int costBudget = currentUser.serverMap.get(choice4.getValue()).annualBudget;
                int carbonBudget = currentUser.serverMap.get(choice4.getValue()).carbonBudget;

                // Check if the powerGraph was a valid call
                if(powerGraph.isEmpty()){
                    emptyData.setText("No available Data");
                    emptyData.setOpacity(1.0);
                }
                else{

                    // Data to store monthly cost
                    XYChart.Series energyCostUsed = new XYChart.Series();
                    energyCostUsed.setName("Current Data");

                    // Cost Budget
                    XYChart.Series costAnnualBudget = new XYChart.Series();
                    costAnnualBudget.setName("Cost Budget");

                    // Carbon Budget
                    XYChart.Series carbonAnnualBudget = new XYChart.Series();
                    carbonAnnualBudget.setName("Carbon Budget");

                    // Carbon Data
                    XYChart.Series carbonValues = new XYChart.Series();
                    carbonValues.setName("Carbon Emission");

                    // List of month strings to do sorting by month
                    List<String> months = new ArrayList<>();

                    // Map to store Cost and carbon values for each year to have an increasing graph
                    Map<String, Double> energyCostMap = new HashMap<>();
                    Map<String, Double> carbonMap = new HashMap<>();

                    // Map to store cost and carbon budget yearly to increase linearly
                    Map<String, Integer> yearCostBudgetMap = new HashMap<>();
                    Map<String, Integer> yearCarbonBudgetMap = new HashMap<>();

                    // Get each month in power Graph
                    for (String month: powerGraph.keySet() ) {
                        months.add(month);
                        try {
                            // Get year and store into Hash map and set value to 0
                            Date date=new SimpleDateFormat("MMM yyyy").parse(month);
                            String year = new SimpleDateFormat("yyyy").format(date);

                            if(!energyCostMap.containsKey(year)){
                                energyCostMap.put(year, 0.0);
                                carbonMap.put(year, 0.0);
                            }
                            if(!yearCostBudgetMap.containsKey(year)){
                                yearCostBudgetMap.put(year, 0);
                                yearCarbonBudgetMap.put(year, 0);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    // Add XY values to the chart
                    costChart.getData().setAll(energyCostUsed, costAnnualBudget);
                    carbonChart.getData().setAll(carbonValues, carbonAnnualBudget);

                    // Sort the months uses comparator
                    months.sort(dateCompare);

                    for(String month:months){

                        // Calculate killowat
                        double kilowatt = (powerGraph.get(month)*60)/1000;

                        try {
                            Date date=new SimpleDateFormat("MMM yyyy").parse(month);
                            String year = new SimpleDateFormat("yyyy").format(date);

                            // data variable for value to add to chart
                            XYChart.Data<String, Double> energyUsedData = new XYChart.Data<>(month, COSTPERKWH * (energyCostMap.get(year) + kilowatt));
                            energyCostUsed.getData().add(energyUsedData);

                            // Set Node Listener for Cost
                            energyUsedData.getNode().setOnMouseClicked(event -> setCostBarChart((String) energyUsedData.getXValue()));
                            energyUsedData.getNode().setOnMouseEntered(event -> energyUsedData.getNode().setOpacity(0.5));
                            energyUsedData.getNode().setOnMouseExited(event -> energyUsedData.getNode().setOpacity(1));

                            // Add cost for year up
                            energyCostMap.put(year, energyCostMap.get(year) + kilowatt);


                            // data variable for carbon value to add to chart
                            XYChart.Data<String, Double> carbonUsedData = new XYChart.Data<>(month, CARBONPERKWH *(kilowatt + carbonMap.get(year) ));
                            carbonValues.getData().add(carbonUsedData);

                            // Set Node Listener for Cost
                            carbonUsedData.getNode().setOnMouseClicked(event -> setCarbonBarChart((String) carbonUsedData.getXValue()));
                            carbonUsedData.getNode().setOnMouseEntered(event -> carbonUsedData.getNode().setOpacity(0.5));
                            carbonUsedData.getNode().setOnMouseExited(event -> carbonUsedData.getNode().setOpacity(1));

                            // Add carbon values for year up
                            carbonMap.put(year, carbonMap.get(year) + kilowatt);

                            // Get monthly budget value
                            int monthlyCostBudget = costBudget/12;
                            int monthlyCarbonBudget = carbonBudget/12;

                            // Add incremented monthly value
                            yearCostBudgetMap.put(year, yearCostBudgetMap.get(year) + monthlyCostBudget);
                            yearCarbonBudgetMap.put(year, yearCarbonBudgetMap.get(year) + monthlyCarbonBudget);

                            // Add data to XY chart
                            costAnnualBudget.getData().add(new XYChart.Data<>(month, yearCostBudgetMap.get(year)));
                            carbonAnnualBudget.getData().add(new XYChart.Data<>(month, yearCarbonBudgetMap.get(year)));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void setCarbonBarChart(String month) {

        // Format Date Value
        SimpleDateFormat df = new SimpleDateFormat("MMM yyyy");
        Date date = null;
        try {
            // Convert to required Date and get Epoch
            date = df.parse(month);
            long epoch = date.getTime()/1000;

            // Get Daily power Graph
            Map<String, Double> powerGraph = currentUser.getPowerDataDay(String.valueOf(epoch), String.valueOf(epoch+MONTH)  ,currentUser.serverMap.get(choice4.getValue()));

            // Convert to list and sort using comparator
            List<String> days = new ArrayList<>(powerGraph.keySet());
            days.sort(dayCompare);

            // Create budget Series
            XYChart.Series carbonLine = new XYChart.Series();
            carbonLine.setName("Daily Carbon Usage");

            // For each day calculate kilowatt and add to Data series
            for(String day:days){
                if(day.contains(month)){
                    double kilowatt = (powerGraph.get(day)*60)/1000;
                    carbonLine.getData().add(new XYChart.Data<>(day, CARBONPERKWH*kilowatt));
                }
            }

            // Add to bar chart
            carbonBarChart.getData().setAll(carbonLine);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setCostBarChart(String month) {

        // Format Date Value
        SimpleDateFormat df = new SimpleDateFormat("MMM yyyy");
        Date date = null;

        try {
            // Convert to required Date and get Epoch
            date = df.parse(month);
            long epoch = date.getTime()/1000;

            // Get daily power graph
            Map<String, Double> powerGraph = currentUser.getPowerDataDay(String.valueOf(epoch), String.valueOf(epoch+MONTH)  ,currentUser.serverMap.get(choice4.getValue()));

            // Convert to list and sort using comparator
            List<String> days = new ArrayList<>(powerGraph.keySet());
            days.sort(dayCompare);

            // Create series
            XYChart.Series costLine = new XYChart.Series();
            costLine.setName("Daily Usage Cost");

            // For each day calculate kilowatt and add to Data series
            for(String day:days){
                if(day.contains(month)) {
                    double kilowatt = (powerGraph.get(day) * 60) / 1000;
                    costLine.getData().add(new XYChart.Data<>(day, COSTPERKWH * kilowatt));
                }
            }

            // Add to bar chart
            costBarChart.getData().setAll(costLine);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    /* Method to Calculate Epoch from date chosen */
    private String getStartDate() {
        long dps = LocalDate.of(startDate.getValue().getYear(), startDate.getValue().getMonth(),
                startDate.getValue().getDayOfMonth())
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli() / 1000;
        return Long.toString(dps);
    }

    /* Method to Calculate Epoch from date chosen */
    private String getEndDate() {
        long dps = LocalDate.of(endDate.getValue().getYear(), endDate.getValue().getMonth(),
                endDate.getValue().getDayOfMonth())
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli() / 1000;
        return Long.toString(dps);
    }

    // Check items in drop down box and get Information
    public void dashboardClicked(MouseEvent mouseEvent) {

        // Check to see user hasn't chosen everything
        if(!choice1.getSelectionModel().isEmpty() || !choice2.getSelectionModel().isEmpty() || !choice3.getSelectionModel().isEmpty() || !choice4.getSelectionModel().isEmpty()){
            emptyData.setText("Please select all");
            emptyData.setVisible(true);
        }
        else{
            emptyData.setVisible(false);
            try {
                setupLinechart();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    // Method to get data from Current User who is logged in
    void populate(User currentUser) {
        // Set Current User
        this.currentUser = currentUser;
        this.currentUser.setUpUser();

        // disable button if user has empty user details
        if(currentUser.firstDropDownChoices.isEmpty() || currentUser.dropDownMap.isEmpty()){
            emptyData.setText("User Data Empty, please update your server details");
            emptyData.setOpacity(1.0);
            displayDataButton.setDisable(true);
        }
        else{
            //Adding Drop Down Values
            choice1.getItems().setAll(currentUser.firstDropDownChoices);
            choice1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                choice2.getSelectionModel().clearSelection();
                choice3.getSelectionModel().clearSelection();
                choice4.getSelectionModel().clearSelection();
                if(currentUser.dropDownMap.containsKey(newValue)){
                    choice2.getItems().setAll(currentUser.dropDownMap.get(newValue));
                }
                else{
                    choice2.getItems().setAll(currentUser.dropDownMap.get(""));
                }
                choice2.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) -> {
                    choice3.getSelectionModel().clearSelection();
                    choice4.getSelectionModel().clearSelection();
                    if(currentUser.dropDownMap.containsKey(newValue1)){
                        choice3.getItems().setAll(currentUser.dropDownMap.get(newValue1));
                    }
                    else{
                        choice3.getItems().setAll(currentUser.dropDownMap.get(""));
                    }
                    choice3.getSelectionModel().selectedItemProperty().addListener((observable2, oldValue2, newValue2) -> {
                        choice4.getSelectionModel().clearSelection();
                        if(currentUser.dropDownMap.containsKey(newValue2)){
                            choice4.getItems().setAll(currentUser.dropDownMap.get(newValue2));
                        }
                        else{
                            choice4.getItems().setAll(currentUser.dropDownMap.get(""));
                        }
                    });
                });
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Reduce animation to enabling running it better
        carbonChart.getXAxis().setAnimated(false);
        costChart.getXAxis().setAnimated(false);
        costBarChart.getXAxis().setAnimated(false);
        carbonBarChart.getXAxis().setAnimated(false);
    }

    /* Comparator for month and year format */
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

    /* Comparator for day month and year format*/
    private final Comparator<String> dayCompare = (o1, o2) -> {
        SimpleDateFormat s = new SimpleDateFormat("dd MMM yyyy");
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

    // TEST
/*   public static void main(String[] args){
        SimpleDateFormat df = new SimpleDateFormat("MMM yyyy");
        Date date = null;
        try {
            date = df.parse("Apr 2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = date.getTime()/1000;
        System.out.println(epoch);
    }
*/
}
