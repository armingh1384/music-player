import java.io.File;
import java.time.LocalDateTime;

public class Song {
    private String name;
    private String album;
    private String artist;
    private String musicPath;
    private File musicFile;
    private Double releaseYear;
    private String genre;
    private String lyrics;
    private int countOfLikes;
    private boolean isLiked;
    private Double durationPlayed;
    private LocalDateTime addedTime;
    private String base64Audio;


    public Song(LocalDateTime addedTime, String genre, String lyrics, int releaseYear, File musicFile, String musicPath, int durationPlayed, String artist, String album, String name) {
        this.addedTime = addedTime;
        this.genre = genre;
        this.lyrics = lyrics;
        this.releaseYear = releaseYear > 0.0 ? releaseYear : 2023.0;
        this.musicFile = musicFile;
        this.musicPath = musicPath;
        this.durationPlayed = Math.max(durationPlayed, 0.0);
        this.artist = artist;
        this.album = album;
        this.name = name;
    }

    public Song() {
        this.addedTime = LocalDateTime.now();
        this.genre = "";
        this.lyrics = "";
        this.releaseYear = 2023.0;
        this.musicFile = null;
        this.musicPath = "";
        this.durationPlayed = 0.0;
        this.artist = "";
        this.album = "";
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        if (album != null && !album.trim().isEmpty()) {
            this.album = album;
        }
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        if (artist != null && !artist.trim().isEmpty()) {
            this.artist = artist;
        }
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        if (musicPath != null && !musicPath.trim().isEmpty()) {
            this.musicPath = musicPath;
        }
    }

    public File getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(File musicFile) {
        this.musicFile = musicFile;
    }

    public Double getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Double releaseYear) {
        if (releaseYear > 0) {
            this.releaseYear = releaseYear;
        }
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public int getCountOfLikes() {
        return countOfLikes;
    }

    public void setCountOfLikes(int countOfLikes) {
        if (countOfLikes >= 0) {
            this.countOfLikes = countOfLikes;
        }
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public Double getDurationPlayed() {
        return durationPlayed;
    }

    public void setDurationPlayed(Double durationPlayed) {
        if (durationPlayed >= 0) {
            this.durationPlayed = durationPlayed;
        }
    }

    public LocalDateTime getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(LocalDateTime addedTime) {
        this.addedTime = addedTime;
    }

    public String getBase64() {
        return base64Audio;
    }

    public void setBase64(String base64) {
        this.base64Audio = base64;
    }

    public String getDetails() {
        return String.format("Name: %s, Artist: %s, Album: %s, Genre: %s, Release Year: %d", name, artist, album, genre, releaseYear);
    }

    @Override
    public String toString() {
        return getDetails();
    }
}