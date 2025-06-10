import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;

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
                if (value instanceof Genre) {
                    song.setGenre((Genre) value);
                } else if (value instanceof String) {
                    try {
                        song.setGenre(Genre.valueOf((String) value));
                    } catch (IllegalArgumentException e) {
                        response.setStatus("error");
                        response.setData("message", "Invalid genre.");
                        return response;
                    }
                }
                break;
            case "setReleaseYear":
                song.setReleaseYear((int) value);
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
                song.setDurationPlayed((int) value);
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

    private Response handleUser(String action, Map<String, Object> data) {
        Response response = new Response();
        User user = (User) data.get("user");
        Object value = data.get("value");
        Object value2 = data.get("value2");

        if (user == null) {
            response.setStatus("error");
            response.setData("message", "User is missing.");
            return response;
        }

        switch (action) {
            case "getUsername":
                response.setData("username", user.getUsername());
                break;
            case "getEmail":
                response.setData("email", user.getEmail());
                break;
            case "getPlaylists":
                response.setData("playlists", user.getPlaylists());
                break;
            case "getSongs":
                response.setData("songs", user.getSongs());
                break;
            case "addPlaylist":
                if (value instanceof PlayList) {
                    user.addPlaylist((PlayList) value);
                    response.setData("status", "playlist added");
                } else {
                    response.setStatus("error");
                    response.setData("message", "Value must be a PlayList.");
                    return response;
                }
                break;
            case "removePlaylist":
                if (value instanceof PlayList) {
                    user.removePlaylist((PlayList) value);
                    response.setData("status", "playlist removed");
                } else {
                    response.setStatus("error");
                    response.setData("message", "Value must be a PlayList.");
                    return response;
                }
                break;
            case "addSongToPlaylist":
                if (value instanceof PlayList && value2 instanceof Song) {
                    user.addSongToPlaylist((PlayList) value, (Song) value2);
                    response.setData("status", "song added to playlist");
                } else {
                    response.setStatus("error");
                    response.setData("message", "Both value and value2 must be provided (PlayList and Song).");
                    return response;
                }
                break;
            case "removeSongFromPlaylist":
                if (value instanceof PlayList && value2 instanceof Song) {
                    user.removeSongFromPlaylist((PlayList) value, (Song) value2);
                    response.setData("status", "song removed from playlist");
                } else {
                    response.setStatus("error");
                    response.setData("message", "Both value and value2 must be provided (PlayList and Song).");
                    return response;
                }
                break;
            case "likeSong":
                if (value instanceof Song) {
                    user.likeSong((Song) value);
                    response.setData("status", "song liked");
                } else {
                    response.setStatus("error");
                    response.setData("message", "Value must be a Song.");
                    return response;
                }
                break;
            case "dislikeSong":
                if (value instanceof Song) {
                    user.dislikeSong((Song) value);
                    response.setData("status", "song disliked");
                } else {
                    response.setStatus("error");
                    response.setData("message", "Value must be a Song.");
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

    private Response handlePlayList(String action, Map<String, Object> data) {
        Response response = new Response();
        PlayList playList = (PlayList) data.get("playList");
        Object value = data.get("value");

        if (playList == null) {
            response.setStatus("error");
            response.setData("message", "PlayList is missing.");
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
                if (value instanceof Song) {
                    boolean added = playList.addSong((Song) value);
                    response.setData("added", added);
                } else {
                    response.setStatus("error");
                    response.setData("message", "Value must be a Song.");
                    return response;
                }
                break;
            case "removeSong":
                if (value instanceof Song) {
                    boolean removed = playList.removeSong((Song) value);
                    response.setData("removed", removed);
                } else {
                    response.setStatus("error");
                    response.setData("message", "Value must be a Song.");
                    return response;
                }
                break;
            case "filter":
                if (value instanceof Filter) {
                    PlayList filtered = playList.filter((Filter) value);
                    response.setData("filteredPlayList", filtered);
                } else {
                    response.setStatus("error");
                    response.setData("message", "Value must be a Filter.");
                    return response;
                }
                break;
            case "collectData":
                if (value instanceof DataCollector) {
                    Object[] collected = playList.collectData((DataCollector) value);
                    response.setData("collectedData", collected);
                } else {
                    response.setStatus("error");
                    response.setData("message", "Value must be a DataCollector.");
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
            e.printStackTrace();
            response.setStatus("error");
            response.setData("message", "Exception: " + e.toString());
            return response;
        }
    }

}