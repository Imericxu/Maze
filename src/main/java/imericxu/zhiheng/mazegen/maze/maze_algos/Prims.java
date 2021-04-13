package imericxu.zhiheng.mazegen.maze.maze_algos;

import imericxu.zhiheng.mazegen.maze.Node;
import imericxu.zhiheng.mazegen.maze.State;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Prims extends MazeAlgorithm
{
	private final Set<Integer> frontiers = new HashSet<>();
	
	public Prims(Node[] nodes)
	{
		super(nodes);
		Node start = nodes[rand.nextInt(nodes.length)];
		changeState(start, State.SOLID);
		for (Integer neighbor : start.getNeighbors())
		{
			frontiers.add(neighbor);
			changeState(neighbor, State.PARTIAL);
		}
	}
	
	@Override
	public void loopOnce()
	{
		if (frontiers.isEmpty()) return;
		
		final int index = rand.nextInt(frontiers.size());
		final int currentId = frontiers.stream().skip(index).findFirst().orElseThrow();
		frontiers.remove(currentId);
		addFrontiersOf(currentId);
		connectRandMazeCell(currentId);
	}
	
	private void addFrontiersOf(int nodeId)
	{
		changeState(nodeId, State.SOLID);
		
		nodes[nodeId].getNeighbors()
		             .stream()
		             .filter(id -> states[id] == State.EMPTY && !frontiers.contains(id))
		             .forEach(id -> {
			             frontiers.add(id);
			             changeState(id, State.PARTIAL);
		             });
	}
	
	private void connectRandMazeCell(int nodeId)
	{
		final var connectedMazeCells = nodes[nodeId].getNeighbors()
		                                            .stream()
		                                            .filter(id -> states[id] == State.SOLID)
		                                            .collect(Collectors.toUnmodifiableList());
		final int index = rand.nextInt(connectedMazeCells.size());
		final int randomId = connectedMazeCells.get(index);
		Node.connect(nodes[nodeId], nodes[randomId]);
	}
}
