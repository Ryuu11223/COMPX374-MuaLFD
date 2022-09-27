package sample;

public class DataNode {
    private String _id,_desc, _media, _title,_print,_search_ignore,_header,_subtitle,_cost,_thumb, _print_queue_location, _useScrubber;
    private int _maxzoom;
    public DataNode() {

    }

    public String get_useScrubber() {
        return _useScrubber;
    }
    public void set_useScrubber(String useScrubber) {
        this._useScrubber =useScrubber;
    }

    public String get_print_queue_location() {
        return _print_queue_location;
    }
    public void set_print_queue_location(String print_queue_location) {
        this._print_queue_location = print_queue_location;
    }

    public String get_desc() {
        return _desc;
    }
    public void set_desc(String desc) {
        this._desc = desc;
    }

    public String get_media() {
        return _media;
    }
    public void set_media(String media) {
        this._media = media;
    }

    public String get_title() {
        return _title;
    }
    public void set_title(String title) {
        this._title = title;
    }

    public String get_print() {
        return _print;
    }
    public void set_print(String print) {
        this._print = print;
    }

    public String get_search_ignore() {
        return _search_ignore;
    }
    public void set_search_ignore(String search_ignore) {
        this._search_ignore = search_ignore;
    }

    public String get_header() {
        return _header;
    }
    public void set_header(String header) {
        this._header = header;
    }

    public String get_subtitle() {
        return _subtitle;
    }
    public void set_subtitle(String subtitle) { 
        this._subtitle = subtitle;
    }

    public String get_cost() {
        return _cost;
    }
    public void set_cost(String cost) {
        this._cost = cost;
    }

    public String get_thumb() {
        return _thumb;
    }
    public void set_thumb(String thumb) {
        this._thumb = thumb;
    }

    public int get_maxzoom() {
        return _maxzoom;
    }
    public void set_maxzoom(String maxzoom) {
        this._maxzoom = Integer.parseInt(maxzoom);
    }

    public String get_id() {
        return _id;
    }
    public void set_id(String id) {
        this._id = id;
    }
}
