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
    void addEvent() {

    }

    TreeItem<LinkNode> item;

    @FXML
    void deleteEvent() {
        if(item.getChildren().isEmpty()) {
            item.getParent().getChildren().remove(item);
        }
        else{
            Optional<ButtonType> result = popup("This collection contains child elements. Are you sure you want to delete?", Alert.AlertType.CONFIRMATION, "Warning!");
            if(result.isEmpty() || result.get() == ButtonType.OK) {
                item.getParent().getChildren().remove(item);
            }
        }
    }

    @FXML
    void loadFile() throws IOException {
        loadData();
    }

    @FXML
    private void selectItem() {
        item = tvFileMenu.getSelectionModel().getSelectedItem();
    }
}
