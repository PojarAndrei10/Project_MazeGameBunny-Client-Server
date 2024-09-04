package view;
import controller.CRUDOperationsController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CRUDPageView {
    CRUDOperationsController crudOperationsController;
    private JButton refreshButton,createButton,deleteButton,updateButton,backButton;
    private JButton buttonShowStatistics;
    private JButton downloadButton;
    private JTable jTable;
    private JLabel name,email,score,password,role;
    private JTextField textFieldName,textFieldEmail,textFieldScore;
    private JTextField textFieldPassword;
    private JTextField textFieldRole;
    JFrame frame;
    JPanel panel;
    public CRUDPageView(CRUDOperationsController crudOperationsController) {
        this.crudOperationsController = crudOperationsController;
        frame = new JFrame("Admin Page->CRUD");
        panel = new JPanel();
        refreshButton = new JButton ("Refresh");
        createButton = new JButton ("Insert");
        deleteButton = new JButton ("Delete");
        updateButton = new JButton ("Update");
        jTable = new JTable();
        name = new JLabel ("Name");
        email = new JLabel ("Email");
        score = new JLabel ("Score");
        password = new JLabel ("Password");
        role = new JLabel ("Role");
        textFieldName = new JTextField (5);
        textFieldEmail = new JTextField (5);
        textFieldScore = new JTextField (5);
        textFieldPassword = new JTextField (5);
        textFieldRole = new JTextField (5);
        backButton = new JButton("Back");
        backButton.setBackground(Color.YELLOW);
        downloadButton = new JButton ("Download");
        downloadButton.setBackground(Color.YELLOW);
        panel.add(downloadButton);
        downloadButton.setBounds(725, 260, 175, 30);
        buttonShowStatistics = new JButton("Show Score Statistics");
        buttonShowStatistics.setBackground(Color.YELLOW);
        panel.add(buttonShowStatistics);
        buttonShowStatistics.setBounds(725, 315, 175, 30);
        panel.add(backButton);
        panel.setPreferredSize (new Dimension(950, 550));
        panel.setLayout (null);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        panel.add (jScrollPane);

        panel.add (refreshButton);
        refreshButton.setBackground(Color.YELLOW);
        panel.add (createButton);
        createButton.setBackground(Color.YELLOW);
        panel.add (deleteButton);
        deleteButton.setBackground(Color.YELLOW);
        panel.add (updateButton);
        updateButton.setBackground(Color.YELLOW);
        panel.add (name);
        panel.add (email);
        panel.add (score);
        panel.add (password);
        panel.add (role);
        panel.add (textFieldName);
        panel.add (textFieldEmail);
        panel.add (textFieldScore);
        panel.add (textFieldPassword);
        panel.add (textFieldRole);

        jScrollPane.setBounds (10, 10, 525, 240);
        refreshButton.setBounds (725, 10, 175, 30);
        createButton.setBounds (725, 60, 175, 30);
        deleteButton.setBounds (725, 110, 175, 30);
        updateButton.setBounds (725, 160, 175, 30);
        backButton.setBounds(725, 210, 175, 30);

        name.setBounds (300, 300, 100, 25);
        email.setBounds (300, 350, 100, 25);
        score.setBounds (300, 400, 100, 25);
        password.setBounds (300, 450, 100, 25);
        role.setBounds (300, 500, 100, 25);

        textFieldName.setBounds (370, 300, 230, 30);
        textFieldEmail.setBounds (370, 350, 230, 30);
        textFieldScore.setBounds (370, 400, 235, 25);
        textFieldPassword.setBounds (370, 450, 230, 25);
        textFieldRole.setBounds (370, 500, 235, 25);
    }
    public JFrame getFrame() {
        return frame;
    }
    public JTable getJTable() {
        return jTable;
    }
    public JTextField getTextFieldName() {
        return textFieldName;
    }
    public JTextField getTextFieldEmail() {
        return textFieldEmail;
    }
    public JTextField getTextFieldPassword() {
        return textFieldPassword;
    }
    public JTextField getTextFieldScore() {
        return textFieldScore;
    }
    public JTextField getTextFieldRole() {
        return textFieldRole;
    }
    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getButtonShowStatistics() {
        return buttonShowStatistics;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }
    public JButton getRefreshButton() {
        return refreshButton;
    }
    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
    public JButton getDownloadButton() {
        return downloadButton;
    }
    public JLabel getName() {return name;}
    public JLabel getPassword() {return password;}
    public JLabel getRole() {return role;}
    public void init () {
        frame.getContentPane().add (panel);
        frame.pack();
        frame.setVisible (true);
        crudOperationsController.changeLanguage(this.crudOperationsController.homeController.language);
    }
    public void column(String[][] data, String[] column){
        jTable.setModel(new DefaultTableModel(data,column));
    }
}
