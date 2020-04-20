package home.controllers;

import data.mysql.Host;
import data.mysql.MYSQL;
import data.mysql.ServerNames;
import data.mysql.User;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

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
                break;
            }
        }
    }
}
