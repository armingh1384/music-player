import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String email;
    private List<PlayList> playlists;
    private List<Song> songs;


    public User(String username, String email, String password) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        this.username = username;
        this.password = password;
        this.email = email;
        this.playlists = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        this.username = username;
    }
    public Song findsongbyname(String name,PlayList p){
        for(Song s : p.getSongs()){
            if(s.getName().equals(s)){
                return s;
            }
        }
        return null;
    }



    public String getEmail() {
        return email;
    }
    public void setPassword(String s){
        this.password = s;
    }

    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        this.email = email;
    }

    public List<PlayList> getPlaylists() {
        return new ArrayList<>(playlists);
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public void addPlaylist(PlayList playlist) {
        if (playlist == null) {
            throw new IllegalArgumentException("Playlist cannot be null.");
        }
        playlists.add(playlist);
    }

    public void addSongToPlaylist(PlayList playlist, Song song) {
        if (playlist == null || song == null) {
            throw new IllegalArgumentException("Playlist and song cannot be null.");
        }
        if (!playlists.contains(playlist)) {
            throw new IllegalArgumentException("Playlist does not belong to the user.");
        }
        playlist.addSong(song);
        if (!songs.contains(song)) {
            songs.add(song);
        }
    }

    public void removePlaylist(PlayList playlist) {
        if (playlist == null) {
            throw new IllegalArgumentException("Playlist cannot be null.");
        }
        playlists.remove(playlist);
        rebuildSongsList();
    }

    public void removeSongFromPlaylist(PlayList playlist, Song song) {
        if (playlist == null || song == null) {
            throw new IllegalArgumentException("Playlist and song cannot be null.");
        }
        if (!playlists.contains(playlist)) {
            throw new IllegalArgumentException("Playlist does not belong to the user.");
        }
        playlist.removeSong(song);
        rebuildSongsList();
    }

    public void likeSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Song cannot be null.");
        }
        song.setLiked(true);
    }

    public void dislikeSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Song cannot be null.");
        }
        song.setLiked(false);
    }

    private void rebuildSongsList() {
        songs.clear();
        for (PlayList playlist : playlists) {
            for (Song song : playlist.getSongs()) {
                if (!songs.contains(song)) {
                    songs.add(song);
                }
            }
        }
    }

}