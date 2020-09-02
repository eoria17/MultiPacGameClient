package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import game.Grid;
import game.Monster;
import game.Moveable;
import game.Player;
import game.Position;

public class MonsterTest {
	Grid grid;
	Player player;
	Monster monster;
	Position c;
	Position c1;
	char currentDirection;
	Position currentCell;
	Moveable mv;

	@Before
	public void setUp() throws Exception {
		grid = new Grid();
		player = new Player(grid, 0, 0);
		monster = new Monster(grid, player, 0, 0);
		c = new Position(0, 0);
		c1 = new Position(1, 0);
		currentCell = new Position(0, 0);
		mv = new Moveable(grid) {
			@Override
			public Position move() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Test
	public void test() {
		player.setCell(c);
		player.getCell();
		mv.setCell(c1);
		mv.getCell();
		mv.setDirection('R');
		mv.getDirection();
		currentDirection = grid.getBestDirection(mv.getCell(), player.getCell());
		currentCell = grid.getCell(mv.getCell(), mv.getDirection());
		assertEquals(mv.getDirection(),currentDirection);
	}

}
