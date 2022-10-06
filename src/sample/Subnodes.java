package sample;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class Subnodes{

    public static class Image extends DataNode{

        public Image(NamedNodeMap nm) {
            super(nm);
        }

        public Image() {}

        @Override
        public String toString() {
            return "img".toString();
        }
    }

    public static class PrintSetting extends DataNode{

        public PrintSetting(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "printsettings".toString();
        }
    }

    public static class Print extends DataNode{
        public Print(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "print".toString();
        }
    }

    public static class Welcome extends DataNode{
        public Welcome(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "welcome".toString();
        }
    }

    public static class Tiles extends DataNode{
        public Tiles(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "tiles".toString();
        }
    }

    public static class Map extends DataNode{
        public Map(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "map".toString();
        }
    }

    public static class PDF extends DataNode{
        public PDF(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "pdf".toString();
        }
    }

    public static class Video extends DataNode{
        public Video(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "video".toString();
        }
    }

    public static class Audio extends DataNode{
        public Audio(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "audio".toString();
        }
    }

    public static class Presentation extends DataNode{
        public Presentation(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "presentation".toString();
        }
    }

    public static class SortedList extends DataNode{
        public SortedList(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "SortedList".toString();
        }
    }

    public static class List extends DataNode{
        public List(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "list".toString();
        }
    }

    public static class Collection extends LinkNode{

        public Collection(NodeList children, NamedNodeMap attributes, java.util.List<DataNode> nodeList) {
            java.util.List<DataNode> temp;

            //sets attributes to link
            for (int i = 0; i < attributes.getLength(); i++) {
                this.getDict().put(attributes.item(i).getNodeName(),attributes.item(i).getNodeValue());
            }
            //gets node for the item
            this.set_node(findDataNode(this.getDict().get("ref"),nodeList));

            //
            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i).getNodeType() == Node.ELEMENT_NODE){
                    this.addChildren(new Link(children.item(i).getAttributes(),nodeList));
                }
            }
        }


    }

    public static class Link extends LinkNode{
        public Link() {}

        public Link(NamedNodeMap attributes, java.util.List<DataNode> nodeList) {
            for (int i = 0; i < attributes.getLength(); i++) {
                this.getDict().put(attributes.item(i).getNodeName(),attributes.item(i).getNodeValue());
            }
            this.set_node(findDataNode(this.getDict().get("ref"),nodeList));
        }
        @Override
        public String toString() {
            return this.get_node().toString();
        }
    }
}
