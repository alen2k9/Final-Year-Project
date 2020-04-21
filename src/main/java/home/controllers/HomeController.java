package home.controllers;

import data.mysql.*;
import javafx.collections.ObservableList;
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
import java.time.Instant;
import java.time.LocalDate;
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

    // Budget Field
    public TextField annualBudgetField;

    // Graph data
    public AreaChart usageGraph;

    // FINAL
    private static final long MILLISECONDS = 1000;
    private static final long TWELVEMONTHSEPOCH = 31556916;
    private static final double COSTPERKWH = 0.1611;

    // User Setup method
    void setUser(User user){
        this.currentUser = user;
        setUpTable();
    }

    // Add values to table
    private void setUpTable() {
        MYSQL mysql = new MYSQL();
        serverTable.setItems(mysql.getTable(currentUser.userId));
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schoolColumn.setCellValueFactory(new PropertyValueFactory<ServerNames, String>("school"));
        researchGroupColumn.setCellValueFactory(new PropertyValueFactory<ServerNames, String>("researchGroup"));
        projectColumn.setCellValueFactory(new PropertyValueFactory<ServerNames, String>("project"));
        serverNameColumn.setCellValueFactory(new PropertyValueFactory<ServerNames, String>("serverName"));
        usageGraph.getXAxis().setAnimated(false);
    }

    // TODO
    public void addServer(MouseEvent mouseEvent) {
    }

    // TODO
    public void setBudget(MouseEvent mouseEvent) {
    }

    public void rowSelected(MouseEvent mouseEvent) {
        ServerNames server = serverTable.getSelectionModel().getSelectedItem();

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

                setGraph(new Server(host.datacenterId, host.floorId, host.rackId, host.hostId));
                break;
            }
        }
    }

    private void setGraph(Server server) {
        long now = System.currentTimeMillis()/MILLISECONDS;
        long twelveMonthsAgo = now - 3*TWELVEMONTHSEPOCH;

        try {

            XYChart.Series usageGraphAxis = new XYChart.Series();
            usageGraphAxis.setName("Current Usage");

            Map<String, Double> usageGraphMap = currentUser.getPowerData(Long.toString(twelveMonthsAgo), Long.toString(now) ,server);
            if(usageGraphMap.isEmpty()){
                // TODO
                System.out.print("works and empty");
            }
            else {
                List<String> months = new ArrayList<>(usageGraphMap.keySet());

                months.sort(dateCompare);

                double total = 0;
                for(String month:months){
                    double kilowatt = (usageGraphMap.get(month)*60)/1000;
                    total += (kilowatt * COSTPERKWH);
                    usageGraphAxis.getData().add(new XYChart.Data(month, total));
                }
                usageGraph.getData().setAll(usageGraphAxis);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        long epoch = System.currentTimeMillis()/1000 - (2629743*12);
        System.out.print(epoch);
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
