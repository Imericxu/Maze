package imericxu.zhiheng.mazegen.maze.solve_algos;

import imericxu.zhiheng.mazegen.maze.Node;
import imericxu.zhiheng.mazegen.maze.State;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Tremaux extends PathAlgorithm
{
	public Tremaux(Node[] nodes, int startId, int endId)
	{
		super(nodes, startId, endId);
		path.push(startId);
		changeState(startId, State.PARTIAL);
	}
	
	@Override
	public boolean loopOnceImpl()
	{
		var currentId = path.peek();
		
		var unvisited = getUnvisited(currentId);
		if (unvisited.isEmpty())
		{
			path.pop();
			return false;
		}
		
		var randomId = unvisited.get(rand.nextInt(unvisited.size()));
		path.push(randomId);
		changeState(randomId, State.PARTIAL);
		
		return randomId == endId;
	}
	
	private List<Integer> getUnvisited(int nodeId)
	{
		final Integer prevId = path.size() > 1 ? path.get(path.size() - 2) : null;
		return getConnectionsOf(nodeId)
				.stream()
				.filter(id -> states[id] == State.SOLID && !Objects.equals(id, prevId))
				.collect(Collectors.toList());
	}
}
