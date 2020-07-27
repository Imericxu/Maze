package imericxu.zhiheng.mazegen.maze_types.orthogonal;

/**
 * A maze composed of squares
 */
public abstract class OrthogonalMaze
{
    protected final OCell[][] grid;
    protected OCell start;
    protected OCell end;
    
    /**
     * Generates a rectangular maze
     */
    protected OrthogonalMaze(int rows, int cols)
    {
        grid = new OCell[rows][cols];
        initNormalGrid();
    }
    
    public abstract void step();
    
    protected void removeWallsBetween(OCell c1, OCell c2)
    {
        int c1Wall;
        int c2Wall;
        
        if (c1.getRow() < c2.getRow())
        {
            c1Wall = OCell.BOTTOM;
            c2Wall = OCell.TOP;
        }
        else if (c1.getRow() > c2.getRow())
        {
            c1Wall = OCell.TOP;
            c2Wall = OCell.BOTTOM;
        }
        else if (c1.getCol() < c2.getCol())
        {
            c1Wall = OCell.RIGHT;
            c2Wall = OCell.LEFT;
        }
        else
        {
            c1Wall = OCell.LEFT;
            c2Wall = OCell.RIGHT;
        }
        
        c1.setWall(c1Wall, false);
        c2.setWall(c2Wall, false);
    }
    
    protected OCell[] getNeighbors(OCell OCell)
    {
        return new OCell[]{above(OCell), below(OCell), left(OCell), right(OCell)};
    }
    
    protected OCell above(OCell OCell)
    {
        if (OCell.getRow() <= 1) return null;
        return grid[OCell.getRow() - 1][OCell.getCol()];
    }
    
    protected OCell below(OCell OCell)
    {
        if (OCell.getRow() >= grid.length - 2) return null;
        return grid[OCell.getRow() + 1][OCell.getCol()];
    }
    
    protected OCell left(OCell OCell)
    {
        if (OCell.getCol() <= 1) return null;
        return grid[OCell.getRow()][OCell.getCol() - 1];
    }
    
    protected OCell right(OCell OCell)
    {
        if (OCell.getCol() >= grid[OCell.getRow()].length - 2) return null;
        return grid[OCell.getRow()][OCell.getCol() + 1];
    }
    
    public OCell[][] getGrid()
    {
        return grid;
    }
    
    /* * * * * * * * * * * * * * * * * * * * *
    Helper Methods
    * * * * * * * * * * * * * * * * * * * * */
    
    private void initNormalGrid()
    {
        for (int row = 0; row < grid.length; ++row)
        {
            for (int col = 0; col < grid[0].length; ++col)
            {
                grid[row][col] = new OCell(row, col);
            }
        }
    
        randomizeStartAndEnd();
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
