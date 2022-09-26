package sample;

public class Subnodes {
    public static class Image extends DataNode{
        public Image(String id, String title, String media) {
            set_id(id);
            set_title(title);
            set_media(media);
        }
        public Image(String id, String title, String print, String desc, String media) {
            set_id(id);
            set_title(title);
            set_desc(desc);
            set_media(media);
            set_print(print);
        }
        public Image(String id, String title, String desc, String media) {
            set_id(id);
            set_title(title);
            set_desc(desc);
            set_media(media);
        }
        public Image(String id, String title, String media, String print,int s,int a) {
            set_id(id);
            set_title(title);
            set_desc(print);
            set_media(media);
        }
    }

    public static class PrintSetting extends DataNode{
        public PrintSetting(String desc, String print_queue_location) {
            set_desc(desc);
            set_print_queue_location(print_queue_location);
        }
    }

    public static class Print extends DataNode{
        public Print(String title, String cost, String desc) {
            set_desc(desc);
            set_title(title);
            set_cost(cost);
        }
    }

    public static class Welcome extends DataNode{
        public Welcome(String id, String title, String search_ignore, String header, String subtitles) {
            set_id(id);
            set_title(title);
            set_search_ignore(search_ignore);
            set_header(header);
            set_subtitle(subtitles);
        }
    }

    public static class Tiles extends DataNode{
        public Tiles(String id, String title, String desc) {
            set_id(id);
            set_title(title);
            set_desc(desc);
        }
    }

    public static class Map extends DataNode{
        public Map(String id, String title, String media, String maxzoom) {
            set_id(id);
            set_title(title);
            set_media(media);
            set_maxzoom(maxzoom);
        }
    }

    public static class PDF extends DataNode{
        public PDF(String id, String title, String media, String thumb) {
            set_id(id);
            set_title(title);
            set_media(media);
            set_thumb(thumb);
        }
    }

    public static class Video extends DataNode{
        public Video(String id, String desc, String title, String media, String thumb, String useScrubber) {
            set_id(id);
            set_desc(desc);
            set_title(title);
            set_media(media);
            set_thumb(thumb);
            set_useScrubber(useScrubber);
        }
    }

    public static class Audio extends DataNode{
        public Audio(String id, String title, String media, String thumb) {
            set_id(id);
            set_title(title);
            set_media(media);
            set_thumb(thumb);
        }
    }

    public static class Presentation extends DataNode{
        public Presentation(String id, String title, String desc,String media, String thumb) {
            set_id(id);
            set_title(title);
            set_desc(desc);
            set_media(media);
            set_thumb(thumb);
        }
    }

    public static class SortedList extends DataNode{
        public SortedList(String id, String title, String desc,String media) {
            set_id(id);
            set_title(title);
            set_desc(desc);
            set_media(media);
        }
    }

    public static class List extends DataNode{
        public List(String id, String title, String desc,String media) {
            set_id(id);
            set_title(title);
            set_desc(desc);
            set_media(media);
        }
    }
}
