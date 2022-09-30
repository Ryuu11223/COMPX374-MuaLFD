package sample;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

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
    static List<LinkNode> collectionNodes;



    public static void main(String[] args) {
        launch(args);
    }

    void loadData() throws IOException{
        //Initialise

        dataNodes = new ArrayList<>();
        collectionNodes = new ArrayList<>();

        //Gets XML file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        file = fileChooser.showOpenDialog(null);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document d = documentBuilder.parse(file);

            //sets root element
            Element root = d.getDocumentElement();
            //sets link elements
            Node collectionOfLinks = root.getElementsByTagName("links").item(0);
            //sets data node elements
            Node nodes = root.getElementsByTagName("nodes").item(0);
            //Converts XML data nodes into objects
            PopulateNodes(nodes);
            PopulateLinks(collectionOfLinks);

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

    public static void PopulateLinks(Node collection){
        NodeList sel = collection.getChildNodes();
        LinkNode temp = null;

        for (int i = 0; i < sel.getLength(); i++) {
            if(sel.item(i).getNodeType() == Node.ELEMENT_NODE){
                temp = new Subnodes.Collection(sel.item(i).getChildNodes(),sel.item(i).getAttributes(),dataNodes);
                collectionNodes.add(temp);
            }
        }
    }

    public static void InitialiseTree(TreeView<LinkNode> tree){
        for (LinkNode c :collectionNodes) {
            if (c.getDict().containsKey("id") && c.get("id").equals("1")){
                tree.setRoot(new TreeItem<>(c.getChildren().get(0)));
                InitialiseBranches(tree.getRoot());
            }
        }
    }

    public static void InitialiseBranches(TreeItem<LinkNode> treeItem){
        TreeItem temp;
        for (LinkNode c :collectionNodes) {
            if (c.get("ref").equals(treeItem.getValue().get("ref"))){
                for ( LinkNode l :c.getChildren()) {
                    temp = new TreeItem<>(l);
                    treeItem.getChildren().add(temp);
                    InitialiseBranches(temp);
                }
            }
        }
    }

    public static void InitialiseProperties(LinkNode item, TableColumn<Map.Entry<String, String>, String> property,TableColumn<Map.Entry<String, String>, String> argument,TableView<Map.Entry<String, String>> tblvProperties){

        Map<String,String> map = item.get_node().getDict();

        property.setId("Key");
        property.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });

        argument.setId("Value");
        argument.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                return new SimpleStringProperty(p.getValue().getValue());
            }
        });

        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(map.entrySet());
        tblvProperties.setItems(items);
        System.out.println(tblvProperties.isVisible() + " " + property.isVisible() + " " + argument.isVisible());
    }

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
