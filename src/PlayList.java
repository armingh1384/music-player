import java.util.ArrayList;
import java.util.List;

public class PlayList {
    private List<Song> songs;
    private int size;
    private String playlistname;
    public String getName(){
        return playlistname;
    }
    public void setName(String name){
        this.playlistname = name;
    }

    public PlayList() {
        this.songs = new ArrayList<>();
    }

    public PlayList(int size) {
        this.songs = new ArrayList<>(size);
    }

    public List<Song> getSongs() {
        return songs;
    }

    public int getNumberOfSongs() {
        return songs.size();
    }

    public boolean addSong(Song song) {
        return songs.add(song);
    }

    public boolean removeSong(Song song) {
        return songs.remove(song);
    }

    public PlayList filter(Filter filter) {
        PlayList filteredPlayList = new PlayList();
        for (Song song : songs) {
            if (filter.accept(song)) {
                filteredPlayList.addSong(song);
            }
        }
        return filteredPlayList;
    }

    public Object[] collectData(DataCollector collector) {
        Object[] data = new Object[getNumberOfSongs()];
        int index = 0;
        for (Song song : songs) {
            data[index++] = collector.get(song);
        }
        return data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}