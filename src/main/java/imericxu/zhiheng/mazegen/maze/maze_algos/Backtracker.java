package imericxu.zhiheng.mazegen.maze.maze_algos;

import java.util.ArrayList;
import java.util.Stack;


public class Backtracker extends MazeAlgorithm
{
    private final Stack<Node> stack = new Stack<>();
    
    public Backtracker(Node[] nodes)
    {
        this(nodes, 0);
    }
    
    public Backtracker(Node[] nodes, int startIndex)
    {
        super(nodes);
        Node start = nodes[startIndex];
        stack.push(start);
        start.state = Node.State.EXPLORE;
        changeList.add(start);
    }
    
    @Override
    public boolean step()
    {
        if (stack.empty()) return false;
        
        final Node current = stack.pop();
        
        final ArrayList<Node> unvisited = getUnvisited(current);
        
        if (!unvisited.isEmpty())
        {
            stack.push(current);
            
            final Node random = unvisited.get(rand.nextInt(unvisited.size()));
            Node.connect(current, random);
            stack.push(random);
            random.state = Node.State.EXPLORE;
            changeList.add(random);
        }
        else
        {
            current.state = Node.State.DONE;
            changeList.add(current);
        }
        
        return true;
    }
    
    private ArrayList<Node> getUnvisited(Node node)
    {
        final var unvisited = new ArrayList<Node>();
        for (final Node neighbor : node.getNeighbors())
            if (neighbor.state == Node.State.DEFAULT)
                unvisited.add(neighbor);
        return unvisited;
    }
}