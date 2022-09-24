package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Backend Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    File file;


    public static void main(String[] args) {
        launch(args);
    }

    void loadData() throws IOException{
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        file = selectedFile;
    }

    Optional<ButtonType> popup(String info, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setTitle("Warning!");
        a.setHeaderText(info);
        return a.showAndWait();
    }
}
