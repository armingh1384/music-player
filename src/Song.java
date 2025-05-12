import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Song {
    String name;

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public boolean isIsliked() {
        return isliked;
    }

    public int getCountoflikes() {
        return countoflikes;
    }

    public String getLyrics() {
        return lyrics;
    }

    public File getMusicFile() {
        return musicFile;
    }

    public LocalDateTime getAdded_time() {
        return added_time;
    }

    public void setAdded_time(LocalDateTime added_time) {
        this.added_time = added_time;
    }

    public void setIsliked(boolean isliked) {
        this.isliked = isliked;
    }

    public void setCountoflikes(int countoflikes) {
        this.countoflikes = countoflikes;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setMusicFile(File musicFile) {
        this.musicFile = musicFile;
    }

    public void setMusicPath( String musicPath) {
        this.musicPath = musicPath;
    }

    public void setDuration_played(int duration_played) {
        this.duration_played = duration_played;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration_played() {
        return duration_played;
    }

    String album;
    String artist;
    int duration_played;
    String musicPath;
    File musicFile;
    int releaseYear;
    Genre genre;
    String lyrics;
    int countoflikes;

boolean isliked;
    LocalDateTime added_time;
    public int getReleaseYear() {
        return releaseYear;
    }
    public String getAlbum() {
        return album;
    }
public String getArtist() {
        return artist;
}
public Genre getGenre() {
        return genre;
}
public String getFilepath() {
        return musicPath;
}
public String getName() {
        return name;
}

    public String getFilePath() {
        return musicPath;
    }

    String getdetails(Song song) {
        return "";
    }
}
