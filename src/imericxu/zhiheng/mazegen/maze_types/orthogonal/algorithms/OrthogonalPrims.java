package imericxu.zhiheng.mazegen.maze_types.orthogonal.algorithms;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OrthogonalMaze;

import java.util.ArrayList;

public class OrthogonalPrims extends OrthogonalMaze
{
    private final ArrayList<Cell> frontiers;
    
    /**
     * Generates a rectangular maze
     */
    public OrthogonalPrims(int rows, int cols)
    {
        super(rows, cols);
        frontiers = new ArrayList<>();
        beginWithStart();
    }
    
    public void step()
    {
        if (!frontiers.isEmpty())
        {
            int i = (int) (Math.random() * frontiers.size());
            Cell randomFrontier = frontiers.remove(i);
            addFrontiers(randomFrontier);
            int row = randomFrontier.getRow();
            int col = randomFrontier.getCol();
            
            ArrayList<Cell> discoveredCells = new ArrayList<>();
            Cell[] tempPoses = getNeighbors(randomFrontier);
            
            for (Cell pos : tempPoses)
            {
                if (pos != null && grid[pos.getRow()][pos.getCol()].getState() == Cell.State.FOUND)
                {
                    discoveredCells.add(pos);
                }
            }
            
            i = (int) (Math.random() * discoveredCells.size());
            Cell chosen = discoveredCells.get(i);
            grid[(chosen.getRow() + row) / 2][(chosen.getCol() + col) / 2].setState(Cell.State.OPEN);
        }
    }
    
    private void addFrontiers(Cell pos)
    {
        grid[pos.getRow()][pos.getCol()].setState(Cell.State.FOUND);
        
        Cell[] tempPoses = getNeighbors(pos);
        
        for (Cell newPos : tempPoses)
        {
            if (newPos == null) continue;
            boolean isFoundCell = grid[newPos.getRow()][newPos.getCol()].getState() != Cell.State.FOUND;
            if (!frontiers.contains(newPos) && isFoundCell)
            {
                frontiers.add(newPos);
            }
        }
    }
    
    private Cell[] getNeighbors(Cell cell)
    {
        Cell up = cell.getRow() > 2 ? cell.shift(-2, 0) : null;
        Cell down = cell.getRow() < grid.length - 3 ? cell.shift(2, 0) : null;
        Cell left = cell.getCol() > 2 ? cell.shift(0, -2) : null;
        Cell right = cell.getCol() < grid[0].length - 3 ? cell.shift(0, 2) : null;
        
        return new Cell[]{up, down, left, right};
    }
    
    private void beginWithStart()
    {
        if (start.getRow() == 0)
        {
            addFrontiers(start.shift(1, 0));
        }
        else if (start.getRow() == grid.length - 1)
        {
            addFrontiers(start.shift(-1, 0));
        }
        else if (start.getCol() == 0)
        {
            addFrontiers(start.shift(0, 1));
        }
        else
        {
            addFrontiers(start.shift(0, -1));
        }
    }
}
