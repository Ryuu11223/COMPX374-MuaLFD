package sample;

import java.util.*;

public abstract class LinkNode {

    private DataNode _node;
    private LinkNode _link;
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
        _attr.put(key, value);
    }
    public String get(String key)
    {
        return _attr.get(key);
    }

    public LinkNode getLink() {
        return _link;
    }

    public void setLink(LinkNode link) {
        this._link = link;
    }

    public Map<String, String> getDict() {
        return _attr;
    }


    public static DataNode findDataNode(String ref, List<DataNode> nodeList){
        for (DataNode n : nodeList) {
            if (ref.equals(n.get("id"))){
                return n;
            }
        }
        return null;
    }

    public void remove(LinkNode l){
        _children.remove(l);
    }
}
