package imericxu.zhiheng.mazegen.maze_types.orthogonal;

import imericxu.zhiheng.mazegen.maze_types.Cell;

import java.util.Stack;

public abstract class Orthogonal
{
    protected final Stack<Cell> changeList;
    protected Cell start;
    protected Cell end;

    public Orthogonal()
    {
        changeList = new Stack<>();
    }

    public Stack<Cell> getChangeList()
    {
        return changeList;
    }

    /**
     * @return true if not finished
     */
    public abstract boolean step();

    public void instantSolve()
    {
        while (true)
        {
            if (step()) return;
        }
    }

    public Cell getStart()
    {
        return start;
    }

    public Cell getEnd()
    {
        return end;
    }
}
