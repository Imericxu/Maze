package imericxu.zhiheng.mazegen.maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public abstract class Pathfinder extends Orthogonal
{
    protected Stack<Cell> path = new Stack<>();
    
    public void setMaze(Maze maze)
    {
        start = maze.getStart();
        end = maze.getEnd();
    }
    
    public Stack<Cell> getPath()
    {
        return path;
    }
    
    protected ArrayList<Cell> getNeighbors(Cell current, Cell[][] grid)
    {
        var neighbors = new ArrayList<Cell>();
        int row = current.getRow();
        int col = current.getCol();
        var walls = current.getWalls();
        
        if (!walls[Cell.TOP] && row > 0)
        {
            neighbors.add(grid[row - 1][col]);
        }
        if (!walls[Cell.RIGHT] && col < grid[0].length - 1)
        {
            neighbors.add(grid[row][col + 1]);
        }
        if (!walls[Cell.BOTTOM] && row < grid.length - 1)
        {
            neighbors.add(grid[row + 1][col]);
        }
        if (!walls[Cell.LEFT] && col > 0)
        {
            neighbors.add(grid[row][col - 1]);
        }
        
        return neighbors;
    }
}