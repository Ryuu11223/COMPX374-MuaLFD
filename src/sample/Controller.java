package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import javafx.scene.control.TreeView;

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
        item.getParent().getChildren().remove(item);
    }

    @FXML
    private void selectItem() {
        item = (TreeItem) tvFileMenu.getSelectionModel().getSelectedItem();
    }
}
