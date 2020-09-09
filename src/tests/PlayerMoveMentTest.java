package tests;

import game.Grid;
import game.Moveable;
import game.Player;
import game.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerMoveMentTest {
    Grid grid;
    Player player;
    Position c;
    Moveable currentCell;


    @Before
    public void setUp() throws Exception {
        grid = new Grid();
        player = new Player(grid, 0, 0);
        c = new Position(0, 0);
    }


    @Test
    public void testMove() throws Exception {
        player.setDirection('R');
        player.setCell(c);
        c = grid.getCell(player.getCell(), player.getDirection());
        assertEquals(c,player.move());
    }

    @Test
    public void testMaxCellsPerMove() throws Exception {
        Position c1 = new Position(0,0);
        Position c2 = new Position(1,0);
        assertEquals(1,grid.distance(c1, c2));
    }


    @Test
    public void testSetDirection() {
        char c = 'R';
        Moveable instance = new Moveable(grid) {
            @Override
            public Position move() {
                // TODO Auto-generated method stub
                return null;
            }};
        instance.setDirection(c);
        assertEquals(c, instance.getDirection());
    }

    @Test
    public void testGetDirection() throws Exception {
        Moveable instance = new Moveable(grid) {
            @Override
            public Position move() {
                // TODO Auto-generated method stub
                return null;
            }
        };

        char expResult = 'R';
        instance.setDirection('R');
        char result = instance.getDirection();
        assertEquals(expResult, result);
    }
}
