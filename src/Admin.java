import java.util.List;

public class Admin {
    String username;
    String password;
    List<User> users;
    List<Song> allsongs;
    void removeUser(User user){
        users.remove(user);
    }
    void addUser(User user){
        users.add(user);
    }

}
