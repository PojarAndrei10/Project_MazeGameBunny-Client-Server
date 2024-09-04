package view;

import controller.LoginController;
import javax.swing.*;
import java.awt.*;

public class LoginPageView {
    private LoginController loginController;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton,registerButton;
    private JLabel username,password;
    private JComboBox<String> languages;
    private JFrame frame;
    JPanel panel = new JPanel();
    public LoginPageView(LoginController loginController) {
        this.loginController = loginController;
        initializeComponents();
        setupListeners();
    }
    private void initializeComponents() {
        frame = new JFrame("Login Page");
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        usernameField = new JTextField(20);
        username = new JLabel("Username");
        passwordField = new JPasswordField(20);
        password = new JLabel("Password");
        languages = new JComboBox<>(new String[]{"English", "Italiano", "Français","Romana"});
        panel.setPreferredSize (new Dimension (500, 500));
        panel.setLayout(new FlowLayout());
        registerButton.setBounds (290, 150, 100, 20);
        loginButton.setBounds (115, 150, 100, 20);
        usernameField.setBounds (105, 25, 230, 25);
        username.setBounds (10, 25, 100, 25);
        passwordField.setBounds (105, 70, 230, 25);
        password.setBounds (10, 70, 100, 25);

        loginButton.setBackground(Color.YELLOW);
        registerButton.setBackground(Color.YELLOW);
        panel.add(username);
        panel.add(usernameField);
        panel.add(password);
        panel.add(passwordField);
        panel.add(languages);
        panel.add(loginButton);
        panel.add(registerButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
    }
    public String getUsernameFieldText() {
        return usernameField.getText();
    }
    public String getPasswordFieldText() {
        return new String(passwordField.getPassword());
    }
    public Object getLanguagesChoices() {
        return languages.getSelectedItem();
    }
    public void setLoginBtnText(String text) {
        loginButton.setText(text);
    }
    public void setRegisterBtnText(String text) {
        registerButton.setText(text);
    }
    public void setPasswordFieldLabelText(String text) {
        password.setText(text);
    }
    public void close() {
        frame.setVisible(false);
    }
    public void setUsernameFieldLabelText(String text) {
        username.setText(text);
    }
    public void init () {
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (panel);
        frame.pack();
        frame.setVisible (true);
        loginController.changeLanguage(loginController.homeController.language);
    }
    private void setupListeners() {
        loginButton.addActionListener(loginController.createLoginBtnActionListener());
        registerButton.addActionListener(loginController.createRegisterBtnActionListener());
        languages.addActionListener(loginController.createLangChoiceActionListener());
    }
}
