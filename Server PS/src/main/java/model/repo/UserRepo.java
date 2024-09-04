package model.repo;
import model.User;
import model.UserType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    Connection connection;
    public UserRepo() {
        connection = DatabaseConnection.getConnection();
    }
    public void createUser(User user){
        PreparedStatement preparedStatement = null;
        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();
        int score = user.getScore();
        String role = user.getRole().toString();

        String insertStatement = "INSERT INTO users (name,password,email,score, role)"
                + " VALUES (?,?,?,?,?)";
        try{
            preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, score);
            preparedStatement.setString(5,role);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DatabaseConnection.closeConnection(preparedStatement);
        }
    }
    public void updateUser(User user){
        PreparedStatement preparedStatement = null;
        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();
        int score = user.getScore();
        String role = user.getRole().toString();

        String insertStatement = "UPDATE users " +
                " SET email = ?, password = ?,role = ? " +
                " WHERE name = ?";
        try{
            preparedStatement = connection.prepareStatement(insertStatement);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3,role);
            preparedStatement.setString(4, name);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DatabaseConnection.closeConnection(preparedStatement);
        }
    }
    public int deleteUser(User user){
        int ok=0;
        PreparedStatement preparedStatement = null;
        String deleteUserStatement = "DELETE FROM users WHERE email = ?";
        try {
            preparedStatement = connection.prepareStatement(deleteUserStatement);
            preparedStatement.setString(1, user.getEmail());
            ok = preparedStatement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection(preparedStatement);
        }
        return ok;
    }
    public User findUserByName(String namee){
        String find = "Select * from users where name = ?";
        String name,password,rolee;
        int score;
        User user = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(find);
            preparedStatement.setString(1, namee);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) return null;
            name = resultSet.getString("name");
            score = resultSet.getInt("score");
            password = resultSet.getString("password");
            UserType role = UserType.PLAYER;
            rolee = resultSet.getString("role");
            if (rolee != null)
                if (rolee.equals("ADMIN"))
                    role = UserType.ADMIN;
                else
                    role = UserType.PLAYER;
            user = new User(name, namee, score, password);
            user.setRole(role);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection(preparedStatement);
            DatabaseConnection.closeConnection(resultSet);
        }
        return user;
    }
    public void updateScore(User user) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "UPDATE users SET score = ? WHERE name = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, user.getScore());
            statement.setString(2, user.getName());
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<User> getListUsers(){
        List<User> returnUsers = new ArrayList<>();

        String findUsersStatement = "Select * from users";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(findUsersStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                String name = resultSet.getString("name");
                int score = resultSet.getInt("score");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                UserType role = UserType.PLAYER;
                String DBRole = resultSet.getString("role");
                if (DBRole != null)
                    if (DBRole.equals("ADMIN"))
                        role = UserType.ADMIN;
                    else
                        role = UserType.PLAYER;
                user = new User(name, email, score, password);
                user.setRole(role);
                returnUsers.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection(preparedStatement);
            DatabaseConnection.closeConnection(resultSet);
        }
        return returnUsers;
    }
    public boolean userExists(String username, String password){
        User user = findUserByName(username);
        if (user == null)
            return false;
        if(!user.getPassword().equals(password)){
            return false;
        }
        return true;
    }
}
