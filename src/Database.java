import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Song> songs;
    private List<User> users;
    private final Gson gson;

    private static final String USERS_FILE = "users.json";
    private static final String SONGS_FILE = "songs.json";

    public Database() {
        gson = new Gson();
        songs = new ArrayList<>();
        users = new ArrayList<>();
        loadUsers();
        loadSongs();
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public void removeUser(User user) {
        users.remove(user);
        saveUsers();
    }

    public void addSong(Song song) {
        songs.add(song);
        saveSongs();
    }

    public void removeSong(Song song) {
        songs.remove(song);
        saveSongs();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void saveUsers() {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Failed to save users: " + e.getMessage());
        }
    }

    public void saveSongs() {
        try (FileWriter writer = new FileWriter(SONGS_FILE)) {
            gson.toJson(songs, writer);
        } catch (IOException e) {
            System.out.println("Failed to save songs: " + e.getMessage());
        }
    }

    public void loadUsers() {
        try (FileReader reader = new FileReader(USERS_FILE)) {
            Type userListType = new TypeToken<List<User>>() {}.getType();
            users = gson.fromJson(reader, userListType);
            if (users == null) users = new ArrayList<>();
        } catch (IOException e) {
            users = new ArrayList<>();
        }
    }

    public void loadSongs() {
        try (FileReader reader = new FileReader(SONGS_FILE)) {
            Type songListType = new TypeToken<List<Song>>() {}.getType();
            songs = gson.fromJson(reader, songListType);
            if (songs == null) songs = new ArrayList<>();
        } catch (IOException e) {
            songs = new ArrayList<>();
        }
    }
}
