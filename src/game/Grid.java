package game;

import clientConnection.ConnectionHandler;

import java.io.Serializable;

/* This class uses a partially hollow 2D array to represent the games grid.
 * Row and column corresponds to the 2D array row and column respectively.
 * Hence, for the standard grid both row and column must be in the range
 * 0 to 10. Furthermore, either row or column must be 0, 5 or 10.
 */

public class Grid implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final int numberOfRow = 11;
	private static final int numberOfColumn = 11;
	private static final int totalCells = numberOfColumn * numberOfRow;

	Position cells[];
	Position cells2D[][] = new Position[numberOfRow][numberOfColumn];

	public Grid() {
		cells = new Position[getCellCount()];
		int k = 0;
		for (int i = 0; i < numberOfRow; i++)
			for (int j = 0; j < numberOfColumn; j++)
				if (!isObstacle(j, i)|| isPlayerPosition(j, i)) {
					cells2D[i][j] = new Position(i, j);
					cells[k++] = cells2D[i][j];
				}
	}

	private int getCellCount() {
		if (ConnectionHandler.gridObstacles == null) {
			return totalCells;
		}
		return totalCells - ConnectionHandler.gridObstacles.length;
	}


	//Li
		private boolean isPlayerPosition(int i, int j) {
			for (Position p : ConnectionHandler.allPlayersPosition.values()) {
				if (p != null && i == p.col && j == p.row) {
					return true;
				}
			}
			return false;

		}

	private boolean isObstacle(int i, int j) {

		if(isPlayerPosition(i,j)) {
			return true;
		}

		if (ConnectionHandler.gridObstacles == null) {
			return false;
		}
		for (int k = 0; k < ConnectionHandler.gridObstacles.length; k++) {
			Position pos = ConnectionHandler.gridObstacles[k];
			if (i == pos.col && j == pos.row) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Returns a reference to the specified cell. row and cell must be in the range
	 * 0 .. 10 and either row or col must be 0, 5 or 10.
	 */
	public Position getCell(int row, int col) throws Exception {
		if ((isObstacle(col,row )&& !isPlayerPosition(col,row))|| row < 0 || row > numberOfRow - 1 || col < 0 || col > numberOfColumn - 1)
			throw new Exception("Invalid Coordiantes row = " + row + " column " + col);
		return cells2D[row][col];
	}

	/*
	 * Returns the cell in the specified direction of the given cell. Valid
	 * direction must be either 'R', 'L', 'U', 'D' or ' '. A null value will be
	 * returned if attempt to get a non-existent cell.
	 */
	public Position getCell(Position cell, char direction) {
		if (direction == ' ')
			return cell;
		if (direction == 'U') {
			if (!isObstacle(cell.col, cell.row - 1) && cell.row > 0)
				return cells2D[cell.row - 1][cell.col];
			return cell;
		} else if (direction == 'D') {
			if (!isObstacle(cell.col, cell.row + 1) && cell.row < numberOfRow - 1)
				return cells2D[cell.row + 1][cell.col];
			return cell;
		} else if (direction == 'L') {
			if (!isObstacle(cell.col - 1, cell.row) && cell.col > 0)
				return cells2D[cell.row][cell.col - 1];
			return cell;
		} else if (direction == 'R') {
			if (!isObstacle(cell.col + 1, cell.row) && cell.col < numberOfColumn - 1)
				return cells2D[cell.row][cell.col + 1];
			return cell;
		}
		return null;
	}

	public Position[] getAllCells() {
		return cells;
	}

	/*
	 * helper method to check whether val is in the range a to b
	 */
	private boolean inBetween(int val, int a, int b) {
		if (val >= a && val <= b)
			return true;
		else
			return false;

	}



	/*
	 * returns the best direction from source cell to the target cell. Assumed cells
	 * passed are valid cells in the grid. you need to verify this method
	 */
	public char getBestDirection(Position from, Position to) {

		if (from.row == to.row) {
			if (from.col < to.col)
				return 'R';
			else if (from.col > to.col)
				return 'L';
		} else if (from.col == to.col) {
			if (from.row < to.row)
				return 'D';
			else if (from.row > to.row)
				return 'U';
		}

		int row = to.row;
		int col = to.col;

		if (inBetween(to.row % 5, 1, 2))
			row = to.row / 5 * 5;
		else if (inBetween(to.row % 5, 3, 4))
			row = to.row / 5 * 5 + 5;
		if (inBetween(to.col % 5, 1, 2))
			col = to.col / 5 * 5;
		else if (inBetween(to.col % 5, 3, 4))
			col = to.col / 5 * 5 + 5;

		if (from.row % 5 == 0)
			if (from.col < col)
				return 'R';
			else if (from.col > col)
				return 'L';
		if (from.col % 5 == 0)
			if (from.row < row)
				return 'D';
			else if (from.row > row)
				return 'U';
		return ' ';
	}

	/* A helper method to get the absolute value */
	private int abs(int x) {
		if (x >= 0)
			return x;
		else
			return -x;
	}

	/* A helper method to get the minimum of three values */
	private int min(int x, int y, int z) {
		if (x <= y && x <= z)
			return x;
		if (y <= z && y <= x)
			return y;
		return z;
	}

	/*
	 * A method to get the shortest distance from one cell to another Assumed cells
	 * are valid cells in the grid
	 */
	public int distance(Position from, Position to) {
		int d = 0;
		// compute minimum horizontal distance:
		if (from.row == to.row)
			d += abs(to.col - from.col);
		else
			d += min(from.col + to.col, abs(from.col - 5) + abs(to.col - 5), abs(from.col - 10) + abs(to.col - 10));

		// compute minimum vertical distance as follows:
		if (from.col == to.col)
			d += abs(to.row - from.row);
		else
			d += min(from.row + to.row, abs(from.row - 5) + abs(to.row - 5), abs(from.row - 10) + abs(to.row - 10));
		return d;
	}

	/* Test harness for Grid */
	public static void main(String args[]) throws Exception {
		Grid grid = new Grid();
		Position c1 = grid.getCell(0, 0);
		Position c2 = grid.getCell(10, 10);
		Position c3 = grid.getCell(0, 2);
		Position c4 = grid.getCell(2, 0);
		Position c5 = grid.getCell(8, 5);

		System.out.println("Distance from (0,0) to (10,10) is " + grid.distance(c1, c2));
		System.out.println("Distance from (0,0) to (8,5) is " + grid.distance(c1, c5));
		System.out.println("From (0,0) to (0,2) best direction is " + grid.getBestDirection(c1, c3));
		System.out.println("From (0,0) to (2,0) best direction is " + grid.getBestDirection(c1, c4));
	}
}