package imericxu.zhiheng.mazegen.maze.solve_algos;

import imericxu.zhiheng.mazegen.maze.Node;
import imericxu.zhiheng.mazegen.maze.State;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class BreadthFirstSearch extends PathAlgorithm
{
	private final Queue<Integer> queue = new ArrayDeque<>();
	private final Map<Integer, Integer> cameFrom = new HashMap<>();
	
	public BreadthFirstSearch(Node[] nodes, int startId, int endId)
	{
		super(nodes, startId, endId);
		changeState(startId, State.PARTIAL);
		queue.add(startId);
	}
	
	@Override
	protected boolean loopOnceImpl()
	{
		final var currentQueue = new ArrayDeque<>(queue);
		queue.clear();
		
		while (!currentQueue.isEmpty())
		{
			final int currentId = currentQueue.poll();
			if (currentId == endId)
			{
				path.addAll(AStar.constructPath(cameFrom, currentId));
				return true;
			}
			getConnectionsOf(currentId).stream()
			                           .filter(id -> states[id] == State.SOLID)
			                           .forEach(id -> {
				                           cameFrom.put(id, currentId);
				                           changeState(id, State.PARTIAL);
				                           queue.add(id);
			                           });
		}
		return false;
	}
}
