package imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Uses the Recursive Backtracking algorithm in a {@code Stack}
 */
public class Backtracker extends Maze
{
    private final Stack<OCell> stack;
    
    /**
     * Generates a rectangular maze
     */
    public Backtracker(int rows, int cols)
    {
        super(rows, cols);
        stack = new Stack<>();
        stack.push(start);
        start.visited();
        start.setDisplay(Cell.Display.EXPLORE);
        changeList.push(start);
    }
    
    @Override
    public boolean step()
    {
        if (!stack.empty())
        {
            OCell current = stack.pop();
            var unvisited = getUnvisitedNeighbors(current);
            
            if (!unvisited.isEmpty())
            {
                stack.push(current);
                
                OCell selected = unvisited.get(r.nextInt(unvisited.size()));
                selected.visited();
                selected.setDisplay(Cell.Display.EXPLORE);
                changeList.push(selected);
                stack.push(selected);
                
                setWallsBetween(current, selected, false);
            }
            else
            {
                current.setDisplay(Cell.Display.SHOW);
                changeList.push(current);
            }
            
            return true;
        }
        else
        {
            for (var row : grid)
            {
                for (var cell : row)
                {
                    cell.clearVisited();
                }
            }
            return false;
        }
    }
    
    /**
     * Gets all neighbors of {@code current} and adds them to an {@code ArrayList}
     * if they are unvisited.
     */
    private ArrayList<OCell> getUnvisitedNeighbors(OCell current)
    {
        ArrayList<OCell> unvisited = new ArrayList<>();
        for (OCell neighbor : getNeighbors(current))
        {
            if (neighbor.getVisited() == 0)
            {
                unvisited.add(neighbor);
            }
        }
        return unvisited;
    }
}
