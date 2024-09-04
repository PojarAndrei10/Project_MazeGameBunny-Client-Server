import handler.UserCommandHandler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) {
        Socket socket = new Socket();
        ServerSocket serverSocket = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        System.out.println("START SERVER !!!!");
        UserCommandHandler uch;
        try
        {
            serverSocket = new ServerSocket(8080);
            socket = serverSocket.accept();
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Client Connected !!!");
            uch= new UserCommandHandler(inputStream, outputStream);
            Object arg;
            String s;
            while (true)
            {
                arg = inputStream.readObject();
                if (arg instanceof String)
                {
                    System.out.println("Commands:\n");
                    System.out.println("The command is: " + arg);
                    s = (String) arg;
                    switch (s) {
                        case "find_by_name":
                            uch.findByName();
                            break;
                        case "insert_user":
                            uch.insertUser();
                            break;
                        case "delete_user":
                            uch.deleteUser();
                            break;
                        case "update_user":
                            uch.updateUser();
                            break;
                        case "get_allUsers":
                            uch.getAllUsers();
                            break;
                        case "user_already_exists":
                            uch.userExistsAlready();
                            break;
                        case "update_score":
                            uch.updateScore();
                            break;
                        default:
                            System.out.println("Unknown command: " + s);
                            break;
                    }
                }
            }
        }
        catch (Exception e)
        {
        e.printStackTrace();
        System.out.println("Server Fail");
        }
    }
}
