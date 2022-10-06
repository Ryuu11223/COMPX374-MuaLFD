package sample;

import org.w3c.dom.NamedNodeMap;

import java.util.HashMap;
import java.util.Map;

public class DataNode {
    private final Map<String,String> _attr = new HashMap<>();

    public DataNode(NamedNodeMap nm) {
        for (int i = 0; i < nm.getLength(); i++) {
            _attr.put(nm.item(i).getNodeName(),nm.item(i).getNodeValue());
        }
    }

    public DataNode() {
    }

    public void set(String key, String value)
    {
        if (_attr.containsKey(key)){
            _attr.put(key, value);
        }
    }
    public String get(String key) {
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

    //_id,_desc, _media, _title,_print,_search_ignore,_header,_subtitle,_cost,_thumb, _print_queue_location, _useScrubber,_maxzoom
}
