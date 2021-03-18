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
	public boolean step()
	{
		if (frontiers.isEmpty()) return false;
		
		Node current = frontiers.remove(rand.nextInt(frontiers.size()));
		addFrontiersOf(current);
		Node randFrontier = selectRandMazeCell(current);
		Node.connect(current, randFrontier);
		
		return true;
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
	
	private Node selectRandMazeCell(Node node)
	{
		var mazeCells = new ArrayList<Node>();
		
		for (Node neighbor : node.getNeighbors())
			if (neighbor.state == Node.State.DONE)
				mazeCells.add(neighbor);
		
		return mazeCells.get(rand.nextInt(mazeCells.size()));
	}
}