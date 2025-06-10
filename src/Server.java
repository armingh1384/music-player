import java.io.*;
import java.net.*;
import com.google.gson.Gson;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(10384);

            while (true) {
                System.out.println("waiting to connect");
                Socket clientSocket = serverSocket.accept();
                System.out.println("connected");

                new Thread(() -> {
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                        String message = in.readLine();

                        Gson gson = new Gson();
                        Request request = gson.fromJson(message, Request.class);
                        System.out.println("Received request: " + request);
                        System.out.println("RequestType: " + request.getRequestType());
                        System.out.println("Action: " + request.getAction());
                        System.out.println("Data: " + request.getData());

                        if (request == null) {
                            System.out.println("request is null!");
                        }

                        Response response = new RequestHandler().handle(request);
                        response.setStatus("ok");
                        response.setMessage("received");

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
