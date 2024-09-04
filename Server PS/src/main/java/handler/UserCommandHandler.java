package handler;
import model.User;
import model.repo.UserRepo;
import java.util.ArrayList;
import java.util.List;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class UserCommandHandler {
    UserRepo userRepo;
    ObjectInputStream inputStream = null;
    ObjectOutputStream outputStream = null;
    public UserCommandHandler(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.userRepo = new UserRepo();
    }
    public void insertUser() throws Exception{
        Object object = inputStream.readObject();
        if (object instanceof User)
            userRepo.createUser((User)object);
    }
    public void updateUser() throws Exception{
        Object object = inputStream.readObject();
        if (object instanceof User)
            userRepo.updateUser((User)object);
    }
    public void deleteUser() throws Exception{
        Object object = inputStream.readObject();
        if (object instanceof User)
            userRepo.deleteUser((User)object);
    }
    public void findByName() throws Exception {
        User user;
        Object object = inputStream.readObject();
        if (object instanceof String)
        {
            user = userRepo.findUserByName((String)object);
            outputStream.writeObject(user);
        }
    }
    public void getAllUsers() throws Exception{
        List<User> listWithUsers = new ArrayList<>();
        listWithUsers = userRepo.getListUsers();
        outputStream.writeObject(listWithUsers);
    }
    public void userExistsAlready() throws Exception{
        Object object = inputStream.readObject();
        if (object instanceof String)
            outputStream.writeObject("true");
    }
    public void updateScore() throws Exception {
        Object object = inputStream.readObject();
        if (object instanceof User) {
            userRepo.updateScore((User) object);
            outputStream.writeObject("Score updated successfully !");
        }
    }
}
