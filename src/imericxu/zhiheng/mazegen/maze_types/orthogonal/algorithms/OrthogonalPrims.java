package imericxu.zhiheng.mazegen.maze_types.orthogonal.algorithms;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OrthogonalMaze;

import java.util.ArrayList;
import java.util.Random;

public class OrthogonalPrims extends OrthogonalMaze
{
    private final ArrayList<OCell> frontiers;
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
            OCell current = frontiers.remove(r.nextInt(frontiers.size()));
            addFrontiers(current);
            
            ArrayList<OCell> visited = new ArrayList<>();
            for (OCell cell : getNeighbors(current))
            {
                if (cell != null && cell.getVisited() >= 1)
                {
                    visited.add(cell);
                }
            }
            
            OCell chosen = visited.get(r.nextInt(visited.size()));
            removeWallsBetween(current, chosen);
        }
    }
    
    public void oldstep()
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
