package imericxu.zhiheng.mazegen.maze.maze_algos;

import java.util.ArrayList;

public class Prims extends MazeAlgorithm
{
    private final ArrayList<Node> frontiers = new ArrayList<>();
    
    public Prims(Node[] nodes)
    {
        this(nodes, 0);
    }
    
    public Prims(Node[] nodes, int startIndex)
    {
        super(nodes);
        Node start = nodes[startIndex];
        addFrontiersOf(start);
    }
    
    @Override
    public void step()
    {
        if (!frontiers.isEmpty())
        {
            Node current = frontiers.remove(rand.nextInt(frontiers.size()));
            addFrontiersOf(current);
            Node randFrontier = getRandomFrontierOf(current);
            Node.connect(current, randFrontier);
        }
    }
    
    private void addFrontiersOf(Node node)
    {
        node.state = Node.State.DONE;
        changeList.add(node);
        
        for (final Node neighbor : node.getNeighbors())
        {
            if (neighbor.state == Node.State.DEFAULT && !frontiers.contains(neighbor))
            {
                frontiers.add(neighbor);
                neighbor.state = Node.State.EXPLORE;
                changeList.add(neighbor);
            }
        }
    }
    
    private Node getRandomFrontierOf(Node node)
    {
        var frontiers = new ArrayList<Node>();
        
        for (Node neighbor : node.getNeighbors())
            if (neighbor.state == Node.State.EXPLORE)
                frontiers.add(neighbor);
        
        return frontiers.get(rand.nextInt(frontiers.size()));
    }
}