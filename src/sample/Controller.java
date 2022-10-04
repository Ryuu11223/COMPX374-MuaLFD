package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class Controller extends Main implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        argument.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Map.Entry<String, String>, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Map.Entry<String, String>, String> event) {
                String key = event.getRowValue().getKey(), value = event.getNewValue();

                if (propertyChange(key, value, item))
                    selectItem();
            }
        });
    }


    @FXML
    private TreeView<LinkNode> tvFileMenu;

    @FXML
    private TableView<Map.Entry<String, String>> tblvProperties;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> property;
    @FXML
    TableColumn<Map.Entry<String, String>, String> argument;

    @FXML
    void addEvent() {

    }

    TreeItem<LinkNode> item;

    @FXML
    void addEventC() {
        if (addChild(item))
            InitialiseTree(tvFileMenu);
    }

    @FXML
    void deleteEventHandler() {
        deleteEvent(item);
    }

    @FXML
    void exportEvent(){
        exportFile();
    }


    @FXML
    void loadFile() throws IOException {
        loadData();
        InitialiseTree(tvFileMenu);
    }

    @FXML
    private void selectItem() {
        if (tvFileMenu.getSelectionModel().getSelectedItem() != item){
            LinkNode selectedItem = tvFileMenu.getSelectionModel().getSelectedItem().getValue();
            item = tvFileMenu.getSelectionModel().getSelectedItem();
            InitialiseProperties(selectedItem,property,argument,tblvProperties);
        }
    }
}
