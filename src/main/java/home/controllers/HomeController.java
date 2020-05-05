package home.controllers;

import data.mysql.*;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller Class for the Profile FXML
 *
 */

public class HomeController implements Initializable {

    // Fxml Objects
    public TableView<ServerNames> serverTable;
    public TableColumn<ServerNames, String> schoolColumn;
    public TableColumn<ServerNames, String> researchGroupColumn;
    public TableColumn<ServerNames, String> projectColumn;
    public TableColumn<ServerNames, String> serverNameColumn;
    public TableColumn<ServerNames, String> annualBudgetColumn;
    public TableColumn<ServerNames, String> carbonBudgetColumn;

    // User who is logged in
    public User currentUser;
    public Button setBudgetButton;
    
    // Server Details 
    public Label schoolNameField;
    public Label researchGroupField;
    public Label projectGroupField;
    public Label serverNameField;
    public Label datacenterIdField;
    public Label floorIdField;
    public Label rackIdField;
    public Label hostIdField;

    // Add Server Details
    public TextField addServerSchoolNameField;
    public TextField addServerResearchGroupField;
    public TextField addServerProjectGroupField;
    public TextField addServerServerNameGroupField;
    public TextField addServerDatacenterIdField;
    public TextField addServerFloorIdField;
    public TextField addServerRackIdField;
    public TextField addServerHostIdField;

    // Budget Field
    public TextField annualBudgetField;
    public TextField carbonBudgetField;

    // Graph data
    public AreaChart usageGraph;

    // FINAL
    private static final long MILLISECONDS = 1000;
    private static final long TWELVEMONTHSEPOCH = 31556916;
    private static final double COSTPERKWH = 0.1611;

    // Label for data
    public Label noAvailableData;

    // Empty Field Notification
    public Label addServerField;
    public Label budgetLabel;

    // Current Host
    private Host currentHost;


    // User Setup method
    void setUser(User user){
        this.currentUser = user;
        this.currentUser.setUpUser();
        setUpTable();
    }

    // Add values to table
    private void setUpTable() {
        MYSQL mysql = new MYSQL();
        serverTable.setItems(mysql.getTable(currentUser.userId));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set values of Table
        schoolColumn.setCellValueFactory(new PropertyValueFactory<>("school"));
        researchGroupColumn.setCellValueFactory(new PropertyValueFactory<>("researchGroup"));
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));
        serverNameColumn.setCellValueFactory(new PropertyValueFactory<>("serverName"));
        annualBudgetColumn.setCellValueFactory(new PropertyValueFactory<>("annualBudget"));
        carbonBudgetColumn.setCellValueFactory(new PropertyValueFactory<>("carbonBudget"));

        // Stop animation
        usageGraph.getXAxis().setAnimated(false);
    }

    /**
     *  Check to see if user has left values empty -> prompt user
     *  Check if values are valid types (numeric) -> prompt
     *  Check for repetition -> prompt
     **/
    public void addServer(MouseEvent mouseEvent) {

        addServerField.setVisible(false);

        if(addServerSchoolNameField.getText().isEmpty() || addServerResearchGroupField.getText().isEmpty() || addServerProjectGroupField.getText().isEmpty()
                || addServerServerNameGroupField.getText().isEmpty() || addServerDatacenterIdField.getText().isEmpty() || addServerFloorIdField.getText().isEmpty()
                || addServerRackIdField.getText().isEmpty() || addServerHostIdField.getText().isEmpty()){

            addServerField.setText("Please Fill All Fields Below!");
            addServerField.setVisible(true);
        }
        else if(!isNumeric(addServerDatacenterIdField.getText()) || !isNumeric(addServerFloorIdField.getText()) || !isNumeric(addServerRackIdField.getText()) || !isNumeric(addServerHostIdField.getText())){
            addServerField.setText("Please use numeric values for Datacenter, Floor, Rack and Host IDs");
            addServerField.setVisible(true);
        }
        else if(currentUser.serverMap.containsKey(addServerServerNameGroupField.getText())){
            addServerField.setText("Server with Same name already in User Database");
            addServerField.setVisible(true);
        }
        else{
            addServerField.setVisible(false);
            MYSQL.addServer(currentUser.userId,new Host(addServerSchoolNameField.getText(), addServerResearchGroupField.getText(), addServerProjectGroupField.getText(), addServerServerNameGroupField.getText(), Integer.parseInt(addServerDatacenterIdField.getText()), Integer.parseInt(addServerFloorIdField.getText()), Integer.parseInt(addServerRackIdField.getText()), Integer.parseInt(addServerHostIdField.getText())));
            setUpTable();
        }
    }

    /* Check for correct input and set Budget*/
    public void setBudget(MouseEvent mouseEvent) {
        if(currentHost == null){
            budgetLabel.setText(" No Host Selected To Set Budget ");
            budgetLabel.setVisible(true);
        }
        else if(annualBudgetField.getText().isEmpty() || !isNumeric(annualBudgetField.getText()) || carbonBudgetField.getText().isEmpty() || !isNumeric(carbonBudgetField.getText())){
            budgetLabel.setText(" Please input valid values (Numeric)");
            budgetLabel.setVisible(true);
        }
        else{
            budgetLabel.setVisible(false);

            // Update SQL server
            MYSQL.setServerBudget(currentHost.serverId, Integer.parseInt(annualBudgetField.getText()), Integer.parseInt(carbonBudgetField.getText()));
            setUpTable();
        }
    }

    /* Display data */
    public void rowSelected(MouseEvent mouseEvent) {

        // Get chosen server data
        ServerNames server = serverTable.getSelectionModel().getSelectedItem();

        // Find server and display information on it
        for(Host host:currentUser.hosts){
            if(host.school.equals(server.getSchool()) && host.researchGroup.equals(server.getResearchGroup()) && host.projectName.equals(server.getProject()) && host.serverName.equals(server.getServerName())){
                schoolNameField.setText(host.school);
                researchGroupField.setText(host.researchGroup);
                projectGroupField.setText(host.projectName);
                serverNameField.setText(host.serverName);
                datacenterIdField.setText(String.valueOf(host.datacenterId));
                rackIdField.setText(String.valueOf(host.rackId));
                floorIdField.setText(String.valueOf(host.floorId));
                hostIdField.setText(String.valueOf(host.hostId));
                annualBudgetField.setText(String.valueOf(host.annualBudget));
                carbonBudgetField.setText(String.valueOf(host.carbonBudget));

                currentHost = host;

                // Set the graph
                setGraph(new Server(host.datacenterId, host.floorId, host.rackId, host.hostId, host.annualBudget, host.carbonBudget));
                break;
            }
        }
    }

    private void setGraph(Server server) {

        // Get current epoch and epoch from 1 year ago
        long now = System.currentTimeMillis()/MILLISECONDS;
        long twelveMonthsAgo = now - TWELVEMONTHSEPOCH;

        try {

            // Create series for data
            XYChart.Series usageGraphAxis = new XYChart.Series();
            usageGraphAxis.setName("Current Usage");

            // Get monthly data in hashmap
            Map<String, Double> usageGraphMap = currentUser.getPowerData(Long.toString(twelveMonthsAgo), Long.toString(now) ,server);

            if(usageGraphMap.isEmpty()){
                noAvailableData.setText("No valid Data from the last 12 months");
                noAvailableData.setVisible(true);
            }
            else {
                noAvailableData.setVisible(false);

                // Get months list and sort
                List<String> months = new ArrayList<>(usageGraphMap.keySet());
                months.sort(dateCompare);

                double total = 0;
                for(String month:months){

                    // Calculate kilowatt and add to series
                    double kilowatt = (usageGraphMap.get(month)*60)/1000;
                    total += (kilowatt * COSTPERKWH);
                    usageGraphAxis.getData().add(new XYChart.Data(month, total));
                }

                // add dat to chart
                usageGraph.getData().setAll(usageGraphAxis);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // comparator for Month and Year
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

    // Check to see if number is numeric
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
