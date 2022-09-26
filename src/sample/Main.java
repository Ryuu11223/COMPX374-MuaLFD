package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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


    public static void main(String[] args) {
        launch(args);
    }

    void loadData() throws IOException{
        //Initialise
        dataNodes = new ArrayList<>();

        //Gets XML file
        FileChooser fileChooser = new FileChooser();
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
            //Checks if the element is a TEXT or ElEMENT type
            if (nl.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                //Gets all attributes of child node
                nm = nl.item(i).getAttributes();

                //Creates a data node object depending on the type of tag
                switch (nl.item(i).getNodeName()){
                    case "printsettings":
                        temp = new Subnodes.PrintSetting(attrValue(nm, "desc"),attrValue(nm, "print.queue.location"));
                        break;
                    case "print":
                        temp = new Subnodes.Print(attrValue(nm, "title"),attrValue(nm, "cost"),attrValue(nm, "desc"));
                        break;
                    case "welcome":
                        temp = new Subnodes.Welcome(attrValue(nm, "id"),attrValue(nm, "title"), attrValue(nm, "search.ignore"), attrValue(nm, "header"),attrValue(nm, "subtitle"));
                        break;
                    case "tiles":
                        temp = new Subnodes.Tiles(attrValue(nm, "id"),attrValue(nm, "title"),attrValue(nm, "desc"));
                        break;
                    case "map":
                        temp = new Subnodes.Map(attrValue(nm, "id"),attrValue(nm, "title"),attrValue(nm, "media"),attrValue(nm, "maxzoom"));
                        break;
                    case "pdf":
                        temp = new Subnodes.PDF(attrValue(nm, "id"),attrValue(nm, "title"),attrValue(nm, "media"),attrValue(nm, "thumb"));
                        break;
                    case "video":
                        temp = new Subnodes.Video(attrValue(nm, "id"),attrValue(nm, "desc"),attrValue(nm, "title"),attrValue(nm, "media"),attrValue(nm, "thumb"),attrValue(nm, "useScrubber"));
                        break;
                    case "audio":
                        temp = new Subnodes.Audio(attrValue(nm, "id"),attrValue(nm, "title"),attrValue(nm, "media"),attrValue(nm, "thumb"));
                        break;
                    case "presentation":
                        temp = new Subnodes.Presentation(attrValue(nm, "id"),attrValue(nm, "title"),attrValue(nm, "desc"),attrValue(nm, "media"),attrValue(nm, "thumb"));
                        break;
                    case "SortedList":
                        temp = new Subnodes.SortedList(attrValue(nm, "id"),attrValue(nm, "title"),attrValue(nm, "desc"),attrValue(nm, "media"));
                        break;
                    case "list":
                        temp = new Subnodes.List(attrValue(nm, "id"),attrValue(nm, "title"),attrValue(nm, "desc"),attrValue(nm, "media"));
                        break;
                    //Image tag is special case because there are different combinations and arrangements of attributes
                    case "img":
                        if (nm.getLength() == 3){
                            temp = new Subnodes.Image(attrValue(nm, "id"),attrValue(nm, "title"),attrValue(nm, "media"));
                        } else if (nm.getLength() == 4) {
                            if (nm.getNamedItem("print") == null){
                                temp = new Subnodes.Image(attrValue(nm, "id"),attrValue(nm, "title"),attrValue(nm, "desc"),attrValue(nm, "media"),0,0);
                            }
                            else {
                                temp = new Subnodes.Image(attrValue(nm, "id"),attrValue(nm, "title"),attrValue(nm, "media"),attrValue(nm, "print"));
                            }
                        }
                        else {
                            temp = new Subnodes.Image(attrValue(nm, "id"),attrValue(nm, "title"),attrValue(nm, "print"),attrValue(nm, "desc"),attrValue(nm, "media"));
                        }
                        break;
                }
                //Add the created node
                dataNodes.add(temp);
                System.out.println(temp.toString());
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
