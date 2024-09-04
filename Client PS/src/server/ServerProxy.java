package server;
public class ServerProxy {
    private ServerConnection serverConnection;
    public ServerProxy(){
        this.serverConnection = ServerConnection.getServerConnection();
    }
    public void sendObject(Object obj) {
        serverConnection.sendObject(obj);
    }
    public Object receiveData() {
        return serverConnection.receive();
    }
    public void closeConnection() {
        serverConnection.closeConnection();
    }
}