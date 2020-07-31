package imericxu.zhiheng.mazegen.maze_types.orthogonal;

import java.util.Stack;

public abstract class Orthogonal
{
    protected final Stack<OCell> changeList;
    protected OCell start;
    protected OCell end;
    public Orthogonal()
    {
        changeList = new Stack<>();
    }
    
    public Stack<OCell> getChangeList()
    {
        return changeList;
    }
    
    public abstract boolean step();
    
    public void instantSolve()
    {
        while (true)
        {
            if (step()) return;
        }
    }
    
    public OCell getStart()
    {
        return start;
    }
    
    public OCell getEnd()
    {
        return end;
    }
}
