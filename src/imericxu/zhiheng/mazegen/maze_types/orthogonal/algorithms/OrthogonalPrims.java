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
        addFrontiers(start);
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
                if (pos != null && pos.getState() == Cell.SEEN)
                {
                    discoveredCells.add(pos);
                }
            }
            
            i = (int) (Math.random() * discoveredCells.size());
            Cell chosen = discoveredCells.get(i);
            grid[(chosen.getRow() + row) / 2][(chosen.getCol() + col) / 2].setState(Cell.SEEN);
        }
    }
    
    private void addFrontiers(Cell pos)
    {
        pos.setState(Cell.SEEN);
        
        Cell[] tempPoses = getNeighbors(pos);
        
        for (Cell newPos : tempPoses)
        {
            if (newPos == null) continue;
            if (!frontiers.contains(newPos) && newPos.getState() != Cell.SEEN)
            {
                newPos.setState(Cell.FRONTIER);
                frontiers.add(newPos);
            }
        }
    }
    
    private Cell[] getNeighbors(Cell Cell)
    {
        return new Cell[]{above(Cell), below(Cell), left(Cell), right(Cell)};
    }
}
