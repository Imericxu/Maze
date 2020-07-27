package imericxu.zhiheng.mazegen.maze_types.orthogonal;

import java.util.Random;

/**
 * A maze composed of squares
 */
public abstract class MazeOrthogonal
{
    protected final OCell[][] grid;
    protected OCell start;
    protected OCell end;
    /**
     * Random number generator (better than Math.random())
     */
    protected Random r;
    
    /**
     * Generates a rectangular maze
     */
    protected MazeOrthogonal(int rows, int cols)
    {
        grid = new OCell[rows][cols];
        r = new Random();
        initializeGrid();
        randomizeStartAndEnd();
    }
    
    /* * * * * * * * * * * * * * * * * * * * *
    Methods
    * * * * * * * * * * * * * * * * * * * * */
    
    public abstract void step();
    
    public OCell[][] getGrid()
    {
        return grid;
    }
    
    protected void removeWallsBetween(OCell c1, OCell c2)
    {
        if (c1.getRow() < c2.getRow())
        {
            c1.setWall(OCell.BOTTOM, false);
            c2.setWall(OCell.TOP, false);
        }
        else if (c1.getRow() > c2.getRow())
        {
            c1.setWall(OCell.TOP, false);
            c2.setWall(OCell.BOTTOM, false);
        }
        else if (c1.getCol() < c2.getCol())
        {
            c1.setWall(OCell.RIGHT, false);
            c2.setWall(OCell.LEFT, false);
        }
        else
        {
            c1.setWall(OCell.LEFT, false);
            c2.setWall(OCell.RIGHT, false);
        }
    }
    
    /**
     * @param OCell cell to get neighbors of
     * @return an array of cells on the four sides of this cell;
     * null if out of bounds
     */
    protected OCell[] getNeighbors(OCell OCell)
    {
        return new OCell[]{above(OCell), below(OCell), left(OCell), right(OCell)};
    }
    
    protected OCell above(OCell OCell)
    {
        if (OCell.getRow() < 1) return null;
        return grid[OCell.getRow() - 1][OCell.getCol()];
    }
    
    protected OCell below(OCell OCell)
    {
        if (OCell.getRow() > grid.length - 2) return null;
        return grid[OCell.getRow() + 1][OCell.getCol()];
    }
    
    protected OCell left(OCell OCell)
    {
        if (OCell.getCol() < 1) return null;
        return grid[OCell.getRow()][OCell.getCol() - 1];
    }
    
    protected OCell right(OCell OCell)
    {
        if (OCell.getCol() > grid[OCell.getRow()].length - 2) return null;
        return grid[OCell.getRow()][OCell.getCol() + 1];
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
                grid[row][col] = new OCell(row, col);
            }
        }
    }
    
    private void randomizeStartAndEnd()
    {
        int sRow, sCol, eRow, eCol;
        int sWall, eWall;
        
        switch ((int) (Math.random() * 4))
        {
        case 0 -> { // Up
            sRow = 0;
            sCol = (int) (Math.random() * grid[0].length);
            sWall = OCell.TOP;
            
            eRow = grid.length - 1;
            eCol = (int) (Math.random() * grid[0].length);
            eWall = OCell.BOTTOM;
        }
        case 1 -> { // Down
            sRow = grid.length - 1;
            sCol = (int) (Math.random() * grid[0].length);
            sWall = OCell.BOTTOM;
            
            eRow = 0;
            eCol = (int) (Math.random() * grid[0].length);
            eWall = OCell.TOP;
        }
        case 2 -> { // Left
            sRow = (int) (Math.random() * grid.length);
            sCol = 0;
            sWall = OCell.LEFT;
            
            eRow = (int) (Math.random() * grid.length);
            eCol = grid[0].length - 1;
            eWall = OCell.RIGHT;
        }
        case 3 -> { // Right
            sRow = (int) (Math.random() * grid.length);
            sCol = grid[0].length - 1;
            sWall = OCell.RIGHT;
            
            eRow = (int) (Math.random() * grid.length);
            eCol = 0;
            eWall = OCell.LEFT;
        }
        default -> throw new IllegalStateException("Unexpected value: " + (int) (Math.random() * 4));
        }
        
        start = grid[sRow][sCol];
        end = grid[eRow][eCol];
        start.setWall(sWall, false);
        end.setWall(eWall, false);
    }
}
