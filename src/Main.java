import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // تست نوشتن فایل دستی
        try (FileWriter writer = new FileWriter("C:\\Users\\armin\\Desktop\\ap_project\\songs_test.txt")) {
            writer.write("[{\"name\":\"manual_test\"}]\n");
            writer.flush();
            System.out.println("File written successfully!");
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }

        // اگر Database و Song داری و می‌خواهی تست کنی که آهنگ ذخیره می‌شود:
        Database db = Database.getInstance();
        db.loadSongs();
        Song s = new Song();
        s.setName("TestSong");
        s.setGenre("POP");
        s.setBase64("base64data");
        db.getSongs().add(s);
        db.saveSongs();

        db.loadSongs();
        List<Song> allSongs = db.getSongs();
        for (Song song : allSongs) {
            System.out.println(song.getName());
        }

        System.out.println("Absolute path: " + new File(Database.SONGS_FILE).getAbsolutePath());
    }
}