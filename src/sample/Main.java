package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.*;
import javafx.util.Callback;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
        primaryStage.setTitle("Backend Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    //// GLOBAL VARIABLE
    static  Media previewMedia;
    static TreeItem<LinkNode> item;
    static TableColumn<Map.Entry<String, String>, String> property;
    static TableColumn<Map.Entry<String, String>, String> argument;
    static TableColumn<Map.Entry<String, String>, Void> buttonColumn;
    static TableView<Map.Entry<String, String>> tblvProperties;

    //// SETTERS ////
    public void setItem(TreeItem<LinkNode> item) {
        Main.item = item;
    }
    public void setProperty(TableColumn<Map.Entry<String, String>, String> property) {
        Main.property = property;
    }
    public void setArgument(TableColumn<Map.Entry<String, String>, String> argument) {
        Main.argument = argument;
    }
    public void setButtonColumn(TableColumn<Map.Entry<String, String>, Void> buttonColumn) {
        Main.buttonColumn = buttonColumn;
    }
    public void setTblvProperties(TableView<Map.Entry<String, String>> tblvProperties) {
        Main.tblvProperties = tblvProperties;
    }

    File file;
    static File directory;
    static List<DataNode> dataNodes;
    static List<LinkNode> collectionNodes;
    static LinkNode tiles;

    static HashMap<String, Integer> refCount;


    public static void main(String[] args) {
        launch(args);
    }

    //// INITIALISE ////
    static void PopulateNodes(Node node){
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

    static void PopulateLinks(Node collection){
        NodeList sel = collection.getChildNodes();
        LinkNode temp;
        refCount = new HashMap<>();

        for (int i = 0; i < sel.getLength(); i++) {
            if(sel.item(i).getNodeType() == Node.ELEMENT_NODE){
                temp = new Subnodes.Collection(sel.item(i).getChildNodes(),sel.item(i).getAttributes(),dataNodes);
                collectionNodes.add(temp);
            }
        }
        //Sets a hardcoded path to tiles sel
        tiles = collectionNodes.get(1);

        for ( LinkNode c :collectionNodes) {
            c.setLink(findLink(tiles.getChildren(), i -> i.get("ref").equals(c.get("ref"))));
            for (LinkNode l:c.getChildren()) {
                int count = refCount.getOrDefault(l.get("ref"), 0);
                refCount.put(l.get("ref"), count + 1);
            }
        }
    }

    void loadData() throws IOException{
        //Initialise
        dataNodes = new ArrayList<>();
        collectionNodes = new ArrayList<>();

        //Gets XML file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        fileChooser.setInitialDirectory(directory);
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
        }
    }

    void setDirectory() throws IOException{
        //Gets Set Directory
        DirectoryChooser fileChooser = new DirectoryChooser();
        directory = fileChooser.showDialog(null);
    }


    //// TABLE MANIPULATION ////
    static void InitialiseProperties(LinkNode item, TableColumn<Map.Entry<String, String>, String> property,TableColumn<Map.Entry<String, String>, String> argument, TableColumn<Map.Entry<String, String>, Void> buttonColumn, TableView<Map.Entry<String, String>> tblvProperties){
        //Gets attribute map
        Map<String,String> map = null;
        for (DataNode n: dataNodes) {
            if (n.equals(item.get_node())){
                map = n.getDict();
            }
        }


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

        //Adds Buttons to last column
        buttonColumn.setCellFactory(new Callback<TableColumn<Map.Entry<String, String>, Void>, TableCell<Map.Entry<String, String>, Void>>() {
            @Override
            public TableCell<Map.Entry<String, String>, Void> call(TableColumn<Map.Entry<String, String>, Void> entryTableColumn) {
                return new TableCell<Map.Entry<String, String>, Void>() {
                    private final Button btn = new Button("...");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            try {
                                expandArgument(this.getTableRow().getItem().getKey(),this.getTableRow().getItem().getValue(),item);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item1, boolean empty) {
                        super.updateItem(item1, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        });


        //Sets all items of node into table view
        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(map.entrySet());
        tblvProperties.setItems(items);

        //Makes the argument column editable. Must double-click.
        tblvProperties.setEditable(true);
        argument.setCellFactory(TextFieldTableCell.forTableColumn());
        property.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    static void propertyChange(String currKey, String newKey, LinkNode item) {
        if(item.get_node().getDict().containsKey(newKey)){
            popup("This key aleady exists!",Alert.AlertType.WARNING,"Warning!");
            return;
        }
        if (currKey == null){
            for (DataNode n: dataNodes ) {
                if (n.equals(item.get_node())){
                    n.set(newKey,"");
                    return;
                }
            }
        }
        if(currKey.equals("id")){
            popup("The field id cannot be edited as this may cause issues with the references",Alert.AlertType.WARNING,"Warning!");
            return;
        }
        for (LinkNode n : collectionNodes) {
            for (LinkNode k: n.getChildren()) {
                if(k.get_node().equals(item.get_node())){
                    k.get_node().set(newKey,k.get_node().get(currKey));
                    k.get_node().getDict().remove(currKey);

                }
            }
        }



    }

    static void argumentChange(String key, String value, LinkNode item) {

        if (key.equals("id"))
            try {
                Integer.parseInt(value);
                for (DataNode d: dataNodes) {
                    if (d.get("id") != null){
                        if (d.get("id").equals(value) || value.equals("1")) {
                            popup("This id Already exists", Alert.AlertType.WARNING, "Warning!");
                            return;
                        }
                    }
                }

                refCount.put(item.get_node().get("id"),refCount.get(item.get_node().get("id"))-1);
                if(refCount.containsKey(value)){

                    refCount.put(value, refCount.get(value) + 1);
                }
                else{
                    refCount.put(value,1);
                }
            }
            catch (Exception ex){
                popup("This id value can only be an integer", Alert.AlertType.WARNING, "Warning!");
                return;
            }

        for (LinkNode n : collectionNodes) {
            for (LinkNode k: n.getChildren()) {
                if (k.get_node().equals(item.get_node())) {
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


    }

    static void expandArgument(String key, String value, LinkNode item) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("editor.fxml"));
        Parent root = loader.load();

        EditorController editorController = loader.getController();
        editorController.setItem(item);
        editorController.setKey(key);
        editorController.setValue(value);

        Stage stage = new Stage();
        stage.setTitle(key);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        refreshTable();
    }

    //// TREE MANIPULATION ////
    static void initialiseTree(TreeView<LinkNode> tree){
        //Initialise tree and root method
        tree.setRoot(new TreeItem<LinkNode>((Objects.requireNonNull(findLink(collectionNodes, i -> i.getDict().containsKey("id") && i.get("id").equals("1")))).getChildren().get(0)));
        initialiseBranches(tree.getRoot());
    }

    static void initialiseBranches(TreeItem<LinkNode> treeItem){
        //Initialise branches of the branch that is passed into method
        TreeItem<LinkNode> temp;
        for (LinkNode c :collectionNodes) {
            if (c.get("ref").equals(treeItem.getValue().get("ref"))){
                for ( LinkNode l :c.getChildren()) {
                    temp = new TreeItem<LinkNode>(l);
                    treeItem.getChildren().add(temp);
                    initialiseBranches(temp);
                }
            }
        }
    }
    static void addElem(LinkNode node, TreeItem<LinkNode> item, DataNode type){
        ArrayList<String> claus = new ArrayList<>(Arrays.asList("img", "pdf", "video", "audio"));
        if (claus.contains(item.getValue().get_node().toString())) {
            popup("You cannot add to this Node", Alert.AlertType.WARNING, "Warning!");
            return;
        }
        dataNodes.add(type);
        setID(type);
        LinkNode link = new Subnodes.Link();
        link.set_node(type);
        link.set("ref", type.get("id"));
        node.getChildren().add(link);
        item.getChildren().add(new TreeItem<LinkNode>(link));
        if (!claus.contains(type.toString())) {
            LinkNode temp = new Subnodes.Collection();
            temp.set("ref", type.get("id"));
            temp.set_node(type);
            collectionNodes.add(temp);
        }
    }

    public void deleteEvent(TreeItem<LinkNode> item){

        if(item.getChildren().isEmpty()){
            item.getParent().getChildren().remove(item);
            updateDataNodes(item.getValue().get_node());
            Objects.requireNonNull(findLink(collectionNodes, i -> i.getChildren().contains(item.getValue()))).remove(item.getValue());
        }
        else {
            Optional<ButtonType> result = popup("This collection contains child elements. Are you sure you want to delete?", Alert.AlertType.CONFIRMATION, "Warning!");
            if(result.isEmpty() || result.get() == ButtonType.OK) {
                item.getParent().getChildren().remove(item);
                LinkNode s = findLink(collectionNodes, i -> i.get("ref").equals(item.getValue().get("ref")));
                assert s != null;
                for (LinkNode l :s.getChildren()) {
                    updateDataNodes(l.get_node());
                }
                updateDataNodes(s.get_node());
                tiles.remove(s.getLink());
                collectionNodes.remove(s);
            }
        }
    }

    public void moveTreeItem(TreeItem<LinkNode> item, String action){
        try {
            List<LinkNode> parentList = Objects.requireNonNull(findLink(collectionNodes, l -> l.get("ref").equals(item.getParent().getValue().get("ref")))).getChildren();
            LinkNode selectedItem = findLink(parentList,l -> l.get("ref").equals(item.getValue().get("ref")));
            int j = parentList.indexOf(selectedItem);

            if (action.equals("up") && !(j == 0)){
                Collections.swap(parentList, j, j - 1);
                Collections.swap(item.getParent().getChildren(), j, j - 1);
            }
            else if(action.equals("down") && !(j == parentList.size())){
                Collections.swap(parentList, j, j + 1);
                Collections.swap(item.getParent().getChildren(), j, j + 1);
            }
            else {
                popup("Item cannot move any further", Alert.AlertType.ERROR,"Error");
            }

        }
        catch (Exception ex){
        }
    }


    //// QUALITY OF LIFE METHODS ////
    static void findPos(TreeItem<LinkNode> item, DataNode type) {
        if(item == null) {
            popup("Please select a node to add to", Alert.AlertType.WARNING,"Warning");
            return;

        }
        for (LinkNode n : collectionNodes) {
            if (n.get_node().equals(item.getValue().get_node())) {
                addElem(n, item, type);
                return;
            }
        }
        for (LinkNode k : tiles.getChildren()) {
            if (k.get_node().equals(item.getValue().get_node())) {
                addElem(k, item, type);
                return;
            }
        }
    }
    static LinkNode findLink(List<LinkNode> list, Predicate<LinkNode> condition){
        for (LinkNode i : list) {
            if (condition.test(i)){
                return i;
            }
        }
        return null;
    }

    static void updateDataNodes(DataNode node){
        int count = refCount.get(node.get("id"));
        refCount.put(node.get("id"), count - 1);
        if (count == 1){
            refCount.remove(node.get("id"));
            dataNodes.remove(node);
        }
    }

    static void setID(DataNode node) {
        int i = 3;
        while (true) {
            i++;
            if (!(refCount.containsKey(String.valueOf(i)))) {
                refCount.put(String.valueOf(i), 1);
                node.set("id", String.valueOf(i));
                return;
            }
        }
    }

    public static void refreshTable(){
        InitialiseProperties(item.getValue(),property,argument,buttonColumn,tblvProperties);
    }

    //Popup method
    static Optional<ButtonType> popup(String info, Alert.AlertType type, String title) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(info);
        return a.showAndWait();
    }

    static  String preview(TreeItem<LinkNode> item, MediaView mediaView, ImageView imageView) throws IOException {
        try {
            String type;
            File previewFile;

            if (mediaView.getMediaPlayer() != null){
                mediaView.getMediaPlayer().dispose();
            }
            mediaView.setMediaPlayer(null);
            imageView.setImage(null);

            if (item.getValue().get_node().getDict().containsKey("media")){

                previewFile = new File(directory.getAbsoluteFile()+"\\Mua.Unity_Data\\StreamingAssets\\"+item.getValue().get_node().get("media"));
                type = Files.probeContentType(Path.of(previewFile.getPath())).split("/")[0];

                if (type.equals("image")){
                    imageView.setImage(new Image(previewFile.getAbsolutePath()));
                    return null;
                } else if (type.equals("video")) {
                    previewMedia = new Media(previewFile.toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(previewMedia);
                    assert false;
                    mediaView.setMediaPlayer(mediaPlayer);
                    mediaPlayer.play();
                    return null;
                }
            }
            return  "Preview unavailable.";
        }
        catch (Exception ex){
            return "Unable to preview. Please make sure you have set the MUA file directory.";
        }
    }


    //// EXPORT SECTION ////
    public void exportFile() {
        if (file == null) {
            popup("No XML has been loaded to export!", Alert.AlertType.WARNING, "Warning!");
            return;
        }

        try {
            Document document = createExportFile();

            if (document == null) {
                Optional<ButtonType> result = popup("This XML Data Cannot Be Generated Into A File", Alert.AlertType.WARNING, "Warning!");
                return;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(directory);
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
            setAttr(document, locale2, "title", "M??ori");
            setAttr(document, locale2, "media", "table/mao_flag.png");

            Element alt = quickAddNode(document, locale2, "alt", "ref", "2");
            setAttr(document, alt, "title", "Mua Whakaaturanga Whakamahinga");
            setAttr(document, alt, "desc", "");

            Element alt2 = quickAddNode(document, locale2, "alt", "ref", "3");
            setAttr(document, alt2, "title", "Ng?? kohikohinga me nga whakaaturanga");
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
}
