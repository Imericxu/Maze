package imericxu.zhiheng.mazegen.maze.maze_algos;

import imericxu.zhiheng.mazegen.maze.Node;
import imericxu.zhiheng.mazegen.maze.State;

import java.util.*;
import java.util.stream.Collectors;

public class Wilson extends MazeAlgorithm
{
	private final Set<Integer> mazeNodes = new HashSet<>();
	private final Set<Integer> nonMazeNodes = new HashSet<>();
	private final Stack<Integer> currentWalk = new Stack<>();
	private Integer prevId = null;
	
	public Wilson(Node[] nodes)
	{
		super(nodes);
		nonMazeNodes.addAll(Arrays.stream(nodes)
		                          .map(node -> node.id)
		                          .collect(Collectors.toSet())
		                   );
		final int startId = nonMazeNodes.iterator().next();
		nonMazeNodes.remove(startId);
		mazeNodes.add(startId);
		changeState(startId, State.DONE);
	}
	
	@Override
	public void loopOnce()
	{
		if (nonMazeNodes.isEmpty()) return;
		if (currentWalk.isEmpty())
		{
			startNewWalk();
			return;
		}
		
		int currentId = currentWalk.peek();
		// Select a random neighbor that's not the one right before it in the walk
		final var options = nodes[currentId].getNeighbors()
		                                    .stream()
		                                    .filter(id -> !Objects.equals(id, prevId))
		                                    .collect(Collectors.toUnmodifiableList());
		final int randomId = options.get(rand.nextInt(options.size()));
		
		if (mazeNodes.contains(randomId))
		{
			Node.connect(nodes[currentId], nodes[randomId]);
			addWalkToMaze();
		}
		else if (currentWalk.contains(randomId))
		{
			deleteLoop(currentId);
		}
		else
		{
			currentWalk.push(randomId);
			Node.connect(nodes[currentId], nodes[randomId]);
			changeState(randomId, State.EXPLORE);
		}
		
		prevId = currentId;
	}
	
	private void addWalkToMaze()
	{
		mazeNodes.addAll(currentWalk);
		nonMazeNodes.removeAll(currentWalk);
		currentWalk.forEach(id -> changeState(id, State.DONE));
		currentWalk.clear();
	}
	
	private void startNewWalk()
	{
		final int startId = nonMazeNodes.iterator().next();
		currentWalk.push(startId);
		changeState(startId, State.EXPLORE);
	}
	
	private void deleteLoop(int endId)
	{
		int prevId, currentId;
		do
		{
			prevId = currentWalk.pop();
			currentId = currentWalk.peek();
			changeState(prevId, State.DEFAULT);
			Node.disconnect(nodes[currentId], nodes[prevId]);
		} while (currentId != endId);
		
		this.prevId = currentWalk.get(currentWalk.size() - 2);
	}
}
