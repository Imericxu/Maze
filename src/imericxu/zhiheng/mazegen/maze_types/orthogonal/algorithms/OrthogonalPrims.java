package imericxu.zhiheng.mazegen.maze_types.orthogonal.algorithms;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OrthogonalMaze;

import java.util.ArrayList;

public class OrthogonalPrims extends OrthogonalMaze
{
    private final ArrayList<OCell> frontiers;
    
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
            OCell current = frontiers.remove(i);
            addFrontiers(current);
            
            ArrayList<OCell> visitedNeighbors = new ArrayList<>();
            for (OCell cell : getNeighbors(current))
            {
                if (cell != null && cell.getVisited() >= 1)
                {
                    visitedNeighbors.add(cell);
                }
            }
            
            i = (int) (Math.random() * visitedNeighbors.size());
            OCell chosen = visitedNeighbors.get(i);
            removeWallsBetween(current, chosen);
        }
    }
    
    private void addFrontiers(OCell cell)
    {
        cell.visited();
        
        for (OCell neighbor : getNeighbors(cell))
        {
            if (neighbor == null) continue;
            if (!frontiers.contains(neighbor) && neighbor.getVisited() == 0)
            {
                frontiers.add(neighbor);
            }
        }
    }
}
