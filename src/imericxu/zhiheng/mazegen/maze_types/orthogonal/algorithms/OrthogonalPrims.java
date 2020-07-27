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
            OCell randomFrontier = frontiers.remove(i);
            addFrontiers(randomFrontier);
            int row = randomFrontier.getRow();
            int col = randomFrontier.getCol();
            
            ArrayList<OCell> discoveredOCells = new ArrayList<>();
            OCell[] tempPoses = getNeighbors(randomFrontier);
            
            for (OCell pos : tempPoses)
            {
                if (pos != null && pos.getState() == OCell.VISITED)
                {
                    discoveredOCells.add(pos);
                }
            }
            
            i = (int) (Math.random() * discoveredOCells.size());
            OCell chosen = discoveredOCells.get(i);
            grid[(chosen.getRow() + row) / 2][(chosen.getCol() + col) / 2].setState(OCell.VISITED);
        }
    }
    
    private void addFrontiers(OCell pos)
    {
        pos.setState(OCell.VISITED);
        
        OCell[] tempPoses = getNeighbors(pos);
        
        for (OCell newPos : tempPoses)
        {
            if (newPos == null) continue;
            if (!frontiers.contains(newPos) && newPos.getState() != OCell.VISITED)
            {
                newPos.setState(OCell.SPECIAL);
                frontiers.add(newPos);
            }
        }
    }
}
