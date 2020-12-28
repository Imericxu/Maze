package imericxu.zhiheng.mazegen.maze.maze_algos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node
{
    public final int id;
    private final ArrayList<Node> connections = new ArrayList<>();
    public State state = State.DEFAULT;
    private List<Node> neighbors;
    
    public Node(int id)
    {
        this.id = id;
    }
    
    public static void connect(Node a, Node b)
    {
        assert a.neighbors.contains(b) && b.neighbors.contains(a);
        assert !a.connections.contains(b) && !b.connections.contains(a);
        a.connections.add(b);
        b.connections.add(a);
    }
    
    public static void disconnect(Node a, Node b)
    {
        assert a.neighbors.contains(b) && b.neighbors.contains(a);
        assert a.connections.contains(b) && b.connections.contains(a);
        a.connections.remove(b);
        b.connections.remove(a);
    }
    
    public void init(List<Node> neighbors)
    {
        assert this.neighbors == null && neighbors != null;
        this.neighbors = neighbors;
    }
    
    public List<Node> getConnections()
    {
        return Collections.unmodifiableList(connections);
    }
    
    public List<Node> getNeighbors()
    {
        return Collections.unmodifiableList(neighbors);
    }
    
    public enum State
    {
        DEFAULT, EXPLORE, DONE
    }
}