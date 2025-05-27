import java.util.Map;

public class RequestHandler {

    public Response handle(Request request) {
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
                response = handlePlaylist(action, data);
                break;
            default:
                response.setStatus("error");
                response.setMessage("Unknown request type: " + requestType);
        }

        return response;
    }

    private Response handleSong(String action, Map<String, Object> data) {
        Response response = new Response();

        return response;
    }

    private Response handleUser(String action, Map<String, Object> data) {
        Response response = new Response();

        return response;
    }

    private Response handlePlaylist(String action, Map<String, Object> data) {
        Response response = new Response();

        return response;
    }
}
