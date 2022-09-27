package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
    //Creates list for nodes for data objects
    static List<DataNode> dataNodes;
    static List<LinkNode> linkNodes;



    public static void main(String[] args) {
        launch(args);
    }

    void loadData() throws IOException{
        //Initialise

        dataNodes = new ArrayList<>();
        linkNodes = new ArrayList<>();

        //Gets XML file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(null);
        file = selectedFile;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document d = documentBuilder.parse(file);

            //sets root element
            Element root = d.getDocumentElement();
            //sets link elements
            Node links = root.getElementsByTagName("links").item(0);
            //sets data node elements
            Node nodes = root.getElementsByTagName("nodes").item(0);
            //Converts XML data nodes into objects
            PopulateNodes(nodes);

            /*for (DataNode n :dataNodes) {
                System.out.println(n.toString());
                n.print();
            }

            System.out.println("\n========================================================================================================================================");

            for (LinkNode n :linkNodes) {
                System.out.println("\n"+n.toString());
                n.print();
                for (int i = 0; i < n.getChildren().size(); i++) {
                    System.out.println("\n"+n.getChildren().get(i).toString());
                    n.getChildren().get(i).print();
                }
            }*/
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }
    }

    public static void PopulateNodes(Node node){
        //Initialise
        NamedNodeMap nm;
        DataNode temp = null;
        //Gets all children in data node tree
        NodeList nl = node.getChildNodes();

        //Goes through all children
        for (int i = 0; i < nl.getLength(); i++) {
            //Checks if the element is a TEXT or ELEMENT type
            if (nl.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                //Gets all attributes of child node
                nm = nl.item(i).getAttributes();

                //Creates a data node object depending on the type of tag
                switch (nl.item(i).getNodeName()) {
                    case "printsettings" -> temp = new Subnodes.PrintSetting(nm);
                    case "print" -> temp = new Subnodes.Print(nm);
                    case "welcome" -> temp = new Subnodes.Welcome(nm);
                    case "tiles" -> temp = new Subnodes.Tiles(nm);
                    case "map" -> temp = new Subnodes.Map(nm);
                    case "pdf" -> temp = new Subnodes.PDF(nm);
                    case "video" -> temp = new Subnodes.Video(nm);
                    case "audio" -> temp = new Subnodes.Audio(nm);
                    case "presentation" -> temp = new Subnodes.Presentation(nm);
                    case "SortedList" -> temp = new Subnodes.SortedList(nm);
                    case "list" -> temp = new Subnodes.List(nm);
                    case "img" -> temp = new Subnodes.Image(nm);
                }
                //Add the created node
                dataNodes.add(temp);
            }
        }
    }


    //Method for fetching the attribute value to make things easier
    public static String attrValue(NamedNodeMap n, String s){
        return n.getNamedItem(s).getNodeValue().toString();
    }

    //Popup method
    Optional<ButtonType> popup(String info, Alert.AlertType type, String title) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(info);
        return a.showAndWait();
    }
}
