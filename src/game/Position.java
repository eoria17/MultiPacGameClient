package game;

/* This class represents the individual cell in the grid.
 */

public class Position {
   protected int row;
   protected int col;
   boolean gotGold = true;
   public Position(int i, int j)
   {
	  row = i;
	  col = j;
   }
}
