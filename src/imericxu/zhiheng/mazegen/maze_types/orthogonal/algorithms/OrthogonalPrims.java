package imericxu.zhiheng.mazegen.maze_types.orthogonal.algorithms;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OrthogonalMaze;

import java.util.ArrayList;
import java.util.Random;

public class OrthogonalPrims extends OrthogonalMaze
{
    private final ArrayList<Cell> frontiers;
    private final Random r;
    
    /**
     * Generates a rectangular maze
     */
    public OrthogonalPrims(int rows, int cols)
    {
        super(rows, cols);
        frontiers = new ArrayList<>();
        addFrontiers(start);
        r = new Random();
    }
    
    @Override
    public void step()
    {
        if (!frontiers.isEmpty())
        {
            int i = r.nextInt(frontiers.size());
            Cell current = frontiers.remove(i);
            addFrontiers(current);
            
            ArrayList<Cell> visited = new ArrayList<>();
            for (Cell pos : getNeighbors(current))
            {
                if (pos != null && pos.getState() == Cell.VISITED)
                {
                    visited.add(pos);
                }
            }
            
            i = r.nextInt(visited.size());
            Cell chosen = visited.get(i);
            getWallBetween(chosen, current).setState(Cell.VISITED);
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
