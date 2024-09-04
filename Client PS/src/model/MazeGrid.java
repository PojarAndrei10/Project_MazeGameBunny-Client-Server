package model;

import java.util.PriorityQueue;
public class MazeGrid {
    public MazeGame mazeGame;
    public int[][] grid = new int[200][200];
    public MazeGrid() {
        mazeGame = new MazeGame(5);
    }
    public boolean isWin(int x, int y){
        return mazeGame.victory(x, y);
    }
    public boolean isTrap(int x, int y){
        return mazeGame.trap(x, y);
    }
    public boolean isValidPosition(int x, int y){
        return mazeGame.diagonal(x, y);
    }
    public void newGame(int level){
        mazeGame = new MazeGame(level);
    }
    public int getShortestPathAlg(){
        PriorityQueue<Cell> priorityQueue = new PriorityQueue<Cell>();
        Cell cell = new Cell();
        Cell cell2;
        Cell neigh;
        int ii = 0;
        int dirX[] = {-1, -1, 1, 1};
        int dirY[] = {1, -1, -1, 1};
        for (int i = 0; i < 100; i++)
            for (int j = 0; j < 100; j++)
                grid[i][j] = 0;
        cell.x = 0;cell.level = 0;cell.y = mazeGame.level - 1;
        cell.key = Math.abs(mazeGame.foodX - cell.x) + Math.abs(mazeGame.foodY - cell.y);
        priorityQueue.add(cell);
        while(!priorityQueue.isEmpty()){
            cell2 = priorityQueue.poll();
            if (mazeGame.victory(cell2.x, cell2.y)) {
                grid[cell2.x][cell2.y] = ii+1;
                break;
            }
            ii++;
            int xxN,yyN;
            for (int i = 0; i < 4; i++){
                xxN = cell2.x + dirX[i];
                yyN = cell2.y + dirY[i];
                grid[cell2.x][cell2.y] = ii;
                if (mazeGame.diagonal(xxN, yyN) && grid[xxN][yyN] == 0 && !isTrap(xxN, yyN))
                {
                    neigh = new Cell();
                    neigh.x = xxN;
                    neigh.y = yyN;
                    neigh.key = Math.abs(mazeGame.foodX - neigh.x) + Math.abs(mazeGame.foodY - neigh.y);
                    priorityQueue.add(neigh);
                }
            }
        }
        return ii;
    }
    class Cell implements Comparable<Cell>{
        public Integer x, y, key, level;
        @Override
        public int compareTo(Cell o) {
            return this.key.compareTo(o.key);
        }
    }
}
