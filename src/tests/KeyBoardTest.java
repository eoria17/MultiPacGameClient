package tests;

import game.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class KeyBoardTest {
    Player player;
    HashMap<Integer, Player> players;
    Monster[] monster;
    Grid grid;
    BoardPanel bp;
    KeyBoard keyBoard;

    @Before
    public void setUp() throws Exception {
        grid = new Grid();
        player = new Player(grid, 0 , 0);
        players = new HashMap<Integer, Player>();
        monster = new Monster[]{new Monster(grid, player, 5, 5),
                new Monster(grid, player, 5, 5)};
        bp = new BoardPanel(grid, players, monster);
        keyBoard = new KeyBoard(bp);
        keyBoard.keyEventRegister(bp);
    }

    @Test
    public void getKey() {
        Assert.assertEquals("left", keyBoard.getKey(37));
        Assert.assertEquals("up", keyBoard.getKey(38));
        Assert.assertEquals("right", keyBoard.getKey(39));
        Assert.assertEquals("down", keyBoard.getKey(40));
        Assert.assertNull(keyBoard.getKey(49));
    }
}
