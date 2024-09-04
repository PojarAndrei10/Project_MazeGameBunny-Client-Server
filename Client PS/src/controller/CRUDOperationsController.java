package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.*;
import model.User;
import model.UserType;
import server.UserServer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.json.impl.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import view.CRUDPageView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.List;

public class CRUDOperationsController implements Observer{
    private UserServer userServer;
    public HomeController homeController;
    private CRUDPageView crudPageView;
    public CRUDOperationsController(HomeController homeController) {
        this.homeController = homeController;
        this.crudPageView = new CRUDPageView(this);
        this.userServer = new UserServer();
    }
    public void insertUser(User user) {
        userServer.insertUser(user);
        refresh();
    }
    public void updateUser(User user) {
        userServer.updateUser(user);
        refresh();
    }
    public void deleteUser(User user) {
        userServer.deleteUser(user);
        refresh();
    }
    private void downloadUsers() {
        List<User> users = userServer.getUsers();
        saveAsCSV(users, "users.csv");
        saveAsJSON(users, "users.json");
        saveAsXML(users, "users.xml");
        saveAsText(users, "users.doc");
    }
    private void saveAsCSV(List<User> users, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Name,Email,Password,Score,Role\n");
            for (User user : users) {
                writer.write(String.format("%s,%s,%s,%d,%s\n",
                        user.getName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getScore(),
                        user.getRole().toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void saveAsJSON(List<User> users, String filename) {
        try (FileWriter file = new FileWriter(filename)) {
            for (User user : users) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Name", user.getName());
                jsonObject.put("Email", user.getEmail());
                jsonObject.put("Password", user.getPassword());
                jsonObject.put("Score", user.getScore());
                jsonObject.put("Role", user.getRole().toString());
                file.write(jsonObject.toString() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void saveAsXML(List<User> users, String filename) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element rootElement = doc.createElement("Users");
            doc.appendChild(rootElement);
            for (User user : users) {
                Element userElement = doc.createElement("User");
                userElement.appendChild(createElementWithText(doc, "Name", user.getName()));
                userElement.appendChild(createElementWithText(doc, "Email", user.getEmail()));
                userElement.appendChild(createElementWithText(doc, "Password", user.getPassword()));
                userElement.appendChild(createElementWithText(doc, "Score", String.valueOf(user.getScore())));
                userElement.appendChild(createElementWithText(doc, "Role", user.getRole().toString()));
                rootElement.appendChild(userElement);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileWriter(filename));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Element createElementWithText(Document doc, String name, String value) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value));
        return element;
    }
    private void saveAsText(List<User> users, String filename) {
        int maxNameLength = "Name".length();
        int maxEmailLength = "Email".length();
        int maxPasswordLength = "Password".length();
        int maxScoreLength = "Score".length();
        int maxRoleLength = "Role".length();

        for (User user : users) {
            maxNameLength = Math.max(maxNameLength, user.getName().length());
            maxEmailLength = Math.max(maxEmailLength, user.getEmail().length());
            maxPasswordLength = Math.max(maxPasswordLength, user.getPassword().length());
            maxScoreLength = Math.max(maxScoreLength, String.valueOf(user.getScore()).length());
            maxRoleLength = Math.max(maxRoleLength, user.getRole().toString().length());
        }
        String format = String.format("%%-%ds | %%-%ds | %%-%ds | %%-%ds | %%-%ds\n",
                maxNameLength, maxEmailLength, maxPasswordLength, maxScoreLength, maxRoleLength);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(String.format(format, "Name", "Email", "Password", "Score", "Role"));
            writer.write(String.format(format, "-".repeat(maxNameLength), "-".repeat(maxEmailLength),
                    "-".repeat(maxPasswordLength), "-".repeat(maxScoreLength), "-".repeat(maxRoleLength)));
            for (User user : users) {
                writer.write(String.format(format,
                        user.getName(),
                        user.getEmail(),
                        user.getPassword(),
                        String.valueOf(user.getScore()),
                        user.getRole().toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void userSet() {
        List<User> users = userServer.getUsers();
        String[][] s = users.stream()
                .map(user -> new String[]{
                        user.getName(),
                        user.getEmail(),
                        user.getPassword(),
                        String.valueOf(user.getScore()),
                        user.getRole().toString()
                })
                .toArray(String[][]::new);
        String[] cols = {"Name", "Email", "Password", "Score", "Role"};
        crudPageView.column(s, cols);
    }
    public void refresh() {
        userSet();
    }
    public void showScoreStatistics() {
        UserServer userRepo = new UserServer();
        List<User> users = userRepo.getUsers();
        users.sort(Comparator.comparingInt(User::getScore).reversed());
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (User user : users)
            dataset.setValue(user.getName(), user.getScore());
        JFreeChart chart = ChartFactory.createPieChart("Player Scores", dataset, true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionOutlinesVisible(false);
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setCircular(true);

        ChartFrame frame = new ChartFrame("Player Scores Statistics", chart);
        frame.pack();
        frame.setVisible(true);
    }
    public void CRUDOperationsStart() {
        crudPageView.init();
        userSet();
        crudPageView.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = crudPageView.getJTable().getSelectedRow();
                User user = new User();
                String name = crudPageView.getJTable().getValueAt(i, 0).toString();
                String email = crudPageView.getJTable().getValueAt(i, 1).toString();
                String password = crudPageView.getJTable().getValueAt(i, 2).toString();
                String role = crudPageView.getJTable().getValueAt(i, 4).toString();
                int score = Integer.parseInt(crudPageView.getJTable().getValueAt(i, 3).toString());
                if (role.equals("ADMIN"))
                    user.setRole(UserType.ADMIN);
                else
                    user.setRole(UserType.PLAYER);
                user.setPassword(password);
                user.setName(name);
                user.setEmail(email);
                user.setScore(score);
                deleteUser(user);
            }
        });
        crudPageView.getButtonShowStatistics().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScoreStatistics();
            }
        });
        crudPageView.getDownloadButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadUsers();
            }
        });
        crudPageView.getJTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = crudPageView.getJTable().getSelectedRow();
                if (selectedRow != -1) {
                    String name = crudPageView.getJTable().getValueAt(selectedRow, 0).toString();
                    String email = crudPageView.getJTable().getValueAt(selectedRow, 1).toString();
                    String password = crudPageView.getJTable().getValueAt(selectedRow, 2).toString();
                    String score = crudPageView.getJTable().getValueAt(selectedRow, 3).toString();
                    String role = crudPageView.getJTable().getValueAt(selectedRow, 4).toString();

                    crudPageView.getTextFieldName().setText(name);
                    crudPageView.getTextFieldEmail().setText(email);
                    crudPageView.getTextFieldPassword().setText(password);
                    crudPageView.getTextFieldScore().setText(score);
                    crudPageView.getTextFieldRole().setText(role);
                }
            }
        });
        crudPageView.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = crudPageView.getJTable().getSelectedRow();
                if (selectedRow != -1) {
                    String name = crudPageView.getTextFieldName().getText();
                    String email = crudPageView.getTextFieldEmail().getText();
                    String password = crudPageView.getTextFieldPassword().getText();
                    int score = Integer.parseInt(crudPageView.getTextFieldScore().getText());
                    String role = crudPageView.getTextFieldRole().getText();
                    UserType userType = role.equals("ADMIN") ? UserType.ADMIN : UserType.PLAYER;
                    crudPageView.getJTable().setValueAt(name, selectedRow, 0);
                    crudPageView.getJTable().setValueAt(email, selectedRow, 1);
                    crudPageView.getJTable().setValueAt(password, selectedRow, 2);
                    crudPageView.getJTable().setValueAt(score, selectedRow, 3);
                    crudPageView.getJTable().setValueAt(role, selectedRow, 4);
                    User user = new User(name, email, score, password);
                    user.setRole(userType);
                    updateUser(user);
                }
            }
        });
        crudPageView.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        crudPageView.getCreateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                String name = crudPageView.getTextFieldName().getText();
                String password = crudPageView.getTextFieldPassword().getText();
                String email = crudPageView.getTextFieldEmail().getText();
                String role = crudPageView.getTextFieldRole().getText();
                int score = Integer.parseInt(crudPageView.getTextFieldScore().getText());
                if (role.equals("ADMIN"))
                    user.setRole(UserType.ADMIN);
                else
                    user.setRole(UserType.PLAYER);
                user.setPassword(password);
                user.setName(name);
                user.setEmail(email);
                user.setScore(score);
                insertUser(user);
            }
        });
        crudPageView.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crudPageView.getFrame().dispose();
            }
        });
    }
    public void changeLanguage(String language) {
        if (language != null && crudPageView != null) {
            Locale locale = new Locale(language.toLowerCase());
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Language", locale);
            crudPageView.getUpdateButton().setText(resourceBundle.getString("buttonUpdate"));
            crudPageView.getDeleteButton().setText(resourceBundle.getString("buttonDelete"));
            crudPageView.getCreateButton().setText(resourceBundle.getString("buttonInsert"));
            crudPageView.getRefreshButton().setText(resourceBundle.getString("buttonRefresh"));
            crudPageView.getDownloadButton().setText(resourceBundle.getString("buttonDownload"));
            crudPageView.getBackButton().setText(resourceBundle.getString("buttonBack"));
            crudPageView.getName().setText(resourceBundle.getString("nameField"));
            crudPageView.getPassword().setText(resourceBundle.getString("passwordF"));
            crudPageView.getRole().setText(resourceBundle.getString("roleField"));
            crudPageView.getButtonShowStatistics().setText(resourceBundle.getString("pieChart"));
        }
    }
    @Override
    public void update(Observable o, Object arg) {

    }
}
