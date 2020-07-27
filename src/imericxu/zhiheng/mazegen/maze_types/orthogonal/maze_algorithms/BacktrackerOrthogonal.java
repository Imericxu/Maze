package imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.MazeOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Uses the Recursive Backtracking algorithm in a {@code Stack}
 */
public class BacktrackerOrthogonal extends MazeOrthogonal
{
    private final Stack<OCell> stack;
    
    /**
     * Generates a rectangular maze
     */
    public BacktrackerOrthogonal(int rows, int cols)
    {
        super(rows, cols);
        stack = new Stack<>();
        stack.push(start);
        start.visited();
        start.setDisplay(Cell.Display.SHOW);
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
                stack.push(selected);
                
                removeWallsBetween(current, selected);
            }
            else
            {
                current.setDisplay(Cell.Display.SHOW);
            }
            
            return true;
        }
        else return false;
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
            if (neighbor != null && neighbor.getVisited() == 0)
            {
                unvisited.add(neighbor);
            }
        }
        return unvisited;
    }
}
