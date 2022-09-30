package sample;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

public abstract class LinkNode {

    private DataNode _node;
    private final java.util.List<LinkNode> _children = new ArrayList<>();
    private final Map<String,String> _attr = new HashMap<>();

    public LinkNode() {
    }

    public List<LinkNode> getChildren() {
        return _children;
    }
    public void addChildren(LinkNode children) {
        _children.add(children);
    }

    public DataNode get_node() {
        return _node;
    }
    public void set_node(DataNode _node) {
        this._node = _node;
    }


    public void set(String key, String value)
    {
        if (_attr.containsKey(key)){
            _attr.replace(key, value);
        }
    }
    public String get(String key)
    {
        return _attr.get(key);
    }

    public Map<String, String> getDict() {
        return _attr;
    }
    public int attrSize() {
        return _attr.size();
    }

    public void printAttributes() {
        _attr.forEach((k,v) -> System.out.print(k+" : "+v + ", "));
        System.out.println("");
    }

    public static DataNode findDataNode(String ref, List<DataNode> nodeList){
        for (DataNode n : nodeList) {
            if (ref.equals(n.get("id"))){
                return n;
            }
        }
        return null;
    }
}
