package controller;
import model.User;
import model.UserType;
import server.UserServer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import view.HomePageView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class HomeController implements Observer{
    public User userr;
    public String language;
    private HomePageView homePageView;
    private GameController gameController;
    private LoginController loginController;
    private RegisterController registerController;
    CRUDOperationsController crudOperationsController;
    public HomeController() {
        homePageView = new HomePageView(this);
        gameController = new GameController(this);
        userr = new User();
        registerController = new RegisterController(this);
        loginController = new LoginController(userr,this);
        crudOperationsController = new CRUDOperationsController(this);
        setupEventHandlers();
    }
    private void setupEventHandlers() {
        homePageView.setPlayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playRabbitMazeGame();
            }
        });
        homePageView.setCRUDButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCRUDoperations();
            }
        });
        homePageView.setReportButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGraphScore();
            }
        });
        homePageView.setLogoutButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }
    public void changeLanguage(String language) {
        if (language != null && homePageView != null) {
            Locale locale = new Locale(language.toLowerCase());
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Language", locale);
            homePageView.getButtonPlay().setText(resourceBundle.getString("buttonPlayRabbitGame"));
            homePageView.getButtonLogOut().setText(resourceBundle.getString("buttonLogOut"));
            homePageView.getButtonCRUDOperations().setText(resourceBundle.getString("buttonCRUDOperations"));
            homePageView.getButtonGraph().setText(resourceBundle.getString("buttonChart"));
            homePageView.getLevel().setText(resourceBundle.getString("levels"));
        }
    }
    private void playRabbitMazeGame() {
        int level = Integer.parseInt(homePageView.getSelectedLevel());
        gameController.init(level, language);
    }
    private void showGraphScore() {
        UserServer userRepo = new UserServer();
        List<User> userList = userRepo.getUsers();
        Map<Integer, List<String>> scoreNamesMap = new HashMap<>();
        for (User user : userList) {
            int score = user.getScore();
            if (score >= 0 && score <= 100) {
                String name = user.getName();
                if (!scoreNamesMap.containsKey(score)) {
                    scoreNamesMap.put(score, new ArrayList<>());
                }
                scoreNamesMap.get(score).add(name);
            }
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int score : scoreNamesMap.keySet()) {
            List<String> names = scoreNamesMap.get(score);
            for (String name : names) {
                dataset.addValue(score, "Player Scores", name);
            }
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Player Scores",
                "Player Name",
                "Score",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        chart.setBackgroundPaint(Color.YELLOW);
        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame chartFrame = new JFrame("Player Scores");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.add(chartPanel);
        chartFrame.pack();
        chartFrame.setVisible(true);
    }
    public void noButtonCrud() {
        if (homePageView != null) {
            homePageView.getButtonCRUDOperations().setVisible(false);
        }
    }
    public void setGreeting(User user) {
        if (homePageView != null) {
            String lang = language;
            Locale locale = new Locale(lang.toLowerCase());
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Language", locale);
            homePageView.getLabell().setText(resourceBundle.getString("message") + " " + user.getScore());
        }
    }
    public void loginSuccessful(User user){
        this.userr = user;
        homePageView.setMessageWelcome(user);
        setGreeting(userr);
        if(user.getRole() == UserType.PLAYER){
            noButtonCrud();
        }
        homePageView.init();
    }
    public void registerDone(){
        loginController.startLoginUser();
        registerController.close();
    }
    public void updateGreeting(){
        homePageView.setMessageWelcome(userr);
        setGreeting(userr);
    }
    private void logout() {
        homePageView.getFrame().dispose();
    }
    public void start(){
        loginController.startLoginUser();
    }
    public void startRegister(){
        loginController.close();
        registerController.setRegisterPageView();
    }
    public void startCRUDoperations(){
        crudOperationsController.CRUDOperationsStart();
    }
    public String getLanguage() {
        return language;
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}
