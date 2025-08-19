import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import com.google.gson.Gson;

public class RequestHandler {
    private final Database database;

    public RequestHandler() {
        this.database = Database.getInstance();
    }

    public Response handle(Request request)  {
        String requestType = request.getRequestType();
        String action = request.getAction();
        Map<String, Object> data = request.getData();
        Response response = new Response();
        switch (requestType) {
            case "song":
                response = handleSong(action, data);
                break;
            case "user":
                response = handleUser(action, data);
                break;
            case "playlist":
                response = handlePlayList(action, data);
                break;
            case "Authorization":
                response = handleAuthorization(action, data);
                break;
            default:
                response.setStatus("error");
                response.setMessage("Unknown request type: " + requestType);
        }
        return response;
    }

    private Response handleSong(String action, Map<String, Object> data) {
        Response response = new Response();
        Song song = (Song) data.get("song");
        if (song == null) {
            response.setStatus("error");
            response.setData("message", "Song is missing.");
            return response;
        }
        Object value = data.get("value");
        switch (action) {
            case "getName":
                response.setData("name", song.getName());
                break;
            case "getArtist":
                response.setData("artist", song.getArtist());
                break;
            case "getAlbum":
                response.setData("album", song.getAlbum());
                break;
            case "getGenre":
                response.setData("genre", song.getGenre().toString());
                break;
            case "getReleaseYear":
                response.setData("releaseYear", song.getReleaseYear());
                break;
            case "getMusicPath":
                response.setData("musicPath", song.getMusicPath());
                break;
            case "getMusicFile":
                response.setData("musicFile", song.getMusicFile() != null ? song.getMusicFile().getPath() : null);
                break;
            case "getLyrics":
                response.setData("lyrics", song.getLyrics());
                break;
            case "getCountOfLikes":
                response.setData("countOfLikes", song.getCountOfLikes());
                break;
            case "isLiked":
                response.setData("isLiked", song.isLiked());
                break;
            case "getDurationPlayed":
                response.setData("durationPlayed", song.getDurationPlayed());
                break;
            case "getAddedTime":
                response.setData("addedTime", song.getAddedTime().toString());
                break;
            case "getDetails":
                response.setData("details", song.getDetails());
                break;
            case "toString":
                response.setData("string", song.toString());
                break;
            case "setName":
                song.setName((String) value);
                break;
            case "setArtist":
                song.setArtist((String) value);
                break;
            case "setAlbum":
                song.setAlbum((String) value);
                break;
            case "setGenre":
               song.setGenre((String)data.get("genre"));
                break;
            case "setReleaseYear":
                song.setReleaseYear((Double) value);
                break;
            case "setMusicPath":
                song.setMusicPath((String) value);
                break;
            case "setMusicFile":
                if (value instanceof File) {
                    song.setMusicFile((File) value);
                } else if (value instanceof String) {
                    song.setMusicFile(new File((String) value));
                }
                break;
            case "setLyrics":
                song.setLyrics((String) value);
                break;
            case "setCountOfLikes":
                song.setCountOfLikes((int) value);
                break;
            case "setLiked":
                song.setLiked((boolean) value);
                break;
            case "setDurationPlayed":
                song.setDurationPlayed((Double) value);
                break;
            case "setAddedTime":
                if (value instanceof LocalDateTime) {
                    song.setAddedTime((LocalDateTime) value);
                } else if (value instanceof String) {
                    try {
                        song.setAddedTime(LocalDateTime.parse((String) value));
                    } catch (Exception e) {
                        response.setStatus("error");
                        response.setData("message", "Invalid date format.");
                        return response;
                    }
                }
                break;
            default:
                response.setStatus("error");
                response.setData("message", "Invalid action: " + action);
                return response;
        }
        if (!action.startsWith("get") && !action.equals("toString") && !action.equals("getDetails")) {
            response.setStatus("success");
            response.setData("message", action + " applied.");
        }
        return response;
    }

    // HandleUser.java
    private Response handleUser(String action, Map<String, Object> data) {
        Response response = new Response();
        Gson gson = new Gson();
        Database db = Database.getInstance();
        if(action.equals("ImportFromNava")){ List<Map<String, Object>> globalSongs = new ArrayList<>();
                db.loadSongs();

                for (Song s : db.getSongs()) {
                    Map<String, Object> songInfo = new HashMap<>();
                    songInfo.put("name", s.getName());

                    songInfo.put("genre", s.getGenre());
                    songInfo.put("base64", s.getBase64());
                    globalSongs.add(songInfo);
                }
                response.setStatus("success");
                response.setData("globalsongs", globalSongs);
                return response;}

        String username = (String) data.get("username");
        if (username==null){
            username= (String) data.get("lastusername");
           User user = db.getUserByUsername(username);


        }


        if ((username == null || username.isEmpty())) {
            response.setStatus("error");
            response.setData("message", "Username is required.");
            return response;
        }
        User user = db.getUserByUsername(username);

        if (user == null) {
            response.setStatus("error");
            response.setData("message", "User not found.");
            return response;
        }

        String playlistname = (String) data.get("playlistname");
        PlayList playlist = null;
        if (playlistname != null) {
            for (PlayList p : user.getPlaylists()) {
                if (playlistname.equals(p.getName())) {
                    playlist = p;
                    break;
                }
            }
            if ((playlist == null && action.equals("addSongToPlaylist")) || action.equals("addPlaylist")) {
                playlist = new PlayList();
                playlist.setName(playlistname);
                user.addPlaylist(playlist);
            }
        }

        switch (action) {
            case "update": {

                String newUsername = (String) data.get("Nusername");
                String newEmail = (String) data.get("Nemail");
                String newPassword = (String) data.get("Npassword");

                if (user == null) {
                    response.setData("status", "error");
                    response.setData("msg", "User not found.");
                    break;
                }
                boolean checkednameandmail=true;int control =0;
                for (User u :db.getUsers()){
                    if(u.getUsername()==newUsername||u.getEmail()==newEmail){
                        control++;

                    }
                    if (control>1)
                        checkednameandmail=false;
                    if(!checkednameandmail)
                        break;

                }
                if(checkednameandmail){
                user.setUsername(newUsername);
                user.setEmail(newEmail);}
                else{
                    response.setData("message","Name or Email has been used by others");
               return response; }
                if(newPassword.contains(newUsername)){
                    response.setStatus("error");
                    response.setData("message","password contains username");
                return response;}
                user.setPassword(newPassword);
                boolean updated=true;

                db.updateUser(user);
                if (updated) {
                    response.setData("status", "success");
                    response.setData("msg", "Profile updated successfully.");
                } else {
                    response.setData("status", "error");
                    response.setData("msg", "Update failed.");
                }
                break;
            }

            case "AddToNava":
                db.loadSongs();
                Song songToGlobal = createSongFromData(data);
                if (songToGlobal != null) {
                    db.addSong(songToGlobal);
                    db.saveSongs();
                    response.setData("status", "song adddded to global");
                } else {
                    response.setStatus("error");
                    response.setData("message", "Song data is invalid.");
                    return response;
                }
                break;

        case "getProfile":
                username = (String) data.get("username");
                 user = db.getUserByUsername(username);
                if (user != null) {
                    response.setData("username", user.getUsername());
                    response.setData("email", user.getEmail());

                    response.setStatus("success");
                } else {
                    response.setStatus("error");

                }
                break;
            case "getPlaylists":
                response.setData("playlists", user.getPlaylists());
                break;
            case "getSongs":
                try {
                     username = (String) data.get("username");
                    String playlistName = (String) data.get("playlistname");


                     user = db.getUserByUsername(username);
                    if (user == null) {
                        response.setStatus("error");
                        response.setData("message", "User not found");
                        break;
                    }


                     playlist = user.getPlaylists().stream()
                            .filter(p -> p.getName().equalsIgnoreCase(playlistName))
                            .findFirst()
                            .orElse(null);

                    if (playlist == null) {
                        response.setStatus("error");
                        response.setData("message", "Playlist not found");
                        break;
                    }

                    response.setStatus("success");
                    response.setData("songs", playlist.getSongs());

                } catch (Exception e) {
                    response.setStatus("error");
                    response.setData("message", "Error retrieving songs: " + e.getMessage());
                }

                break;
            case "addPlaylist":
                if (playlist != null) {
                    db.updateUser(user);
                    response.setData("status", "playlist added");
                } else {
                    response.setStatus("error");
                    response.setData("message", "Playlist is missing or invalid.");
                    return response;
                }
                break;
            case "removePlaylist":
                if (playlist != null) {
                    user.removePlaylist(playlist);
                    db.updateUser(user);
                    response.setData("status", "playlist removed");
                } else {
                    response.setStatus("error");
                    response.setData("message", "Playlist is missing or invalid.");
                    return response;
                }
                break;
            case "addSongToPlaylist":
                if (playlist != null) {
                    Song song = createSongFromData(data);
                    playlist.addSong(song);
                    if (!user.getSongs().contains(song)) {
                        user.getSongs().add(song);
                    }
                    db.updateUser(user);
                    response.setData("status", "song added to playlist and user");
                } else {
                    response.setStatus("error");
                    response.setData("message", "Playlist is missing or invalid.");
                    return response;
                }
                break;
            case "removeSongFromPlaylist":
                Song songToRemove =  createSongFromData(data);


                if(songToRemove==null) {
                    response.setStatus("error"); return response;
                }
                if (playlist != null && songToRemove != null) {
                    playlist.removeSong(songToRemove);
                    db.updateUser(user);
                    response.setData("status", playlist.getName());
                } else {
                    response.setStatus("error");
                    response.setData("message", "Playlist and song are required.");
                    return response;
                }
                break;
            case "likeSong":
                Song songToLike = createSongFromData(data);
                if (songToLike != null) {
                    user.likeSong(songToLike);
                    db.updateUser(user);
                    response.setData("status", "song liked");
                } else {
                    response.setStatus("error");
                    response.setData("message", "Song is missing.");
                    return response;
                }
                break;
            case "dislikeSong":
                Song songToDislike = getSongFromData(data,  "song");
                if (songToDislike != null) {
                    user.dislikeSong(songToDislike);
                    db.updateUser(user);
                    response.setData("status", "song disLiked");
                } else {
                    response.setStatus("error");
                    response.setData("message", "Song is missing.");
                    return response;
                }
                break;
            default:
                response.setStatus("error");
                response.setData("message", "Invalid action: " + action);
                return response;
        }
        response.setStatus("success");
        return response;
    }

    private Song createSongFromData(Map<String, Object> data) {
        Song song = new Song();
        song.setName((String) data.getOrDefault("name", ""));
        song.setArtist((String) data.getOrDefault("artist", ""));
        song.setBase64((String) data.getOrDefault("base64Audio", "salam"));
        song.setMusicPath((String) data.getOrDefault("musicPath", ""));
        song.setReleaseYear((Double) data.getOrDefault("releaseYear", 0.0));
        song.setGenre((String) data.getOrDefault("genre", ""));
        song.setLyrics((String) data.getOrDefault("lyrics", ""));
        song.setDurationPlayed((Double) data.getOrDefault("durationPlayed", 0.0));
        song.setAlbum((String) data.getOrDefault("album", ""));
        song.setLiked((boolean) data.getOrDefault("isLiked", false));
        return song;
    }

    private Song getSongFromData(Map data, String key) {
        if (data.get(key) instanceof Song) {
            return (Song) data.get(key);
        } else if (data.get(key) instanceof Map) {
                return createSongFromData(data);
        }
        return null;
    }

    private Response handlePlayList(String action, Map<String, Object> data) {
        Response response = new Response();
        Gson gson = new Gson();
        try {
            PlayList playList = extractPlayListFromData(data, gson);
            if (playList == null) {
                response.setStatus("error");
                response.setData("message", "PlayList data is invalid");
                return response;
            }
            switch (action) {
                case "getSongs":
                    response.setData("songs", playList.getSongs());
                    break;
                case "getNumberOfSongs":
                    response.setData("numberOfSongs", playList.getNumberOfSongs());
                    break;
                case "addSong":
                    response.setStatus("error");
                    response.setData("message", "addSong should be done via user, not playlist!");
                    return response;
                case "removeSong":
                    Object value = data.get("value");
                    Song song = getSongFromData(data, "song");
                    response.setData("removed", playList.removeSong(song));
                    break;
                case "filter":
                    Object filterValue = data.get("value");
                    Filter filter = null;
                    if (filterValue instanceof Filter) {
                        filter = (Filter) filterValue;
                    } else if (filterValue instanceof Map) {
                        filter = gson.fromJson(gson.toJson(filterValue), Filter.class);
                    }
                    response.setData("filteredPlayList", playList.filter(filter));
                    break;
                case "collectData":
                    Object collectorValue = data.get("value");
                    DataCollector collector = null;
                    if (collectorValue instanceof DataCollector) {
                        collector = (DataCollector) collectorValue;
                    } else if (collectorValue instanceof Map) {
                        collector = gson.fromJson(gson.toJson(collectorValue), DataCollector.class);
                    }
                    response.setData("collectedData", playList.collectData(collector));
                    break;
                default:
                    response.setStatus("error");
                    response.setData("message", "Invalid action: " + action);
            }
            response.setStatus("success");
        } catch (Exception e) {
            response.setStatus("error");
            response.setData("message", "Processing error: " + e.getMessage());
        }
        return response;
    }

    private PlayList extractPlayListFromData(Map<String, Object> data, Gson gson) {
        Object playListObj = data.get("playList");
        if (playListObj == null) return null;
        if (playListObj instanceof PlayList) {
            return (PlayList) playListObj;
        }
        if (playListObj instanceof Map) {
            String json = gson.toJson(playListObj);
            return gson.fromJson(json, PlayList.class);
        }
        return null;
    }

    private Genre extractGenre(Map<String, Object> data) {
        if (!data.containsKey("genre") || !(data.get("genre") instanceof String)) {
            return Genre.UNKNOWN;
        }
        try {
            return Genre.valueOf(((String) data.get("genre")).toUpperCase());
        } catch (IllegalArgumentException e) {
            return Genre.UNKNOWN;
        }
    }

    private int extractReleaseYear(Map<String, Object> data) {
        if (!data.containsKey("releaseYear") || !(data.get("releaseYear") instanceof Number)) {
            return 2023;
        }
        return ((Number) data.get("releaseYear")).intValue();
    }

    private int extractDurationPlayed(Map<String, Object> data) {
        if (!data.containsKey("durationPlayed") || !(data.get("durationPlayed") instanceof Number)) {
            return 0;
        }
        return ((Number) data.get("durationPlayed")).intValue();
    }

    private String validateBase64Audio(Map<String, Object> data) throws Exception {
        return (String) data.get("base64Audio");
    }

    private String validateName(Map<String, Object> data) throws Exception {
        return (String) data.get("name");
    }

    private File createTempAudioFile(String name, String base64Audio) throws Exception {
        File tempFile = File.createTempFile(name, ".mp3");
        byte[] audioBytes = Base64.getDecoder().decode(base64Audio);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(audioBytes);
        }
        return tempFile;
    }

    private final Authenticator authenticator = new Authenticator();

    private Response handleAuthorization(String action, Map<String, Object> data) {
        Response response = new Response();
        if (data == null) {
            response.setStatus("error");
            response.setData("message", "Request data is missing");
            return response;
        }
        if (action == null || action.trim().isEmpty()) {
            response.setStatus("error");
            response.setData("message", "Action is missing");
            return response;
        }
        try {
            switch (action.toLowerCase()) {
                case "signup":
                    String username = data.get("username") != null ? data.get("username").toString() : null;
                    String email = data.get("email") != null ? data.get("email").toString() : null;
                    String password = data.get("password") != null ? data.get("password").toString() : null;
                    if (username == null || email == null || password == null) {
                        response.setStatus("error");
                        response.setData("message", "Missing signup fields");
                        return response;
                    }
                    if(password.contains(username)){
                        response.setStatus("error");
                    response.setData("message","password contains username");
                    return response;}
                    if (authenticator.signUp(username, email, password)) {
                        response.setStatus("success");
                        response.setData("message", "Registration successful");
                    } else {
                        response.setStatus("error");
                        response.setData("message", "Username or Email already exists");
                    }
                    return response;
                case "login":
                    String loginUsername = data.get("username") != null ? data.get("username").toString() : null;
                    String loginPassword = data.get("password") != null ? data.get("password").toString() : null;
                    if (loginUsername == null || loginPassword == null) {
                        response.setStatus("error");
                        response.setData("message", "Missing login fields");
                        return response;
                    }
                    if (authenticator.login(loginUsername, loginPassword)) {
                        response.setStatus("success");
                        response.setData("message", "Login successful");
                        User user = Database.getInstance().getUserByUsername(loginUsername);
                        response.setData("user", user);
                    } else {
                        response.setStatus("error");
                        response.setData("message", "Invalid username or password");
                    }
                    return response;
                default:
                    response.setStatus("error");
                    response.setData("message", "Invalid action");
                    return response;
            }
        } catch (Exception e) {
            response.setStatus("error");
            response.setData("message", "Exception: " + e.toString());
            return response;
        }
    }
}