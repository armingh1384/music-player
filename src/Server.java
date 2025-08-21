import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(10384);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("connected");
                new Thread(() -> {
                    try {
                        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                        Gson gson = new Gson();
                        ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
                        while (true) {
                            try {
                                int length = dis.readInt();
                                byte[] chunk = new byte[length];
                                dis.readFully(chunk);
                                bufferStream.write(chunk);
                                String message =bufferStream.toString(StandardCharsets.UTF_8.name());
                                System.out.println(message);
                                if (message.trim().endsWith("}")) {
                                    Request request = gson.fromJson(message, Request.class);
                                    Response response = new RequestHandler().handle(request);
                                    String jsonResponse = gson.toJson(response);
                                    System.out.println(jsonResponse);
                                    byte[] outBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
                                    dos.writeInt(outBytes.length);
                                    dos.write(outBytes);
                                    dos.flush();
                                    bufferStream.reset();
                                }
                            } catch (EOFException e) {
                                break;
                            } catch (Exception e) {
                                Response errorResponse = new Response();
                                errorResponse.setStatus("error");
                                errorResponse.setData("message", "Malformed request");
                                System.out.println("malformed request");
                                byte[] errBytes = gson.toJson(errorResponse).getBytes(StandardCharsets.UTF_8);
                                dos.writeInt(errBytes.length);
                                dos.write(errBytes);
                                dos.flush();
                                bufferStream.reset();
                            }
                        }
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
