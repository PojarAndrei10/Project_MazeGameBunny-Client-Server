package controller;
import model.User;
import server.UserServer;
import view.LoginPageView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class LoginController implements Observer {
    private UserServer userServer;
    private LoginPageView loginPageView;
    private User user;
    public HomeController homeController;
    public LoginController(User user, HomeController homeController) {
        this.user = user;
        this.homeController = homeController;
        this.userServer = new UserServer();
        loginPageView = new LoginPageView(this);
    }
    public void userLogin() {
        User user;
        String username = loginPageView.getUsernameFieldText();
        String password = loginPageView.getPasswordFieldText();
        if (userServer.userExists(username, password)) {
            user = userServer.findUserByName(username);
            loginPageView.close();
            homeController.loginSuccessful(user);
        } else {
            System.out.println("This user does not exist");
        }
    }
    public ActionListener createLoginBtnActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeController.language = loginPageView.getLanguagesChoices().toString();
                userLogin();
            }
        };
    }
    public ActionListener createRegisterBtnActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        };
    }
    public ActionListener createLangChoiceActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeController.language = loginPageView.getLanguagesChoices().toString();
                changeLanguage(homeController.language);
            }
        };
    }
    public void startLoginUser() {
        loginPageView.init();
    }
    public void close() {
        loginPageView.close();
    }
    public void registerUser() {
        homeController.startRegister();
    }
    public void changeLanguage(String language) {
        if (language != null) {
            Locale locale = new Locale(language.toLowerCase());
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Language", locale);
            loginPageView.setLoginBtnText(resourceBundle.getString("buttonLogin"));
            loginPageView.setRegisterBtnText(resourceBundle.getString("buttonRegister"));
            loginPageView.setPasswordFieldLabelText(resourceBundle.getString("passwordF"));
            loginPageView.setUsernameFieldLabelText(resourceBundle.getString("usernameF"));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Observer) {
            String username = loginPageView.getUsernameFieldText();
            String password = loginPageView.getPasswordFieldText();
            if (userServer.userExists(username, password)) {
                user = userServer.findUserByName(username);
                loginPageView.close();
                homeController.loginSuccessful(user);
            } else {
                System.out.println("This user does not exist");
            }
        }
    }
}
