package imericxu.zhiheng.mazegen.maze.maze_algos;

import java.util.*;

public class Wilson extends MazeAlgorithm
{
	private final HashSet<Node> knownNodes = new HashSet<>();
	private final HashSet<Node> unknownNodes = new HashSet<>();
	private final Stack<Node> currentWalk = new Stack<>();
	
	public Wilson(Node[] nodes)
	{
		super(nodes);
		unknownNodes.addAll(Arrays.asList(nodes));
		
		Node start = unknownNodes.iterator().next();
		unknownNodes.remove(start);
		start.state = Node.State.DONE;
		changeList.add(start);
	}
	
	@Override
	public boolean step()
	{
		if (unknownNodes.isEmpty()) return false;
		
		if (currentWalk.isEmpty())
			startNewWalk();
		else
		{
			Node current = currentWalk.peek();
			List<Node> neighbors = current.getNeighbors();
			if (currentWalk.size() > 1) // Don't go backwards
				neighbors.remove(currentWalk.get(currentWalk.size() - 2));
			Node randNeighbor = neighbors.get(rand.nextInt(neighbors.size()));
			
			if (currentWalk.contains(randNeighbor) && currentWalk.size() > 4)
			{
				deleteLoop(randNeighbor);
			}
			else if (knownNodes.contains(randNeighbor))
			{
				Node.connect(current, randNeighbor);
				knownNodes.addAll(currentWalk);
				unknownNodes.removeAll(currentWalk);
				
				currentWalk.forEach(node -> node.state = Node.State.DONE);
				changeList.addAll(currentWalk);
				currentWalk.clear();
			}
			else
			{
				Node.disconnect(current, randNeighbor);
				currentWalk.push(randNeighbor);
				
				randNeighbor.state = Node.State.EXPLORE;
				changeList.add(randNeighbor);
			}
		}
		
		return true;
	}
	
	private void startNewWalk()
	{
		Node start = unknownNodes.iterator().next();
		currentWalk.push(start);
		
		start.state = Node.State.EXPLORE;
		changeList.add(start);
	}
	
	private void deleteLoop(Node end)
	{
		Node current = currentWalk.pop();
		Node next = currentWalk.pop();
		Node.disconnect(current, next);
		
		current.state = next.state = Node.State.DEFAULT;
		Collections.addAll(changeList, current, next);
		
		while (currentWalk.size() > 1 && next != end)
		{
			current = next;
			next = currentWalk.pop();
			Node.disconnect(current, next);
			
			next.state = Node.State.DEFAULT;
			changeList.add(next);
		}
		
		Node.disconnect(next, currentWalk.peek());
	}
}