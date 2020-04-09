package home.controllers;

import data.mysql.MYSQL;
import data.mysql.Server;
import data.mysql.User;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public TableView<Server> serverTable;
    public TableColumn<Server, String> schoolColumn;
    public TableColumn<Server, String> researchGroupColumn;
    public TableColumn<Server, String> projectColumn;
    public TableColumn<Server, String> serverNameColumn;

    public User currentUser;

    void setUser(User user){
        this.currentUser = user;
        setUpTable();
    }

    private void setUpTable() {
        MYSQL mysql = new MYSQL();
        serverTable.setItems(mysql.getTable(currentUser.userId));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schoolColumn.setCellValueFactory(new PropertyValueFactory<Server, String>("school"));
        researchGroupColumn.setCellValueFactory(new PropertyValueFactory<Server, String>("researchGroup"));
        projectColumn.setCellValueFactory(new PropertyValueFactory<Server, String>("project"));
        serverNameColumn.setCellValueFactory(new PropertyValueFactory<Server, String>("serverName"));
    }
}
