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
    void addImgEvent() { findPos(item, new Subnodes.Image()); }
    @FXML
    void addVidEvent() { findPos(item, new Subnodes.Video()); }
    @FXML
    void addAudEvent() { findPos(item, new Subnodes.Audio()); }
    @FXML
    void addPdfEvent() { findPos(item, new Subnodes.PDF()); }
    @FXML
    void addSltEvent() { findPos(item, new Subnodes.SortedList()); }
    @FXML
    void addLstEvent() { findPos(item, new Subnodes.List()); }
    @FXML
    void addPrzEvent() { findPos(item, new Subnodes.Presentation()); }
    @FXML
    void addMapEvent() { findPos(item, new Subnodes.Map()); }


    TreeItem<LinkNode> item;

    @FXML
    void deleteEventHandler() {
        deleteEvent(item);
        item = null;
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
