package imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Maze;

import java.util.ArrayList;
import java.util.Random;

public class Tremaux extends Pathfinder
{
    private final OCell[][] grid;
    private final Random r;
    
    public Tremaux(Maze maze)
    {
        super(maze);
        grid = maze.getGrid();
        r = new Random();
    }
    
    public void init()
    {
        path.push(start);
        start.visited();
    }
    
    public boolean step()
    {
        var current = path.peek();
        var choices = getUnvisited(current);
        
        if (!choices.isEmpty())
        {
            var selected = choices.get(r.nextInt(choices.size()));
            selected.setDisplay(Cell.Display.EXPLORE);
            selected.visited();
            path.push(selected);
            return selected != end;
        }
        else
        {
            path.pop();
        }
        
        return true;
    }
    
    private ArrayList<OCell> getUnvisited(OCell current)
    {
        current.setDisplay(Cell.Display.EXPLORE);
        changeList.push(current);
    
        var unvisited = new ArrayList<OCell>();
        int row = current.getRow();
        int col = current.getCol();
        var walls = current.getWalls();
    
        var previous = path.size() < 2 ? null : path.get(path.size() - 2);
    
        if (!walls[OCell.TOP] && row > 0)
        {
            var cell = grid[row - 1][col];
            if (cell != previous) changeList.push(cell);
            if (cell.getVisited() == 0) unvisited.add(cell);
        }
        if (!walls[OCell.RIGHT] && col < grid[0].length - 1)
        {
            var cell = grid[row][col + 1];
            if (cell != previous) changeList.push(cell);
            if (cell.getVisited() == 0) unvisited.add(cell);
        }
        if (!walls[OCell.BOTTOM] && row < grid.length - 1)
        {
            var cell = grid[row + 1][col];
            if (cell != previous) changeList.push(cell);
            if (cell.getVisited() == 0) unvisited.add(cell);
        }
        if (!walls[OCell.LEFT] && col > 0)
        {
            var cell = grid[row][col - 1];
            if (cell != previous) changeList.push(cell);
            if (cell.getVisited() == 0) unvisited.add(cell);
        }
        
        return unvisited;
    }
}
