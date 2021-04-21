package imericxu.mazegen.logic.solve_algos;

import imericxu.mazegen.logic.Node;
import imericxu.mazegen.logic.State;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * <h2>Breadth-First Search</h2>
 * Simply expands in every direction until the target node is found.
 */
public class BreadthFirstSearch extends SolveAlgorithm {
	private final Queue<Integer> queue = new ArrayDeque<>();
	private final Map<Integer, Integer> cameFrom = new HashMap<>();

	public BreadthFirstSearch(Node[] nodes, int startId, int endId) {
		super(nodes, startId, endId);
		changeState(startId, State.PARTIAL);
		queue.add(startId);
	}

	@Override
	protected boolean loopOnceImpl() {
		final var currentQueue = new ArrayDeque<>(queue);
		queue.clear();

		while (!currentQueue.isEmpty()) {
			final int currentId = currentQueue.poll();
			if (currentId == endId) {
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
