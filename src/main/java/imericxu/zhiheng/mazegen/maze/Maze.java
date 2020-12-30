package imericxu.zhiheng.mazegen.maze;

import java.util.ArrayList;
import java.util.Random;

/**
 * A maze composed of squares
 */
public abstract class Maze extends Orthogonal
{
	protected final Cell[][] grid;
	/**
	 * Random number generator (better than Math.random())
	 */
	protected final Random r;
	
	/**
	 * Generates a rectangular maze
	 */
	protected Maze(int rows, int cols)
	{
		grid = new Cell[rows][cols];
		r = new Random();
		initializeGrid();
		randomizeStartAndEnd();
	}
    
    
    /* * * * * * * * * * * * * * * * * * * * *
    Methods
    * * * * * * * * * * * * * * * * * * * * */
	
	public Cell[][] getGrid()
	{
		return grid;
	}
	
	/**
	 * @param cell cell to get neighbors of
	 * @return an array of cells on the four sides of this cell;
	 * null if out of bounds
	 */
	public ArrayList<Cell> getNeighbors(Cell cell)
	{
		var neighbors = new ArrayList<Cell>();
		int row = cell.getRow();
		int col = cell.getCol();
		
		if (row > 0) neighbors.add(grid[row - 1][col]);
		if (row < grid.length - 1) neighbors.add(grid[row + 1][col]);
		if (col > 0) neighbors.add(grid[row][col - 1]);
		if (col < grid[0].length - 1) neighbors.add(grid[row][col + 1]);
		
		return neighbors;
	}
	
	protected void setWallsBetween(Cell c1, Cell c2, boolean hasWall)
	{
		if (c1.getRow() < c2.getRow())
		{
			c1.setWall(Cell.BOTTOM, hasWall);
			c2.setWall(Cell.TOP, hasWall);
		}
		else if (c1.getRow() > c2.getRow())
		{
			c1.setWall(Cell.TOP, hasWall);
			c2.setWall(Cell.BOTTOM, hasWall);
		}
		else if (c1.getCol() < c2.getCol())
		{
			c1.setWall(Cell.RIGHT, hasWall);
			c2.setWall(Cell.LEFT, hasWall);
		}
		else
		{
			c1.setWall(Cell.LEFT, hasWall);
			c2.setWall(Cell.RIGHT, hasWall);
		}
	}
	
	protected void queueUpdate(Cell cell)
	{
		changeList.add(cell);
	}
    
    /* * * * * * * * * * * * * * * * * * * * *
    Helper Methods
    * * * * * * * * * * * * * * * * * * * * */
	
	private void initializeGrid()
	{
		for (int row = 0; row < grid.length; ++row)
		{
			for (int col = 0; col < grid[0].length; ++col)
			{
				grid[row][col] = new Cell(row, col);
			}
		}
	}
	
	private void randomizeStartAndEnd()
	{
		int sRow, sCol, eRow, eCol;
		int sWall, eWall;
		
		int random = r.nextInt(4);
		switch (random)
		{
			case 0 -> { // Up
				sRow = 0;
				sCol = r.nextInt(grid[0].length);
				sWall = Cell.TOP;
				
				eRow = grid.length - 1;
				eCol = r.nextInt(grid[0].length);
				eWall = Cell.BOTTOM;
			}
			case 1 -> { // Down
				sRow = grid.length - 1;
				sCol = r.nextInt(grid[0].length);
				sWall = Cell.BOTTOM;
				
				eRow = 0;
				eCol = r.nextInt(grid[0].length);
				eWall = Cell.TOP;
			}
			case 2 -> { // Left
				sRow = r.nextInt(grid.length);
				sCol = 0;
				sWall = Cell.LEFT;
				
				eRow = r.nextInt(grid.length);
				eCol = grid[0].length - 1;
				eWall = Cell.RIGHT;
			}
			case 3 -> { // Right
				sRow = r.nextInt(grid.length);
				sCol = grid[0].length - 1;
				sWall = Cell.RIGHT;
				
				eRow = r.nextInt(grid.length);
				eCol = 0;
				eWall = Cell.LEFT;
			}
			default -> throw new IllegalStateException("Unexpected value: " + random);
		}
		
		start = grid[sRow][sCol];
		end = grid[eRow][eCol];
		start.setWall(sWall, false);
		end.setWall(eWall, false);
	}
}
