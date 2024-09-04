package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import controller.HomeController;
import model.User;

public class HomePageView {
    HomeController homeController;
    private JButton buttonPlay,buttonCRUDOperations,buttonGraph,buttonLogOut;
    private JLabel labell,level;
    private JComboBox levels;
    private JPanel panel;
    private JFrame frame;
    public HomePageView(HomeController homeController) {
        String[] comboLevelItems = {"5", "6", "7", "8"};
        panel = new JPanel();
        frame = new JFrame("Home Page");
        buttonPlay = new JButton ("Play");
        levels = new JComboBox<>(comboLevelItems);
        level = new JLabel("Level");
        buttonGraph = new JButton ("Show Graph Score");
        labell = new JLabel ();
        buttonCRUDOperations = new JButton ("CRUD operations for user");
        buttonLogOut=new JButton("Exit Application");
        this.homeController = homeController;
        panel.setPreferredSize(new Dimension(944, 563));
        panel.setLayout(null);

        buttonPlay.setBackground(Color.YELLOW);
        buttonCRUDOperations.setBackground(Color.YELLOW);
        buttonGraph.setBackground(Color.YELLOW);
        buttonLogOut.setBackground(Color.YELLOW);
        panel.add(levels);
        panel.add(buttonPlay);
        panel.add(labell);
        panel.add(buttonCRUDOperations);
        panel.add(level);
        panel.add(buttonGraph);
        panel.add(buttonLogOut);

        buttonPlay.setBounds(720, 110, 170, 25);
        buttonCRUDOperations.setBounds(720, 170, 170, 30);
        buttonGraph.setBounds(720, 245, 175, 30);
        buttonLogOut.setBounds(720, 300, 170, 25);
        levels.setBounds (360, 110, 100, 25);
        labell.setBounds (60, 0, 500, 150);
        level.setBounds (275, 110, 100, 25);
    }
    public void setPlayButtonListener(ActionListener listener) {
        buttonPlay.addActionListener(listener);
    }
    public void setCRUDButtonListener(ActionListener listener) {
        buttonCRUDOperations.addActionListener(listener);
    }
    public void setReportButtonListener(ActionListener listener) {
        buttonGraph.addActionListener(listener);
    }
    public String getSelectedLevel() {
        return levels.getSelectedItem().toString();
    }
    public void setLogoutButtonListener(ActionListener listener) {
        buttonLogOut.addActionListener(listener);
    }
    public JFrame getFrame() {
        return frame;
    }
    public void setMessageWelcome(User user){labell.setText("Welcome " + user.getName() + ", your best score obtained is "+ user.getScore());}
    public JButton getButtonPlay() {return buttonPlay;}
    public JButton getButtonCRUDOperations() {return buttonCRUDOperations;}
    public JButton getButtonGraph() {return buttonGraph;}
    public JButton getButtonLogOut() {return buttonLogOut;}
    public JLabel getLevel() {return level;}
    public JLabel getLabell() {return labell;}
    public void init () {
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (panel);
        frame.pack();
        homeController.changeLanguage(homeController.language);
        frame.setVisible (true);
    }
}