import java.io.*;
import java.net.*;
import org.json.*;

public class Main {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 10384);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {

            JSONObject data = new JSONObject();
            data.put("username", "ari");
            data.put("email", "alil@gmail.com");
            data.put("password", "armin1384");

            JSONObject request = new JSONObject();
            request.put("requestType","Authorization");
            request.put("action", "signup");
            request.put("data", data);

            out.println(request.toString());
            out.flush();

            String response = in.readLine();
            System.out.println(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
