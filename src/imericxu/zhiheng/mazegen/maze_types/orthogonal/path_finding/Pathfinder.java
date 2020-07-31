package imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.Orthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Maze;

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
}
