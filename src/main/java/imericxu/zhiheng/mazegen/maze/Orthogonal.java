package imericxu.zhiheng.mazegen.maze;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Orthogonal
{
    protected final Queue<Cell> changeList;
    protected Cell start;
    protected Cell end;

    public Orthogonal()
    {
        changeList = new LinkedList<>();
    }

    public Queue<Cell> getChangeList()
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
            for (int i = 0; i < 100; ++i)
            {
                if (step()) return;
                changeList.clear();
            }
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
