package singlePlayerGame;

public class Player extends Moveable {
    private boolean readyToStart = false;
    public Player(Grid g, int row, int col) throws Exception
    {
        super(g);
        currentCell = grid.getCell(row, col);
        currentDirection = ' ';
    }
    public Position move()
    {
        currentCell = grid.getCell(currentCell,currentDirection);
        return currentCell;
    }
    public int maxCellsPerMove()
    {
        return 1;
    }
    public  int pointsRemaining()
    {
        return -1;  // not implemented
    }
    public void setReady(boolean val)
    {
        readyToStart = val;
    }
    public boolean isReady()
    {   return readyToStart;
    }
}
