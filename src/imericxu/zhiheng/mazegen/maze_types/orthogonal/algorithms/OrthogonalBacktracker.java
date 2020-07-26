package imericxu.zhiheng.mazegen.maze_types.orthogonal.algorithms;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OrthogonalMaze;

import java.util.ArrayList;
import java.util.Stack;

public class OrthogonalBacktracker extends OrthogonalMaze
{
    private final Stack<Cell> stack;
    private Cell previous;
    
    /**
     * Generates a rectangular maze
     */
    public OrthogonalBacktracker(int rows, int cols)
    {
        super(rows, cols);
        stack = new Stack<>();
        stack.push(start);
        start.visited();
    }
    
    @Override
    public void step()
    {
        if (!stack.isEmpty())
        {
            Cell current = stack.pop();
            ArrayList<Cell> unvisited = getUnvisitedNeighbors(current);
            
            if (!unvisited.isEmpty())
            {
                stack.push(current);
                
                int i = (int) (Math.random() * unvisited.size());
                Cell chosen = unvisited.get(i);
                chosen.visited();
    
                previous = current;
                getWallBetween(current, chosen).visited();
                
                stack.push(chosen);
            }
            else
            {
                current.visited();
                getWallBetween(previous, current).visited();
                previous = current;
            }
        }
        else
        {
            start.setState(Cell.START);
        }
    }
    
    private ArrayList<Cell> getUnvisitedNeighbors(Cell current)
    {
        ArrayList<Cell> unvisited = new ArrayList<>();
        for (Cell neighbor : getNeighbors(current))
        {
            if (neighbor != null && neighbor.getState() != Cell.VISITED)
            {
                unvisited.add(neighbor);
            }
        }
        return unvisited;
    }
}
