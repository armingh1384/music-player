import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Admin extends JFrame {
    private Database db;
    private JTable usersTable;
    private JTable songsTable;

    public Admin(Database db) {
        this.db = db;
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setTitle("Admin Dashboard - Music Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Users", createUsersPanel());
        tabbedPane.addTab("Songs", createSongsPanel());
        tabbedPane.addTab("Top Liked", createTopLikedPanel());

        add(tabbedPane);
    }

    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] userColumns = {"Username", "Total Songs", "Liked Songs"};
        usersTable = new JTable(new DefaultTableModel(userColumns, 0));
        JScrollPane userScrollPane = new JScrollPane(usersTable);

        panel.add(userScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSongsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] songColumns = {"Song Name", "Artist", "Likes Count", "Is Liked", "Owner"};
        songsTable = new JTable(new DefaultTableModel(songColumns, 0));
        JScrollPane songScrollPane = new JScrollPane(songsTable);

        panel.add(songScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTopLikedPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea topSongsArea = new JTextArea();
        topSongsArea.setEditable(false);
        topSongsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        StringBuilder sb = new StringBuilder();
        sb.append("=== MOST LIKED SONGS ===\n\n");

        // Get top 10 most liked songs
        List<Song> topSongs = getTopLikedSongs(10);
        for (int i = 0; i < topSongs.size(); i++) {
            Song song = topSongs.get(i);
            sb.append(String.format("%d. %s - %s (%d likes)\n",
                    i + 1, song.getName(), song.getArtist(), song.getCountOfLikes()));
        }

        topSongsArea.setText(sb.toString());
        panel.add(new JScrollPane(topSongsArea), BorderLayout.CENTER);

        return panel;
    }

    private void loadData() {
        loadUsersData();
        loadSongsData();
    }

    private void loadUsersData() {
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        model.setRowCount(0);

        for (User user : db.getUsers()) {
            int likedSongsCount = 0;
            for (PlayList PlayList : user.getPlaylists()) {
                for (Song song : PlayList.getSongs()) {
                    if (song.isLiked()) {
                        likedSongsCount++;
                    }
                }
            }

            model.addRow(new Object[]{
                    user.getUsername(),
                    getTotalUserSongs(user),
                    likedSongsCount
            });
        }
    }

    private void loadSongsData() {
        DefaultTableModel model = (DefaultTableModel) songsTable.getModel();
        model.setRowCount(0);

        for (User user : db.getUsers()) {
            for (PlayList PlayList : user.getPlaylists()) {
                for (Song song : PlayList.getSongs()) {
                    model.addRow(new Object[]{
                            song.getName(),
                            song.getArtist(),
                            song.getCountOfLikes(),
                            song.isLiked() ? "Yes" : "No",
                            user.getUsername()
                    });
                }
            }
        }
    }

    private List<Song> getTopLikedSongs(int count) {
        // Collect all songs
        java.util.ArrayList<Song> allSongs = new java.util.ArrayList<>();
        for (User user : db.getUsers()) {
            for (PlayList PlayList : user.getPlaylists()) {
                allSongs.addAll(PlayList.getSongs());
            }
        }

        // Sort by likes count (descending)
        allSongs.sort((s1, s2) -> Integer.compare(s2.getCountOfLikes(), s1.getCountOfLikes()));

        // Return top N songs
        return allSongs.subList(0, Math.min(count, allSongs.size()));
    }

    private int getTotalUserSongs(User user) {
        int total = 0;
        for (PlayList PlayList : user.getPlaylists()) {
            total += PlayList.getSongs().size();
        }
        return total;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Database db = Database.getInstance();
            new Admin(db).setVisible(true);
        });
    }
}