import com.google.gson.Gson;

import java.util.List;

public class Database {
    List<Song> songs;
    List<User> users;

     Gson gson;


    void removeUser(User user){
        users.remove(user);
    }
    void addUser(User user){
        users.add(user);
    }
}
