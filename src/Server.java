import java.io.*;
import java.net.*;
import com.google.gson.Gson;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                new Thread(() -> {
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                        String message = in.readLine();

                        Gson gson = new Gson();
                        Request request = gson.fromJson(message, Request.class);

                        Response response = new Response();
                        response.setStatus("ok");
                        response.setMessage("received");
                        response.setSong(request.getSong());

                        String jsonResponse = gson.toJson(response);
                        out.write(jsonResponse);
                        out.newLine();
                        out.flush();

                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
