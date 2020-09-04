package game;

import game.*;

import javax.swing.*;

import java.awt.*;

/* This class is the main System level class which creates all the objects
 * representing the game logic (model) and the panel for user interaction.
 * It also implements the main game loop
 */

public class Game extends JFrame {

    private final int TIMEALLOWED = 100;

    private JButton start = new JButton("start");
    private JLabel mLabel = new JLabel("Time Remaining : "+TIMEALLOWED);

    private Grid grid;
    private Player player;
    private Monster monster;
    private BoardPanel bp;


    /* This constructor creates the main model objects and the panel used for UI.
     * It throws an exception if an attempt is made to place the player or the
     * monster in an invalid location.
     */
    public Game() throws Exception
    {
        grid = new Grid();
        player = new Player(grid,0,0);
        monster = new Monster(grid,player,5,5);
        bp = new BoardPanel(grid,player,monster);

        // Create a separate panel and add all the buttons
        JPanel panel = new JPanel();
        panel.add(start);
        panel.add(mLabel);

        // add panels to frame
        add (bp, BorderLayout.CENTER);
        add (panel,BorderLayout.SOUTH);

        bp.setFocusable(true);

        (new KeyBoard()).keyBoardListener(bp);
        (new KeyBoard()).keyEventRegister(bp);
        start.addActionListener(bp);
    }

    // method to delay by specified time in ms
    public void delay(int time)
    {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /* This method waits until play is ready (until start button is pressed)
     * after which it updates the moves in turn until time runs out (player won)
     * or player is eaten up (player lost).
     */
    public String play()
    {
        int time = 0;
        String message;
        player.setDirection(' '); // set to no direction
        while (!player.isReady())
            delay(100);
        do {
            bp.requestFocusInWindow();
            Position newPlayerCell = player.move();
            if (newPlayerCell == monster.getCell())
                break;
            player.setDirection(' ');   // reset to no direction

            Position newMonsterCell = monster.move();
            if (newMonsterCell == player.getCell())
                break;

            // update time and repaint
            time++;
            mLabel.setText("Time Remaining : "+ (TIMEALLOWED - time));
            delay(1000);
            bp.repaint();

        } while (time < TIMEALLOWED);

        if (  time < TIMEALLOWED)			// players has been eaten up
            message =  "Player Lost";
        else
            message =  "Player Won";

        mLabel.setText(message);
        return message;
    }

    public static void main(String args[]) throws Exception
    {
        Game game = new Game();
        game.setTitle("Monster Game");
        game.setSize(700,700);
        game.setLocationRelativeTo(null);  // center the frame
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
        game.play();
    }
}
