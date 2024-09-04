package controller;
import model.User;
import model.UserType;
import server.UserServer;
import view.RegisterPageView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class RegisterController implements Observer{
    private UserServer userServer;
    private RegisterPageView registerPageView;
    public HomeController homeController;
    public RegisterController(HomeController homeController) {
        this.homeController = homeController;
        registerPageView = new RegisterPageView(this);
        userServer = new UserServer();
        setupEventHandlers();
    }
    private void setupEventHandlers() {
        registerPageView.setRegisterButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkNameUser(registerPageView.getEmail())) {
                    addNewUser();
                }
            }
        });
    }
    public void setRegisterPageView() {
        registerPageView.init();
    }
    public void close() {
        registerPageView.close();
    }
    public boolean checkNameUser(String name) {
        if (userServer.findUserByName(name) != null) {
            return false;
        }
        return true;
    }
    public void addNewUser() {
        User adminOrPlayer = new User();
        adminOrPlayer.setRole(getRole());
        adminOrPlayer.setName(registerPageView.getName());
        adminOrPlayer.setEmail(registerPageView.getEmail());
        adminOrPlayer.setPassword(registerPageView.getPassword());
        adminOrPlayer.setScore(0);
        userServer.insertUser(adminOrPlayer);
        homeController.registerDone();
    }
    public void changeLanguage(String language) {
        if (language != null) {
            Locale locale = new Locale(language.toLowerCase());
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Language", locale);
            registerPageView.setButtonText(resourceBundle.getString("buttonRegister"));
            registerPageView.setPasswordLabel(resourceBundle.getString("passwordF"));
            registerPageView.setNameLabel(resourceBundle.getString("nameField"));
            registerPageView.setRoleLabel(resourceBundle.getString("roleField"));
        }
    }
    public UserType getRole() {
        JTextField textFieldRole = registerPageView.getTextFieldRole();
        if (textFieldRole.getText().equals("admin"))
            return UserType.ADMIN;
        return UserType.PLAYER;
    }
    @Override
    public void update(Observable o, Object arg) {
    }
}
