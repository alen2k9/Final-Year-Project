package home.controllers;

import data.mysql.MYSQL;
import data.mysql.ServerNames;
import data.mysql.User;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller Class for the Profile FXML
 *
 */

public class ProfileController implements Initializable {

    // Fxml Objects
    public TableView<ServerNames> serverTable;
    public TableColumn<ServerNames, String> schoolColumn;
    public TableColumn<ServerNames, String> researchGroupColumn;
    public TableColumn<ServerNames, String> projectColumn;
    public TableColumn<ServerNames, String> serverNameColumn;

    // User who is logged in
    public User currentUser;

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
}
