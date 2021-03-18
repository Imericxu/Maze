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
    
    /**
     * pre: a and b must not be already connected<br>
     * Adds each node to the other's connections list
     */
    public static void connect(Node a, Node b)
    {
        assert a.neighbors.contains(b) && b.neighbors.contains(a);
        assert !a.connections.contains(b) && !b.connections.contains(a);
        a.connections.add(b);
        b.connections.add(a);
    }
    
    /**
     * pre: a and b must be connected<br>
     * Removes each node from the other's connections list
     */
    public static void disconnect(Node a, Node b)
    {
        assert a.neighbors.contains(b) && b.neighbors.contains(a);
        assert a.connections.contains(b) && b.connections.contains(a);
        a.connections.remove(b);
        b.connections.remove(a);
    }
    
    public void setNeighbors(List<Node> neighbors)
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