package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import javafx.scene.control.TreeView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> rootItem = new TreeItem<String>("XML files");
        TreeItem<String> branchItem1 = new TreeItem<String>("Head");
        TreeItem<String> branchItem2 = new TreeItem<String>("Pictures");
        TreeItem<String> branchItem3 = new TreeItem<String>("Videos");
        TreeItem<String> branchItem4 = new TreeItem<String>("Cats");

        branchItem3.getChildren().add(branchItem4);
        rootItem.getChildren().addAll(branchItem1, branchItem2, branchItem3);
        trHierarcy.setRoot(rootItem);

    }
    @FXML
    private TextField tfTitle;

    @FXML
    private TreeView trHierarcy;

    @FXML
    void btnClick(ActionEvent event) {
        Stage mainWindow = (Stage) tfTitle.getScene().getWindow();
        String title = tfTitle.getText();
        mainWindow.setTitle(title);
    }

    @FXML
    private void selectItem() {
        TreeItem<String> item = (TreeItem<String>) trHierarcy.getSelectionModel().getSelectedItem();
        Stage mainWindow = (Stage) trHierarcy.getScene().getWindow();

        if(item != null) {
            mainWindow.setTitle(item.getValue());
            return;
        }

        mainWindow.setTitle(item.getValue());
    }



}
