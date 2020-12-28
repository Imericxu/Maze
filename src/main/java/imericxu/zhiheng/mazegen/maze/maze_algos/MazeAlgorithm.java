package imericxu.zhiheng.mazegen.maze.maze_algos;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public abstract class MazeAlgorithm
{
    public final Queue<Node> changeList = new LinkedList<>();
    protected final Node[] nodes;
    protected final Random rand = new Random();
    
    public MazeAlgorithm(Node[] nodes)
    {
        this.nodes = nodes;
    }
    
    /**
     * @return true if more steps left (maze not done)
     */
    public abstract boolean step();
    
    public void instantSolve()
    {
        while (true)
        {
            for (int i = 0; i < 50; ++i)
            {
                if (!step())
                    return;
                changeList.clear();
            }
        }
    }
    
    public Node[] getNodes()
    {
        return nodes.clone();
    }
}
