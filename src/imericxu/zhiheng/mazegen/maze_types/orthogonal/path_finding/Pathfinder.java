package imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;

import java.util.Stack;

public abstract class Pathfinder
{
    protected Stack<OCell> path;
    
    public abstract boolean step();
    
    public void instantSolve()
    {
        while (true)
        {
            if (!step()) return;
        }
    }
    
    public Stack<OCell> getPath()
    {
        return path;
    }
}
