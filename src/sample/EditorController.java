package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class EditorController extends Main{

    @FXML
    public Button btnCancel;
    @FXML
    public Button btnConfirm;
    @FXML
    public Button btnFilePath;
    @FXML
    private TextArea textArea;

    public Stage stage;

    private String key, value;;
    private LinkNode item;

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
        textArea.setText(value);
    }

    public void setItem(LinkNode item) {
        this.item = item;
    }

    @FXML
    void cancelHandler() throws Exception {
        stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    @FXML
    void confirmHandler(){
        value = textArea.getText();
        stage = (Stage) btnConfirm.getScene().getWindow();
        propertyChange(key,value,item);
        stage.close();
    }

    @FXML
    void filePathHandler(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        textArea.setText(file.getAbsolutePath());
    }

}
