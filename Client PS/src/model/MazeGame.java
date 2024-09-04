package model;

public class MazeGame {
    public int level,scoreObtained;
    public int x, y,foodX, foodY;
    public int[][] grid = new int[200][200];
    public MazeGame(int level) {
        this.level = level;
        this.scoreObtained = 0;
        this.x = 0;
        this.y = this.level - 1;
        this.foodX = this.level - 1;
        this.foodY = 0;
        for (int i = 0; i < this.level; i++)
            for (int j = 0; j < this.level; j++)
            {
                grid[i][j] = 0;
            }
        grid[1][1] = 1;grid[2][3] = 1;grid[5][6] = 1;grid[2][4] = 1;grid[2][5] = 1;grid[2][6] = 1;
    }
    public boolean victory(int xx, int yy){
        return xx == foodX && yy == foodY;
    }
    public boolean trap(int xx, int yy){
        return grid[xx][yy] == 1;
    }
    public boolean diagonal(int x, int y){
        return x >= 0 && x < this.level && y >= 0 && y < this.level;
    }
}
