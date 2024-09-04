package controller;
import model.MazeGrid;
import model.MazeGame;
import server.UserServer;
import view.GamePageView;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

public class GameController implements KeyListener,Observer{
    UserServer userServer;
    public String language;
    boolean okk = false;
    MazeGrid mazeGrid;
    GamePageView gamePageView;
    public HomeController homeController;
    public GameController(HomeController homeController) {
        this.homeController = homeController;
        this.userServer = new UserServer();
    }
    public void init(int lvl, String language){
        okk = false;
        this.language = language;
        this.mazeGrid = new MazeGrid();
        mazeGrid.newGame(lvl);
        this.gamePageView = new GamePageView(this.mazeGrid, this);
        gamePageView.startGame();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        MazeGame mazeGame = mazeGrid.mazeGame;
        int xx = mazeGame.x;
        int yy = mazeGame.y;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                xx += 1;
                yy -= 1;
                break;
            case KeyEvent.VK_UP:
                xx -= 1;
                yy -= 1;
                break;
            case KeyEvent.VK_RIGHT:
                xx += 1;
                yy += 1;
                break;
            case KeyEvent.VK_LEFT:
                xx -= 1;
                yy += 1;
                break;
        }
        int event = ok(xx, yy);
        switch (event) {
            case 1:
                okk = true;
                gamePageView.showLossMessage(homeController.language);
                gamePageView.closeGame();
                break;
            case 2:
                okk = true;
                int len = mazeGrid.getShortestPathAlg();
                if (mazeGame.scoreObtained <= len) {

                    gamePageView.drawGame(mazeGame.level);
                    gamePageView.showVictoryMessage(homeController.language);
                    mazeGame.scoreObtained = len;
                } else {
                    gamePageView.showSuboptimalSolutionMessage(homeController.language);
                    gamePageView.showPath(mazeGame.level);
                }
                if (mazeGrid.isWin(xx, yy)) {
                    int finalScore = (mazeGrid.getShortestPathAlg() * 100) / mazeGame.scoreObtained;
                    homeController.userr.setScore(finalScore);
                    homeController.updateGreeting();
                    userServer.updateScore(homeController.userr); // Actualizam scorul in baza de date
                }
                break;
        }
        if (!mazeGame.diagonal(xx, yy))
            return;
        mazeGame.x = xx;
        mazeGame.y = yy;
        mazeGame.scoreObtained++;
        if (!okk)
            gamePageView.drawGame(mazeGame.level);
    }
    @Override
    public void keyTyped(KeyEvent e) {
        //
    }
    @Override
    public void keyReleased(KeyEvent e) {
        //
    }
    private int ok(int x, int y){
        if (!mazeGrid.isValidPosition(x, y))
            return 0;
        if (mazeGrid.isTrap(x, y))
            return 1;
        if (mazeGrid.isWin(x, y))
            return 2;
        return 0;
    }
    @Override
    public void update(Observable o, Object arg) {
    }
}
