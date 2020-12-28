package imericxu.zhiheng.mazegen.maze.maze_algos;

import imericxu.zhiheng.mazegen.maze.Cell;
import imericxu.zhiheng.mazegen.maze.Maze;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Uses the Recursive Backtracking algorithm in a {@code Stack}
 */
public class Backtracker extends Maze
{
    private final Stack<Cell> stack;
    
    /**
     * Generates a rectangular maze
     */
    public Backtracker(int rows, int cols)
    {
        super(rows, cols);
        stack = new Stack<>();
        stack.push(start);
        start.setState(Cell.State.EXPLORE);
        queueUpdate(start);
    }
    
    @Override
    public boolean step()
    {
        if (!stack.empty())
        {
            Cell current = stack.pop();
            var unvisited = getUnvisitedNeighbors(current);
            
            if (!unvisited.isEmpty())
            {
                stack.push(current);
                
                Cell selected = unvisited.get(r.nextInt(unvisited.size()));
                selected.setState(Cell.State.EXPLORE);
                queueUpdate(selected);
                stack.push(selected);
                
                setWallsBetween(current, selected, false);
            }
            else
            {
                current.setState(Cell.State.DONE);
                queueUpdate(current);
            }
            
            return false;
        }
        else return true;
    }
    
    /**
     * Gets all neighbors of {@code current} and adds them to an {@code ArrayList}
     * if they are unvisited.
     */
    private ArrayList<Cell> getUnvisitedNeighbors(Cell current)
    {
        ArrayList<Cell> unvisited = new ArrayList<>();
        for (Cell neighbor : getNeighbors(current))
        {
            if (neighbor.state == Cell.State.DEFAULT)
            {
                unvisited.add(neighbor);
            }
        }
        return unvisited;
    }
}