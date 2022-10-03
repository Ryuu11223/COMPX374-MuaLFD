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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
        //Initialise tree and root method
        for (LinkNode c :collectionNodes) {
            if (c.getDict().containsKey("id") && c.get("id").equals("1")){
                tree.setRoot(new TreeItem<>(c.getChildren().get(0)));
                InitialiseBranches(tree.getRoot());
            }
        }
    }

    public static void InitialiseBranches(TreeItem<LinkNode> treeItem){
        //Initialise branches of the branch that is passed into method
        TreeItem<LinkNode> temp;
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

        //Gets attribute map
        Map<String,String> map = item.get_node().getDict();

        //Overwrite get method to extract from Map
        property.setId("Key");
        property.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });

        //Overwrite get method to extract from Map
        argument.setId("Value");
        argument.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                return new SimpleStringProperty(p.getValue().getValue());
            }
        });

        //Sets all items of node into table view
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(map.entrySet());
        tblvProperties.setItems(items);

        //Makes the argument column editable. Must double-click.
        tblvProperties.setEditable(true);
        argument.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    //Popup method
    static Optional<ButtonType> popup(String info, Alert.AlertType type, String title) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(info);
        return a.showAndWait();
    }


    public void deleteEvent(TreeItem<LinkNode> item){


        if(item.getChildren().isEmpty()) {
            for (LinkNode l:collectionNodes) {
                //System.out.println("in collection? "+l.getChildren().contains(item.getValue()));
                DataNode temp= l.removeChild(item.getValue());
                if(dataNodes.contains(temp)){
                    //System.out.println("in collection? "+l.getChildren().contains(item.getValue()));
                    //System.out.println("in nodes? "+dataNodes.contains(temp));
                    dataNodes.remove(temp);
                    //System.out.println("in nodes? "+dataNodes.contains(temp));
                    item.getParent().getChildren().remove(item);
                    return;
                }
            }
        }
        else{
            Optional<ButtonType> result = popup("This collection contains child elements. Are you sure you want to delete?", Alert.AlertType.CONFIRMATION, "Warning!");
            if(result.isEmpty() || result.get() == ButtonType.OK) {
                for (LinkNode l:collectionNodes) {
                    //System.out.println("in collection? "+l.getChildren().contains(item.getValue()));
                    DataNode temp= l.removeChild(item.getValue());
                    if(dataNodes.contains(temp)){
                        //System.out.println("in collection? "+l.getChildren().contains(item.getValue()));
                        //System.out.println("in nodes? "+dataNodes.contains(temp));
                        dataNodes.remove(temp);
                        //System.out.println("in nodes? "+dataNodes.contains(temp));
                        item.getParent().getChildren().remove(item);
                        return;
                    }
                }
            }
        }
    }

    public void exportFile() {
        if (file == null) {
            popup("No XML has been loaded to export!", Alert.AlertType.CONFIRMATION, "Warning!");
            return;
        }

        try {
            Document document = createExportFile();

            if (document == null) {
                Optional<ButtonType> result = popup("This XML Data Cannot Be Generated Into A File", Alert.AlertType.CONFIRMATION, "Warning!");
                return;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.setInitialFileName("DemoRoot.xml");

            File userSelection = fileChooser.showSaveDialog(null);

            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));

            StreamResult result = new StreamResult(userSelection);
            transformer.transform(source, result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Document createExportFile() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder bBuilder = dbFactory.newDocumentBuilder();
            Document document = bBuilder.newDocument();

            Element rootElement = document.createElement("root");
            document.appendChild(rootElement);

            Element linksElement = document.createElement("links");
            rootElement.appendChild(linksElement);

            Element nodesElement = document.createElement("nodes");
            rootElement.appendChild(nodesElement);

            Element nodesLocal = document.createElement("localization");
            setAttr(document, nodesLocal, "default", "eng");
            rootElement.appendChild(nodesLocal);

            Element locale = quickAddNode(document, nodesLocal, "locale", "id", "eng");
            setAttr(document, locale, "title", "English");
            setAttr(document, locale, "media", "table/us_flag.png");

            Element locale2 = quickAddNode(document, nodesLocal, "locale", "id", "mri/mao");
            setAttr(document, locale2, "title", "Māori");
            setAttr(document, locale2, "media", "table/mao_flag.png");

            Element alt = quickAddNode(document, locale2, "alt", "ref", "2");
            setAttr(document, alt, "title", "Mua Whakaaturanga Whakamahinga");
            setAttr(document, alt, "desc", "");

            Element alt2 = quickAddNode(document, locale2, "alt", "ref", "3");
            setAttr(document, alt2, "title", "Ngā kohikohinga me nga whakaaturanga");
            setAttr(document, alt2, "desc", "");

            addNodes(document, nodesElement);
            addLinks(document, linksElement);

            return document;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static void setAttr(Document doc, Element element, String type, String value){
        Attr attr = doc.createAttribute(type);
        attr.setValue(value);
        element.setAttributeNode(attr);
    }

    static Element quickAddNode(Document doc, Element parentElement, String name, String type, String value){
        Element element = doc.createElement(name);
        setAttr(doc, element, type, value);
        parentElement.appendChild(element);
        return element;
    }

    static Element quickAddNode(Document doc, Element parentElement, String name){
        Element element = doc.createElement(name);
        parentElement.appendChild(element);
        return element;
    }

    static void addNodes(Document doc, Element element){
        String key, value;
        for (DataNode x : dataNodes) {
            Element elem = quickAddNode(doc, element, x.toString());
            x.getDict().forEach((i, k) -> {
                setAttr(doc, elem, i, k);
            } );
        }
    }

    static void addLinks(Document doc, Element element) {
        for (LinkNode x : collectionNodes) {
            Element elemSel = quickAddNode(doc, element, "sel");
            x.getDict().forEach((i, k) -> {
                setAttr(doc, elemSel, i, k);
            } );
            for (LinkNode y : x.getChildren()) {
                Element elemLink = quickAddNode(doc, elemSel, "link");
                y.getDict().forEach((i, k) -> {
                    setAttr(doc, elemLink, i, k);
                } );
            }
        }
    }

    static boolean propertyChange(String key, String value, TreeItem<LinkNode> item) {
        if (key.equals("id"))
            for (DataNode d: dataNodes) {
                if (d.get("id") != null)
                    if (d.get("id").equals(value) || value.equals("1")) {
                        popup("This id Already exists", Alert.AlertType.CONFIRMATION, "Warning!");
                        return true;
                    }
            }

        for (LinkNode n : collectionNodes) {
            for (LinkNode k: n.getChildren()) {
                if (k.get_node().equals(item.getValue().get_node())) {
                    for (LinkNode i: collectionNodes) {
                        if(i.get("ref").equals(k.get("ref"))) {
                            i.set("ref", value);
                        }
                    }
                    k.get_node().set(key, value);
                    if (key.equals("id")) {
                        k.set("ref" , value);
                    }
                }
            }
        }
        return false;
    }
}
