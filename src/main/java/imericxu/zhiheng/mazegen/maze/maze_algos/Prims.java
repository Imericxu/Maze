package imericxu.zhiheng.mazegen.maze.maze_algos;

import imericxu.zhiheng.mazegen.maze.Cell;
import imericxu.zhiheng.mazegen.maze.Maze;

import java.util.ArrayList;

/**
 * Uses Prim's algorithm to generate orthogonal mazes
 */
public class Prims extends Maze
{
    /**
     * Cells surrounding the already explored cells
     */
    private final ArrayList<Cell> frontiers;
    
    /**
     * Generates a rectangular maze
     */
    public Prims(int rows, int cols)
    {
        super(rows, cols);
        frontiers = new ArrayList<>();
        addFrontiersOf(start);
    }
    
    @Override
    public boolean step()
    {
        if (!frontiers.isEmpty())
        {
            var current = frontiers.remove(r.nextInt(frontiers.size()));
            addFrontiersOf(current);
            
            ArrayList<Cell> choices = getChoices(current);
            var selected = choices.get(r.nextInt(choices.size()));
            setWallsBetween(current, selected, false);
            
            return false;
        }
        else return true;
    }
    
    /**
     * Marks {@code cell} as visited.<br/>
     * Gets the neighbors of {@code cell} and adds them to
     * {@link #frontiers} if not already added and the {@link Cell}
     * has not been visited
     */
    private void addFrontiersOf(Cell cell)
    {
        cell.setState(Cell.State.DONE);
        queueUpdate(cell);
        
        for (Cell neighbor : getNeighbors(cell))
        {
            if (!frontiers.contains(neighbor) && neighbor.state == Cell.State.DEFAULT)
            {
                frontiers.add(neighbor);
                neighbor.setState(Cell.State.EXPLORE);
                queueUpdate(neighbor);
            }
        }
    }
    
    /**
     * Gets the neighbors of {@code current} and adds if the
     * {@link Cell} has previously been explored
     *
     * @param current the current frontier
     * @return an ArrayList containing possible {@link Cell}s {@code current} can connect to
     */
    private ArrayList<Cell> getChoices(Cell current)
    {
        var visited = new ArrayList<Cell>();
        for (Cell cell : getNeighbors(current))
        {
            if (cell.state == Cell.State.DONE)
            {
                visited.add(cell);
            }
        }
        return visited;
    }
}
