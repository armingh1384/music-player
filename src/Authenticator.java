public class Authenticator {
    private final Database database = Database.getInstance();

    public boolean signUp(String username, String email, String password) {
        if (database.usernameExists(username)) {
            return false;
        }
        if (database.emailExists(email)) {
            return false;
        }
        User user = new User(username, email, password);
        database.addUser(user);
        return true;
    }

    public boolean login(String username, String password) {
        User user = database.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return false;
        }
        return true;
    }
}
