package imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Maze;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Tremaux extends Pathfinder
{
    private final OCell[][] grid;
    private final OCell end;
    private final Random r;
    
    public Tremaux(Maze maze)
    {
        path = new Stack<>();
        grid = maze.getGrid();
        end = maze.getEnd();
        r = new Random();
        
        var start = maze.getStart();
        start.setDisplay(Cell.Display.EXPLORE);
        start.visited();
        path.push(start);
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
        var unvisited = new ArrayList<OCell>();
        int row = current.getRow();
        int col = current.getCol();
        var walls = current.getWalls();
        
        var previous = path.size() < 2 ? null : path.get(path.size() - 2);
        changeList.push(current);
        
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
