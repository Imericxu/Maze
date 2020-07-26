package imericxu.zhiheng.mazegen.maze_types.orthogonal;

import imericxu.zhiheng.mazegen.maze_types.Cell;

/**
 * A maze composed of squares
 */
public abstract class OrthogonalMaze
{
    protected final Cell[][] grid;
    protected Cell start;
    protected Cell end;
    
    /**
     * Generates a rectangular maze
     */
    protected OrthogonalMaze(int rows, int cols)
    {
        grid = new Cell[rows * 2 + 1][cols * 2 + 1];
        initNormalGrid();
    }
    
    protected Cell getWallBetween(Cell c1, Cell c2)
    {
        return grid[(c1.getRow() + c2.getRow()) / 2][(c1.getCol() + c2.getCol()) / 2];
    }
    
    public abstract void step();
    
    protected Cell above(Cell cell)
    {
        if (cell.getRow() <= 2) return null;
        return grid[cell.getRow() - 2][cell.getCol()];
    }
    
    protected Cell below(Cell cell)
    {
        if (cell.getRow() >= grid.length - 3) return null;
        return grid[cell.getRow() + 2][cell.getCol()];
    }
    
    protected Cell left(Cell cell)
    {
        if (cell.getCol() <= 2) return null;
        return grid[cell.getRow()][cell.getCol() - 2];
    }
    
    protected Cell right(Cell cell)
    {
        if (cell.getCol() >= grid[cell.getRow()].length - 3) return null;
        return grid[cell.getRow()][cell.getCol() + 2];
    }
    
    protected Cell[] getNeighbors(Cell Cell)
    {
        return new Cell[]{above(Cell), below(Cell), left(Cell), right(Cell)};
    }
    
    public Cell getStart()
    {
        return start;
    }
    
    public Cell getEnd()
    {
        return end;
    }
    
    public Cell[][] getGrid()
    {
        return grid;
    }
    
    private void initNormalGrid()
    {
        for (int row = 0; row < grid.length; ++row)
        {
            for (int col = 0; col < grid[0].length; ++col)
            {
                grid[row][col] = new Cell(row, col);
            }
        }
        
        for (int row = 1; row < grid.length - 1; row += 2)
        {
            for (int col = 1; col < grid[0].length - 1; col += 2)
            {
                grid[row][col].setState(Cell.OPEN);
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
    
    private int rand(int upTo)
    {
        return (int) (Math.random() * (upTo / 2)) * 2 + 1;
    }
    
    private void setStart(int row, int col)
    {
        grid[row][col].setState(Cell.START);
        
        if (row == 0) ++row;
        else if (row == grid.length - 1) --row;
        else if (col == 0) ++col;
        else --col;
        
        start = grid[row][col];
    }
    
    private void setEnd(int row, int col)
    {
        end = grid[row][col];
        grid[row][col].setState(Cell.END);
    }
}
