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
    
    @Override
    public void step()
    {
        if (!frontiers.isEmpty())
        {
            int i = (int) (Math.random() * frontiers.size());
            Cell randomFrontier = frontiers.remove(i);
            addFrontiers(randomFrontier);
            int row = randomFrontier.getRow();
            int col = randomFrontier.getCol();
            
            ArrayList<Cell> visited = new ArrayList<>();
            for (Cell pos : getNeighbors(randomFrontier))
            {
                if (pos != null && pos.getState() == Cell.VISITED)
                {
                    visited.add(pos);
                }
            }
            
            i = (int) (Math.random() * visited.size());
            Cell chosen = visited.get(i);
            grid[(chosen.getRow() + row) / 2][(chosen.getCol() + col) / 2].setState(Cell.VISITED);
        }
    }
    
    private void addFrontiers(Cell pos)
    {
        pos.setState(Cell.VISITED);
        
        for (Cell newPos : getNeighbors(pos))
        {
            if (newPos == null) continue;
            if (!frontiers.contains(newPos) && newPos.getState() != Cell.VISITED)
            {
                newPos.setState(Cell.SPECIAL);
                frontiers.add(newPos);
            }
        }
    }
}
