package imericxu.zhiheng.mazegen.maze.maze_algos;

import imericxu.zhiheng.mazegen.maze.Node;
import imericxu.zhiheng.mazegen.maze.State;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;


public class Backtracker extends MazeAlgorithm
{
	private final Stack<Integer> exploreStack = new Stack<>();
	
	public Backtracker(Node[] nodes)
	{
		super(nodes);
		final int startId = rand.nextInt(nodes.length);
		exploreStack.push(startId);
		changeState(startId, State.PARTIAL);
	}
	
	@Override
	public void loopOnce()
	{
		if (exploreStack.empty()) return;
		
		final int currentId = exploreStack.pop();
		final List<Integer> unvisitedNeighbors = nodes[currentId].getNeighbors()
		                                                         .stream()
		                                                         .filter(id -> states[id] == State.EMPTY)
		                                                         .collect(Collectors.toUnmodifiableList());
		
		if (unvisitedNeighbors.isEmpty())
		{
			changeState(currentId, State.SOLID);
			return;
		}
		
		exploreStack.push(currentId);
		
		final int index = rand.nextInt(unvisitedNeighbors.size());
		final int randomId = unvisitedNeighbors.get(index);
		Node.connect(nodes[currentId], nodes[randomId]);
		changeState(randomId, State.PARTIAL);
		exploreStack.push(randomId);
	}
}
