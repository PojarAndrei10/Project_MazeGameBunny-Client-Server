package server;
import server.ServerProxy;
import model.User;
import java.util.ArrayList;
import java.util.List;

public class UserServer {
    ServerProxy serverProxy;
    public UserServer() {
        serverProxy = new ServerProxy();
    }
    public User findUserByName(String name){
        serverProxy.sendObject("find_by_name");
        serverProxy.sendObject(name);
        Object objectt = serverProxy.receiveData();
        if (objectt instanceof User)
            return (User)objectt;
        return null;
    }
    public void insertUser(User user){
        serverProxy.sendObject("insert_user");
        serverProxy.sendObject(user);
    }
    public int deleteUser(User user){
        serverProxy.sendObject("delete_user");
        serverProxy.sendObject(user);
        return 1;
    }
    public void updateUser(User user){
        serverProxy.sendObject("update_user");
        serverProxy.sendObject(user);
    }
    public List<User> getUsers(){
        List<User> returnUsers = new ArrayList<>();
        serverProxy.sendObject("get_allUsers");
        Object objectt = serverProxy.receiveData();
        if (objectt instanceof List<?>)
            returnUsers = (List<User>) objectt;
        return returnUsers;
    }
    public boolean userExists(String email, String password){
        serverProxy.sendObject("user_already_exists");
        serverProxy.sendObject(email);
        serverProxy.sendObject(password);
        Object objectt = serverProxy.receiveData();
        if (objectt instanceof String)
            return true;
        return true;
    }
    public void updateScore(User user) {
        try {
            serverProxy.sendObject("update_score");
            serverProxy.sendObject(user);
            Object response = serverProxy.receiveData();
            if (response instanceof String) {
                System.out.println((String) response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
