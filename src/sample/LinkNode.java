package sample;

import java.util.List;
import java.util.Optional;

public abstract class LinkNode {
    private List<LinkNode> _children;
    private Optional<DataNode> _item;

    public LinkNode(){
    }

    public List<LinkNode> get_children() {
        return _children;
    }
    public void set_children(List<LinkNode> _children) {
        this._children = _children;
    }

    public Optional<DataNode> get_item() {
        return _item;
    }
    public void set_item(Optional<DataNode> _item) {
        this._item = _item;
    }

    public Optional<DataNode> findItem(String id, List<DataNode> dataNodes){
        return Optional.ofNullable(dataNodes.stream()
                .filter(customer -> id.equals(customer.get_id()))
                .findFirst()
                .orElse(null));
    }

}
