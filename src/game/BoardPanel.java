package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/* This panel represents the game board (grid) 
 * It also responds to game related events
 * The overridden paintcompnent() is called whenever the board
 * or the pieces needs to be updated 
 */
public class BoardPanel extends JPanel implements ActionListener {

   private Player player;
   private Monster monster;
   private Grid grid;
   private Graphics gr;
   private Game game;
   private final int CELLWIDTH = 40;
   private final int CELLHEIGHT = 40;
   private final int LMARGIN = 100;
   private final int TMARGIN = 100;
   
   public BoardPanel(Grid g, Player p, Monster m)
   {
        player = p;
       	grid = g;
       	monster = m;
       	gr = this.getGraphics(); 
   }

   /* responds to various button clicked messages */ 
   public void actionPerformed(ActionEvent e)
   {
	   if (((JButton)e.getSource()).getText().compareTo("up") == 0)
	      player.setDirection('U');
	   else if (((JButton)e.getSource()).getText().compareTo("down") == 0)
		  player.setDirection('D');
	   else if (((JButton)e.getSource()).getText().compareTo("left") == 0)
		  player.setDirection('L');
	   else if (((JButton)e.getSource()).getText().compareTo("right") == 0)
		  player.setDirection('R');
	   else if (((JButton)e.getSource()).getText().compareTo("start") == 0)
		  player.setReady(true);
	}
   
   /* returns the x coordinate based on left margin and cell width */
   private int xCor(int col)
   {
	   return LMARGIN + col * CELLWIDTH;
   }
   /* returns the y coordinate based on top margin and cell height */
   private int yCor(int row)
   {
	   return TMARGIN + row * CELLHEIGHT;
   }
   
   /* Redraws the board and the pieces
    * Called initially and in response to repaint()
    */
   protected void paintComponent(Graphics gr)
   {
        super.paintComponent(gr);
  		Position cells[] = grid.getAllCells();
		Position cell;
        for (int i=0; i<cells.length; i++)
        {
           cell = cells[i];
           if (cell.col%5 == 0 && cell.row%5 == 0)
        	   gr.setColor(Color.cyan);
           else 
        	   gr.setColor(Color.white);
           gr.fillRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);         	
    	   gr.setColor(Color.black);
           gr.drawRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
        }
        cell = player.getCell();
 	    gr.setColor(Color.red);
        gr.fillOval(xCor(cell.col)+CELLWIDTH/8, yCor(cell.row)+CELLWIDTH/8, CELLWIDTH*3/4, CELLHEIGHT*3/4);
 	    gr.setColor(Color.white);
        gr.drawString("P",xCor(cell.col)+CELLWIDTH/3, yCor(cell.row)+2*CELLWIDTH/3);
        
        if (monster.viewable())
        {
           cell = monster.getCell();
    	   gr.setColor(Color.black);
           gr.fillRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
    	   gr.setColor(Color.white);
           gr.drawString("M",xCor(cell.col)+CELLWIDTH/3, yCor(cell.row)+2*CELLWIDTH/3);
        }
    }	
}