package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection {
    private Socket socket;
    private static ServerConnection serverConnection;
    private ObjectInputStream inputStream=null;
    private ObjectOutputStream outputStream=null;
    public ServerConnection() {
        try {
            socket = new Socket("localhost", 8080);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendObject(Object arg)
    {
        try {
            outputStream.writeObject(arg);
            outputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Object receive() {
        Object arg = null;
        try {
            arg = inputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arg;
    }
    public void closeConnection() {
        try {
            switch (socket != null ? "socket" : "") {
                case "socket":
                    socket.close();
                case "inputStream":
                    inputStream.close();
                case "outputStream":
                    outputStream.close();
                default:
                    break;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ServerConnection getServerConnection() {
        if (serverConnection == null) serverConnection = new ServerConnection();
        return serverConnection;
    }
}
