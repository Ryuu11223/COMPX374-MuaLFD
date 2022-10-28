package sample;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaView;
import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller extends Main implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tblvProperties.setOnMouseClicked(new EventHandler<MouseEvent>() {            ;
            @Override
            public void handle(MouseEvent event) {
                TableCell target = (TableCell) event.getTarget();
                ObservableList<Map.Entry<String,String>> tableRows = tblvProperties.getItems();
                if (target.getId() == "Key") {
                    if (target.getIndex() == tableRows.size()) {
                        tblvProperties.getItems().add(tableRows.size(), new AbstractMap.SimpleEntry<>(null,null));
                        target.startEdit();
                    }
                }
            }
        });
        property.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Map.Entry<String, String>, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Map.Entry<String, String>, String> event) {
                if (event.getNewValue().contains(" ")|| event.getNewValue().equals("")) {
                    popup("This property cannot contain spaces or be null.", Alert.AlertType.WARNING,"Warning!");
                    refreshTable();
                    return;
                }
                propertyChange(event.getOldValue(), event.getNewValue(), item.getValue());
                refreshTable();
            }
        });

        argument.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Map.Entry<String, String>, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Map.Entry<String, String>, String> event) {

                argumentChange(event.getRowValue().getKey(), event.getNewValue(), item.getValue());
                refreshTable();
            }
        });
    }

    @FXML
    public SplitPane scene;


    @FXML
    public MediaView mediaView;

    @FXML
    public ImageView imageView;

    @FXML
    private TreeView<LinkNode> tvFileMenu;

    @FXML
    private Label lblPreviewStatus;


    @FXML
    private TableView<Map.Entry<String, String>> tblvProperties;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> property, argument;

    @FXML
    private TableColumn<Map.Entry<String, String>, Void> buttonColumn;

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
    void upEventHandler(){
        moveTreeItem(item, "up");
    }

    @FXML
    void downEventHandler(){
        moveTreeItem(item, "down");
    }

    @FXML
    void exportEvent(){
        exportFile();
    }

    @FXML
    void loadFile() throws IOException {
        loadData();
        initialiseTree(tvFileMenu);
    }

    @FXML
    void setDirectoryHandler() throws IOException {
        setDirectory();
    }

    @FXML
    void selectItem() throws IOException {
        if (tvFileMenu.getSelectionModel().getSelectedItem() != item){
            item = tvFileMenu.getSelectionModel().getSelectedItem();
            setItem(item);
            setProperty(property);
            setArgument(argument);
            setButtonColumn(buttonColumn);
            setTblvProperties(tblvProperties);
            refreshTable();
            lblPreviewStatus.setText(preview(item,mediaView,imageView));
        }
    }
}
