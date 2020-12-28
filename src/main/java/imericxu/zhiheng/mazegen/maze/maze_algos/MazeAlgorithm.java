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
        changeList.add(nodes[0]);
    }
    
    public abstract void step();
}
