package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller extends Main implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        argument.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Map.Entry<String, String>, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Map.Entry<String, String>, String> event) {
                String key = event.getRowValue().getKey(), value = event.getNewValue();
                propertyChange(key, value, item.getValue());
                refreshTable();
            }
        });
    }

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
    public void selectItem() throws IOException {
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
