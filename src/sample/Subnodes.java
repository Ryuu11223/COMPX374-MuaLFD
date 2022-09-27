package sample;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class Subnodes {
    public static class Image extends DataNode{
        public Image(NamedNodeMap nm) {
            super(nm);
        }

        @Override
        public String toString() {
            return "Image".toString();
        }
    }

    public static class PrintSetting extends DataNode{

        public PrintSetting(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "PrintSettings".toString();
        }
    }

    public static class Print extends DataNode{
        public Print(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "Print".toString();
        }
    }

    public static class Welcome extends DataNode{
        public Welcome(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "Welcome".toString();
        }
    }

    public static class Tiles extends DataNode{
        public Tiles(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "Tiles".toString();
        }
    }

    public static class Map extends DataNode{
        public Map(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "Map".toString();
        }
    }

    public static class PDF extends DataNode{
        public PDF(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "PDF".toString();
        }
    }

    public static class Video extends DataNode{
        public Video(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "Video".toString();
        }
    }

    public static class Audio extends DataNode{
        public Audio(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "Audio".toString();
        }
    }

    public static class Presentation extends DataNode{
        public Presentation(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "Presentation".toString();
        }
    }

    public static class SortedList extends DataNode{
        public SortedList(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "Sorted List".toString();
        }
    }

    public static class List extends DataNode{
        public List(NamedNodeMap nm) {
            super(nm);
        }
        @Override
        public String toString() {
            return "List".toString();
        }
    }

    public static class Collection extends LinkNode{

        public Collection(NodeList children, NamedNodeMap attributes, java.util.List<DataNode> nodeList) {
            java.util.List<DataNode> temp;

            for (int i = 0; i < attributes.getLength(); i++) {
                this.getDict().put(attributes.item(i).getNodeName(),attributes.item(i).getNodeValue());
            }
            this.set_node(findDataNode(this.getDict().get("ref"),nodeList));

            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i).getNodeType() == Node.ELEMENT_NODE){
                    this.addChildren(new Link(children.item(i).getAttributes(),nodeList));
                    System.out.println("Child " + i +": " + new Link(children.item(i).getAttributes(),nodeList).toString());
                    for (int j = 0; j < children.item(i).getAttributes().getLength(); j++) {
                        System.out.println(children.item(i).getAttributes().item(j).getNodeName()+":"+children.item(i).getAttributes().item(j).getNodeValue());
                    }

                }
            }
            System.out.println(" Length: " + children.getLength());
        }

    }

    public static class Link extends LinkNode{
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
