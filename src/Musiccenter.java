import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Musiccenter {
    private Song[] playlist;
    private int currentIndex = 0;
    private PlaybackState playbackState = PlaybackState.STOPPED;
    private Song currentSong = null;

    private enum PlaybackState {
        PLAYING,
        PAUSED,
        STOPPED
    }

    public Musiccenter(Song[] playlist) {
        this.playlist = playlist;
        if (playlist != null && playlist.length > 0) {
            this.currentSong = playlist[0];
        }
    }

    public void play() {
        if (currentSong == null) {
            System.out.println("No song to play.");
            return;
        }
        playbackState = PlaybackState.PLAYING;
        System.out.println("Playing song: " + currentSong.getName());
    }

    public void stop() {
        if (playbackState == PlaybackState.PLAYING || playbackState == PlaybackState.PAUSED) {
            playbackState = PlaybackState.STOPPED;
            System.out.println("Stopped song: " + currentSong.getName());
        } else {
            System.out.println("No song is playing.");
        }
    }

    public void pause() {
        if (playbackState == PlaybackState.PLAYING) {
            playbackState = PlaybackState.PAUSED;
            System.out.println("Paused song: " + currentSong.getName());
        } else {
            System.out.println("No song is playing to pause.");
        }
    }

    public void playnext() {
        if (playlist == null || playlist.length == 0) {
            System.out.println("Playlist is empty.");
            return;
        }
        currentIndex++;
        if (currentIndex >= playlist.length) {
            System.out.println("End of playlist reached.");
            currentSong = null;
            playbackState = PlaybackState.STOPPED;
            return;
        }
        currentSong = playlist[currentIndex];
        playbackState = PlaybackState.PLAYING;
        System.out.println("Playing next song: " + currentSong.getName());
    }

    public void download(String targetDir) {
        if (currentSong == null) {
            System.out.println("No song selected to download.");
            return;
        }

        String sourcePath = currentSong.getFilePath();
        if (sourcePath == null || sourcePath.isEmpty()) {
            System.out.println("Current song does not have a valid file path.");
            return;
        }

        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists()) {
            System.out.println("Source file does not exist: " + sourcePath);
            return;
        }

        File targetDirectory = new File(targetDir);
        if (!targetDirectory.exists()) {
            boolean created = targetDirectory.mkdirs();
            if (!created) {
                System.out.println("Failed to create target directory: " + targetDir);
                return;
            }
        }

        File targetFile = new File(targetDirectory, sourceFile.getName());

        try (FileInputStream inputStream = new FileInputStream(sourceFile);
             FileOutputStream outputStream = new FileOutputStream(targetFile)) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            System.out.println("Downloading song: " + currentSong.getName());

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Download complete. Saved to: " + targetFile.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error occurred while downloading: " + e.getMessage());
        }
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public PlaybackState getPlaybackState() {
        return playbackState;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
