package imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.Orthogonal;

import java.util.Stack;

public abstract class Pathfinder extends Orthogonal
{
    protected Stack<OCell> path;
    
    public Pathfinder()
    {
        path = new Stack<>();
    }
    
    public Stack<OCell> getPath()
    {
        return path;
    }
}
