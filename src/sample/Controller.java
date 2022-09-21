package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.*;
import java.util.*;

public class Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> rootItem = new TreeItem<>("Collection 1");
        TreeItem<String> branchItem1 = new TreeItem<>("Video");
        TreeItem<String> branchItem2 = new TreeItem<>("PDF");
        TreeItem<String> branchItem3 = new TreeItem<>("Map");
        TreeItem<String> branchItem4 = new TreeItem<>("Cats");

        branchItem3.getChildren().add(branchItem4);
        rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3);
        tvFileMenu.setRoot(rootItem);

    }

    @FXML
    private TreeView tvFileMenu;

    @FXML
    void addEvent() {

    }

    TreeItem item;

    @FXML
    void deleteEvent() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Warning!");
        a.setHeaderText("This collection contains child elements. Are you sure you want to delete?");

        if(item.getChildren().isEmpty()) {
            item.getParent().getChildren().remove(item);
        }
        else{
            Optional<ButtonType> result = a.showAndWait();
            if(!result.isPresent() || result.get() == ButtonType.OK) {
                item.getParent().getChildren().remove(item);
            }
        }
    }

    @FXML
    private void selectItem() {
        item = (TreeItem) tvFileMenu.getSelectionModel().getSelectedItem();
    }
}
