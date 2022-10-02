package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class Controller extends Main implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    @FXML
    private TreeView<LinkNode> tvFileMenu;

    @FXML
    private TableView<Map.Entry<String, String>> tblvProperties;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> property;
    @FXML
    private TableColumn<Map.Entry<String, String>, String> argument;


    @FXML
    void addEvent() {

    }

    TreeItem<LinkNode> item;

    @FXML
    void deleteEventHandler() {
        deleteEvent(item);
    }

    @FXML
    void exportEvent(){
        exportFile();
    }

    @FXML
    void onEditStart(){
    }

    @FXML
    void textChanged(){
    }

    @FXML
    void loadFile() throws IOException {
        loadData();
        InitialiseTree(tvFileMenu);
    }

    @FXML
    private void selectItem() {
        LinkNode selectedItem = tvFileMenu.getSelectionModel().getSelectedItem().getValue();
        item = tvFileMenu.getSelectionModel().getSelectedItem();
        InitialiseProperties(selectedItem,property,argument,tblvProperties);
    }
}
