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
    
    protected OCell[] getNeighbors(OCell OCell)
    {
        return new OCell[]{above(OCell), below(OCell), left(OCell), right(OCell)};
    }
    
    public OCell getStart()
    {
        return start;
    }
    
    public OCell getEnd()
    {
        return end;
    }
    
    public OCell[][] getGrid()
    {
        return grid;
    }
    
    private void initNormalGrid()
    {
        for (int row = 0; row < grid.length; ++row)
        {
            for (int col = 0; col < grid[0].length; ++col)
            {
                grid[row][col] = new OCell(row, col);
            }
        }
        
        // Generate start and end
        switch ((int) (Math.random() * 4))
        {
        case 0 -> { // Up
            setStart(0, rand(grid[0].length));
            setEnd(grid.length - 1, rand(grid[0].length));
        }
        case 1 -> { // Down
            setStart(grid.length - 1, rand(grid[0].length));
            setEnd(0, rand(grid[0].length));
        }
        case 2 -> { // Left
            setStart(rand(grid.length), 0);
            setEnd(rand(grid.length), grid[0].length - 1);
        }
        case 3 -> { // Right
            setStart(rand(grid.length), grid[0].length - 1);
            setEnd(rand(grid.length), 0);
        }
        }
    }
    
    private void setStart(int row, int col)
    {
        grid[row][col].setState(OCell.START);
        start = grid[row][col];
        removeBorderWall(row, col, start);
    }
    
    private void setEnd(int row, int col)
    {
        grid[row][col].setState(OCell.END);
        end = grid[row][col];
        removeBorderWall(row, col, end);
    }
    
    private void removeBorderWall(int row, int col, OCell end)
    {
        if (row == 0)
        {
            end.setWall(OCell.TOP, false);
        }
        else if (row == grid.length - 1)
        {
            end.setWall(OCell.BOTTOM, false);
        }
        else if (col == 0)
        {
            end.setWall(OCell.LEFT, false);
        }
        else
        {
            end.setWall(OCell.RIGHT, false);
        }
    }
    
    private int rand(int upTo)
    {
        return (int) (Math.random() * upTo);
    }
}
