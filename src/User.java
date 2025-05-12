import java.util.ArrayList;
import java.util.List;

public class User {
    String username;
    String password;
    String email;
    List<PlayList> playlists;
    List<Song> songs;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.playlists = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    void addplaylist(PlayList playlist){
        if (playlists == null) {
            playlists = new ArrayList<>();
        }
        playlists.add(playlist);
    }

    void addsong_toplaylist(PlayList playlist, Song song){
        if (playlists != null && playlists.contains(playlist)) {
            playlist.addSong(song);
            if (songs == null) {
                songs = new ArrayList<>();
            }
            if (!songs.contains(song)) {
                songs.add(song);
            }
        }
    }

    void removeplaylist(PlayList playlist){
        if (playlists != null) {
            playlists.remove(playlist);
        }
    }

    void removesong_fromplaylist(PlayList playlist, Song song){
        if (playlists != null && playlists.contains(playlist)) {
            playlist.removeSong(song);
            if (songs != null) {
                boolean songInOtherPlaylists = false;
                for (PlayList pl : playlists) {
                    Song[] plSongs = pl.getSongs();
                    for (Song sng : plSongs) {
                        if (sng != null && sng.equals(song)) {
                            songInOtherPlaylists = true;
                            break;
                        }
                    }
                    if (songInOtherPlaylists) break;
                }
                if (!songInOtherPlaylists) {
                    songs.remove(song);
                }
            }
        }
    }

    void likesong(Song s){
        if(!s.isliked){
        s.isliked = true;
        s.setCountoflikes(s.getCountoflikes() + 1);
    }}
void dislikesong(Song s){
        if(s.isliked){
            s.isliked = false;
            s.setCountoflikes(s.getCountoflikes() - 1);
        }

}


}

