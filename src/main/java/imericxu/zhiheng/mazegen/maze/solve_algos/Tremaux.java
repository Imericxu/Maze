package imericxu.zhiheng.mazegen.maze.solve_algos;

import imericxu.zhiheng.mazegen.maze.Cell;
import imericxu.zhiheng.mazegen.maze.Maze;
import imericxu.zhiheng.mazegen.maze.Pathfinder;

import java.util.ArrayList;
import java.util.Random;

public class Tremaux extends Pathfinder
{
    private final Random r;
    private Cell[][] grid;
    
    public Tremaux()
    {
        super();
        r = new Random();
    }
    
    @Override
    public void setMaze(Maze maze)
    {
        super.setMaze(maze);
        grid = maze.getGrid();
        clearVisited(grid);
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
            selected.setState(Cell.State.EXPLORE);
            selected.visited();
            path.push(selected);
            return selected == end;
        }
        else
        {
            path.pop();
        }
    
        return false;
    }
    
    private ArrayList<Cell> getUnvisited(Cell current)
    {
        current.setState(Cell.State.EXPLORE);
        changeList.push(current);
    
        var unvisited = new ArrayList<Cell>();
        int row = current.getRow();
        int col = current.getCol();
        var walls = current.getWalls();
    
        var previous = path.size() < 2 ? null : path.get(path.size() - 2);
    
        if (!walls[Cell.TOP] && row > 0)
        {
            var cell = grid[row - 1][col];
            if (cell != previous) changeList.push(cell);
            if (cell.getVisited() == 0) unvisited.add(cell);
        }
        if (!walls[Cell.RIGHT] && col < grid[0].length - 1)
        {
            var cell = grid[row][col + 1];
            if (cell != previous) changeList.push(cell);
            if (cell.getVisited() == 0) unvisited.add(cell);
        }
        if (!walls[Cell.BOTTOM] && row < grid.length - 1)
        {
            var cell = grid[row + 1][col];
            if (cell != previous) changeList.push(cell);
            if (cell.getVisited() == 0) unvisited.add(cell);
        }
        if (!walls[Cell.LEFT] && col > 0)
        {
            var cell = grid[row][col - 1];
            if (cell != previous) changeList.push(cell);
            if (cell.getVisited() == 0) unvisited.add(cell);
        }
        
        return unvisited;
    }
}
