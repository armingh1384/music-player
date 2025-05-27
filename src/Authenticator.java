import java.util.HashMap;
import java.util.Map;

public class Authenticator {
    private Map<String, User> usersByUsername = new HashMap<>();
    private Map<String, User> usersByEmail = new HashMap<>();

    public boolean signUp(String username, String email, String password) {
        if (usersByUsername.containsKey(username)) {
            System.out.println("Username already exists.");
            return false;
        }
        if (usersByEmail.containsKey(email)) {
            System.out.println("Email already registered.");
            return false;
        }
        User user = new User(username, password, email);
        usersByUsername.put(username, user);
        usersByEmail.put(email, user);
        System.out.println("Sign up successful.");
        return true;
    }

    public boolean login(String username, String password) {
        User user = usersByUsername.get(username);
        if (user == null) {
            System.out.println("Username not found.");
            return false;
        }
        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password.");
            return false;
        }
        System.out.println("Login successful.");
        return true;
    }
}
