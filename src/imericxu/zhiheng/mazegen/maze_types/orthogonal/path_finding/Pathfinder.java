package imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.Orthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Maze;

import java.util.Stack;

public abstract class Pathfinder extends Orthogonal
{
    protected Stack<OCell> path;
    
    public Pathfinder(Maze maze)
    {
        path = new Stack<>();
        start = maze.getStart();
        end = maze.getEnd();
    }
    
    public abstract void init();
    
    public Stack<OCell> getPath()
    {
        return path;
    }
}
