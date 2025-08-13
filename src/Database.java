import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final Database instance = new Database();

    private final Gson gson;
    private List<Song> songs;
    private List<User> users;

    private static final String USERS_FILE = "users.json";
    private static final String SONGS_FILE = "songs.json";

    private Database() {
        gson = new Gson();
        songs = new ArrayList<>();
        users = new ArrayList<>();
        loadUsers();
        loadSongs();
    }

    public static Database getInstance() {
        return instance;
    }

    public synchronized void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public synchronized void removeUser(User user) {
        users.remove(user);
        saveUsers();
    }

    public synchronized void addSong(Song song) {
        songs.add(song);
        saveSongs();
    }

    public synchronized void removeSong(Song song) {
        songs.remove(song);
        saveSongs();
    }

    public synchronized void updateUser(User updatedUser) {
        boolean exists = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser);
                exists = true;
                break;
            }
        }
        if (!exists) {
            users.add(updatedUser);
        }
        saveUsers();
    }

    public synchronized boolean usernameExists(String username) {
        return users.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    public synchronized boolean emailExists(String email) {
        return users.stream().anyMatch(u -> u.getEmail().equals(email));
    }

    public synchronized User getUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public synchronized List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public synchronized List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    private synchronized void saveUsers() {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Failed to save users: " + e.getMessage());
        }
    }

    private synchronized void saveSongs() {
        try (FileWriter writer = new FileWriter(SONGS_FILE)) {
            gson.toJson(songs, writer);
        } catch (IOException e) {
            System.out.println("Failed to save songs: " + e.getMessage());
        }
    }

    private synchronized void loadUsers() {
        try (FileReader reader = new FileReader(USERS_FILE)) {
            Type userListType = new TypeToken<List<User>>() {}.getType();
            users = gson.fromJson(reader, userListType);
            if (users == null) users = new ArrayList<>();
        } catch (IOException e) {
            users = new ArrayList<>();
        }
    }

    private synchronized void loadSongs() {
        try (FileReader reader = new FileReader(SONGS_FILE)) {
            Type songListType = new TypeToken<List<Song>>() {}.getType();
            songs = gson.fromJson(reader, songListType);
            if (songs == null) songs = new ArrayList<>();
        } catch (IOException e) {
            songs = new ArrayList<>();
        }
    }
}
