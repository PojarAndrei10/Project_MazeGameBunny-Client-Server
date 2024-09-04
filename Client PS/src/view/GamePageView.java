package view;

import controller.GameController;
import model.MazeGrid;
import model.MazeGame;
import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class GamePageView {
    GameController gameController;
    JFrame frame;
    JPanel panel;
    JPanel gamePanel;
    JPanel scorePannel;
    JLabel scoreObtained;

    JLabel rows[][];
    MazeGame mazeGame;
    MazeGrid mazeGrid;
    public GamePageView(MazeGrid mazeGrid, GameController gameController) {
        this.mazeGrid = mazeGrid;
        this.mazeGame = mazeGrid.mazeGame;
        this.gameController = gameController;
    }
    public void startGame(){
        frame = new JFrame("Rabbit Maze Game");
        frame.setFocusable(true);
        frame.addKeyListener(this.gameController);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        gamePanel= new JPanel(new GridLayout(mazeGame.level,mazeGame.level, 5,5));
        rows = new JLabel[mazeGame.level][mazeGame.level];

        scoreObtained = new JLabel();
        scorePannel = new JPanel();
        scorePannel.add(scoreObtained);

        drawGame(mazeGame.level);
        panel.add(gamePanel);
        panel.add(scorePannel);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    public void drawGame(int size){
        int iconSize = 500 / size;
        gamePanel.removeAll();
        Image bunnyI,trapI,squareI,carrotsI;
        scoreObtained.setText("Number of moves is "+mazeGame.scoreObtained);

        ImageIcon bunny,trap,square,carrots,bunnyScale,trapScale,squareScale,carrotsScale;

        for(int i=0;i<mazeGame.level;i++)
            for(int j=0;j<mazeGame.level;j++){
                bunny= new ImageIcon("bunny.png");
                trap = new ImageIcon("trap.png");
                square = new ImageIcon("patrat2.png");
                carrots= new ImageIcon("carrots.png");
                bunnyI = bunny.getImage();trapI = trap.getImage();squareI= square.getImage();carrotsI = carrots.getImage();
                bunnyScale =  new ImageIcon(bunnyI.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                trapScale=  new ImageIcon(trapI.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                squareScale=  new ImageIcon(squareI.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                carrotsScale =  new ImageIcon(carrotsI.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                if (mazeGame.y == j && mazeGame.x == i)
                    rows[i][j] = new JLabel(bunnyScale);
                else if (mazeGame.grid[i][j] == 1)
                    rows[i][j] = new JLabel(trapScale);
                else if (mazeGame.victory(i, j))
                    rows[i][j] = new JLabel(carrotsScale);
                else rows[i][j] = new JLabel(squareScale);
                gamePanel.add(rows[i][j]);
            }
        gamePanel.revalidate();

    }
    public void showPath(int size){
        frame.setVisible(true);
        int iconSize = 500 / size;
        gamePanel.removeAll();
        ImageIcon bunny,trap,square,carrots,shortestPath;
        Image bunnyI,trapI,squareI,carrotsI,shortestPathI;
        for(int i=0;i<mazeGame.level;i++)
            for(int j=0;j<mazeGame.level;j++){
                bunny = new ImageIcon("bunny.png");
                trap = new ImageIcon("trap.png");
                square= new ImageIcon("patrat2.png");
                carrots= new ImageIcon("carrots.png");
                shortestPath = new ImageIcon("bifa.png");
                shortestPathI = shortestPath.getImage();bunnyI= bunny.getImage();trapI = trap.getImage();squareI = square.getImage();
                carrotsI = carrots.getImage();
                ImageIcon bunnyScale =  new ImageIcon(bunnyI.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                ImageIcon trapScale =  new ImageIcon(trapI.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                ImageIcon squareScale =  new ImageIcon(squareI.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                ImageIcon carrotsScale =  new ImageIcon(carrotsI.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                ImageIcon shortestPathScale = new ImageIcon(shortestPathI.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                if (mazeGrid.grid[i][j] > 0)
                    rows[i][j] = new JLabel(shortestPathScale);
                else if (mazeGame.y == j && mazeGame.x == i)
                    rows[i][j] = new JLabel(bunnyScale);
                else if (mazeGame.grid[i][j] == 1)
                    rows[i][j] = new JLabel(trapScale);
                else if (mazeGame.victory(i, j))
                    rows[i][j] = new JLabel(carrotsScale);
                else rows[i][j] = new JLabel(squareScale);
                gamePanel.add(rows[i][j]);
            }
        gamePanel.revalidate();
    }
    public void showLossMessage(String language) {
        Locale locale = new Locale(language.toLowerCase());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Language", locale);
        JOptionPane.showMessageDialog(frame, resourceBundle.getString("loss_message"));
    }
    public void showSuboptimalSolutionMessage(String language) {
        Locale locale = new Locale(language.toLowerCase());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Language", locale);
        JOptionPane.showMessageDialog(frame, resourceBundle.getString("suboptimal_solution_message"));
    }
    public void showVictoryMessage(String language)
    {
        Locale locale = new Locale(language.toLowerCase());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Language", locale);
        JOptionPane.showMessageDialog(frame, resourceBundle.getString("succes"));
    }
    public void numberOfMovesMessage(String language) {
        Locale locale = new Locale(language.toLowerCase());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Language", locale);
        scoreObtained.setText(resourceBundle.getString("numberMoves") + " " + mazeGame.scoreObtained);
    }
    public void closeGame(){
        frame.setVisible(false);
    }
}