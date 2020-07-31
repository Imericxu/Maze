package imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.Orthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Maze;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Pathfinder extends Orthogonal
{
    protected Stack<OCell> path;
    
    public Pathfinder()
    {
        path = new Stack<>();
    }
    
    public void setMaze(Maze maze)
    {
        start = maze.getStart();
        end = maze.getEnd();
    }
    
    public Stack<OCell> getPath()
    {
        return path;
    }
    
    protected void clearVisited(OCell[][] grid)
    {
        for (var row : grid)
        {
            for (var cell : row)
            {
                cell.clearVisited();
            }
        }
    }
    
    protected ArrayList<OCell> getNeighbors(OCell current, OCell[][] grid)
    {
        var neighbors = new ArrayList<OCell>();
        int row = current.getRow();
        int col = current.getCol();
        var walls = current.getWalls();
        
        if (!walls[OCell.TOP] && row > 0)
        {
            neighbors.add(grid[row - 1][col]);
        }
        if (!walls[OCell.RIGHT] && col < grid[0].length - 1)
        {
            neighbors.add(grid[row][col + 1]);
        }
        if (!walls[OCell.BOTTOM] && row < grid.length - 1)
        {
            neighbors.add(grid[row + 1][col]);
        }
        if (!walls[OCell.LEFT] && col > 0)
        {
            neighbors.add(grid[row][col - 1]);
        }
        
        return neighbors;
    }
}
