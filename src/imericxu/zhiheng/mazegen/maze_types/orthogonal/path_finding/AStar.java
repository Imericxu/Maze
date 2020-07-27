package imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding;

import imericxu.zhiheng.mazegen.maze_types.PCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public abstract class AStar
{
    private HashSet<PCell> openList;
    private HashSet<PCell> closedList;
    private HashMap<PCell, PCell> cameFrom;
    private PCell end;
    private ArrayList<PCell> path;
    
    protected AStar()
    {
    
    }
    
    protected abstract void setCurrentDisplay(PCell current);
    
    public boolean step()
    {
        if (!openList.isEmpty())
        {
            PCell current = Collections.min(openList, (o1, o2) -> (int) ((o1.getF() - o2.getF()) * 10));
            setCurrentDisplay(current);
            
            path = reconstructPath(cameFrom, current);
            
            if (current == end)
            {
                return false;
            }
            
            openList.remove(current);
            closedList.add(current);
            
            var neighbors = getNeighbors(current);
            for (var neighbor : neighbors)
            {
                if (!closedList.contains(neighbor))
                {
                    double tempG = current.getG() + 1;
                    if (tempG < neighbor.getG())
                    {
                        cameFrom.put(neighbor, current);
                        neighbor.setG(tempG);
                        neighbor.setF(tempG + PCell.heuristic(neighbor, end, PCell.Heuristic.MANHATTAN));
                    }
                    
                    // HashSet automatically checks if it contains neighbor
                    openList.add(neighbor);
                }
            }
            
            return true;
        }
        else return false;
    }
    
    public ArrayList<PCell> getPath()
    {
        return path;
    }
    
    protected abstract <T> ArrayList<T> reconstructPath(HashMap<PCell, PCell> cameFrom, PCell current);
    
    protected abstract ArrayList<PCell> getNeighbors(PCell current);
}
