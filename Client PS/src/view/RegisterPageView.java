package view;

import controller.RegisterController;
import model.UserType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterPageView {
    private RegisterController registerController;
    private JButton buttonRegister;
    private JLabel email,password,name,role;
    private JTextField textFieldEmail;
    private JTextField textFieldPassword;
    private JTextField textFieldName,textFieldRole;
    JFrame frame;
    JPanel panel;
    public RegisterPageView(RegisterController registerController) {
        this.registerController = registerController;
        frame = new JFrame("Register Page");
        panel = new JPanel();
        buttonRegister = new JButton ("Register");
        email = new JLabel ("Email");
        textFieldEmail = new JTextField (5);
        password = new JLabel ("Password");
        textFieldPassword = new JTextField (5);
        name = new JLabel ("Name");
        textFieldName = new JTextField (5);
        role = new JLabel ("Role");
        textFieldRole = new JTextField (5);

        panel.setPreferredSize (new Dimension(944, 563));
        panel.setLayout (null);
        buttonRegister.setBackground(Color.YELLOW);
        panel.add (buttonRegister);
        panel.add (email);
        panel.add (textFieldEmail);
        panel.add (password);
        panel.add (textFieldPassword);
        panel.add (name);
        panel.add (textFieldName);
        panel.add (role);
        panel.add (textFieldRole);

        buttonRegister.setBounds (45, 175, 210, 55);
        email.setBounds (10, 20, 100, 25);
        textFieldEmail.setBounds (110, 20, 150, 25);
        password.setBounds (10, 50, 100, 25);
        textFieldPassword.setBounds (110, 50, 150, 25);
        name.setBounds (5, 80, 100, 25);
        textFieldName.setBounds (110, 85, 150, 25);
        role.setBounds (5, 115, 100, 25);
        textFieldRole.setBounds (110, 120, 150, 25);
    }
    public void init () {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        registerController.changeLanguage(registerController.homeController.getLanguage());
    }
    public String getEmail(){return textFieldEmail.getText();}
    public String getPassword(){return textFieldPassword.getText();}
    public String getName(){return textFieldName.getText();}
    public void close(){frame.setVisible(false);}
    public void setButtonText(String text) {buttonRegister.setText(text);}
    public void setPasswordLabel(String text) {password.setText(text);}
    public void setNameLabel(String text) {name.setText(text);}
    public void setRoleLabel(String text) {role.setText(text);}
    public JTextField getTextFieldRole() {return textFieldRole;}
    public void setRegisterButtonListener(ActionListener listener) {buttonRegister.addActionListener(listener);}
}