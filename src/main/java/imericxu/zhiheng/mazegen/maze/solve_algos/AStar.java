package imericxu.zhiheng.mazegen.maze.solve_algos;

import imericxu.zhiheng.mazegen.maze.Node;
import imericxu.zhiheng.mazegen.maze.State;

import java.util.*;

public class AStar extends PathAlgorithm
{
	private final PriorityQueue<Integer> openSet;
	private final Map<Integer, Integer> cameFrom = new HashMap<>();
	private final Heuristic heuristic;
	/**
	 * For id {@code n}, {@code gScore[n]} is the cost of the cheapest path from start to {@code n} currently known.
	 */
	private final Map<Integer, Double> gScores = new HashMap<>();
	/**
	 * The predicted heuristic of each node
	 */
	private final Map<Integer, Double> fScores = new HashMap<>();
	
	public AStar(Node[] nodes, int startId, int endId, Heuristic heuristic)
	{
		super(nodes, startId, endId);
		openSet = new PriorityQueue<>(Comparator.comparingDouble(fScores::get));
		this.heuristic = heuristic;
		
		changeState(startId, State.PARTIAL);
		openSet.add(startId);
		gScores.put(startId, 0.0);
		fScores.put(startId, heuristic.calculate(startId, endId));
	}
	
	public static List<Integer> constructPath(Map<Integer, Integer> cameFrom, int current)
	{
		final var path = new ArrayList<>(Collections.singletonList(current));
		while (cameFrom.containsKey(current))
		{
			current = cameFrom.get(current);
			path.add(current);
		}
		return path;
	}
	
	@Override
	protected boolean loopOnceImpl()
	{
		final Node current = nodes[Objects.requireNonNull(openSet.peek())];
		
		if (current.id == endId)
		{
			path.addAll(constructPath(cameFrom, current.id));
			return true;
		}
		
		openSet.remove(current.id);
		current.getConnections().forEach(connectionId -> {
			final double newGScore = gScores.get(current.id) + 1;
			if (newGScore < gScores.getOrDefault(connectionId, Double.POSITIVE_INFINITY))
			{
				cameFrom.put(connectionId, current.id);
				gScores.put(connectionId, newGScore);
				fScores.put(connectionId, gScores.get(connectionId) + heuristic.calculate(connectionId, endId));
				if (!openSet.contains(connectionId))
				{
					openSet.add(connectionId);
					changeState(connectionId, State.PARTIAL);
				}
			}
		});
		
		return false;
	}
	
	public interface Heuristic
	{
		double calculate(int id1, int id2);
	}
}
