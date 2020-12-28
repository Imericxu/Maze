package imericxu.zhiheng.mazegen.maze.solve_algos;

import imericxu.zhiheng.mazegen.maze.Pathfinder;
import imericxu.zhiheng.mazegen.maze.maze_algos.MazeAlgorithm;
import imericxu.zhiheng.mazegen.maze.maze_algos.Node;

import java.util.ArrayList;
import java.util.Random;

public class Tremaux extends Pathfinder
{
    private final Random rand = new Random();
    
    public Tremaux(MazeAlgorithm mazeAlgorithm)
    {
        super(mazeAlgorithm);
    }
    
    @Override
    public boolean step()
    {
        var current = path.peek();
        current.state = Node.State.EXPLORE;
        queueUpdate(current);
        
        var choices = getUnvisited(current);
        
        if (!choices.isEmpty())
        {
            var selected = choices.get(rand.nextInt(choices.size()));
            path.push(selected);
            
            selected.state = Node.State.EXPLORE;
            queueUpdate(selected);
            
            return selected != end;
        }
        else
        {
            path.pop();
        }
        
        return true;
    }
    
    private ArrayList<Node> getUnvisited(Node node)
    {
        var unvisited = new ArrayList<Node>();
        
        var previous = path.size() < 2 ? null : path.get(path.size() - 2);
        
        for (final Node neighbor : node.getConnections())
            if (neighbor.state == Node.State.DONE)
                unvisited.add(neighbor);
        
        return unvisited;
    }
}
