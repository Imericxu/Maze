package imericxu.mazegen.logic.maze_algos;

import imericxu.mazegen.logic.Node;
import imericxu.mazegen.logic.State;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h2>Randomized Prim’s Algorithm</h2>
 * Prim’s algorithm with no weights and connections are randomly selected.
 * Visually, the maze spreads from a center in a somewhat circular fashion.
 */
public class Prims extends MazeAlgorithm {
	private final Set<Integer> frontiers = new HashSet<>();

	public Prims(Node[] nodes) {
		super(nodes);
		Node start = nodes[rand.nextInt(nodes.length)];
		changeState(start, State.SOLID);
		for (Integer neighbor : start.getNeighbors()) {
			frontiers.add(neighbor);
			changeState(neighbor, State.PARTIAL);
		}
	}

	@Override
	public boolean loopOnceImpl() {
		final int index = rand.nextInt(frontiers.size());
		final int currentId = frontiers.stream().skip(index).findFirst().orElseThrow();
		frontiers.remove(currentId);
		addFrontiersOf(currentId);
		connectRandMazeCell(currentId);

		return frontiers.isEmpty();
	}

	private void addFrontiersOf(int nodeId) {
		changeState(nodeId, State.SOLID);

		nodes[nodeId].getNeighbors().stream()
				.filter(id -> states[id] == State.EMPTY && !frontiers.contains(id))
				.forEach(id -> {
					frontiers.add(id);
					changeState(id, State.PARTIAL);
				});
	}

	private void connectRandMazeCell(int nodeId) {
		final var connectedMazeCells = nodes[nodeId].getNeighbors().stream()
				.filter(id -> states[id] == State.SOLID)
				.collect(Collectors.toUnmodifiableList());
		final int index = rand.nextInt(connectedMazeCells.size());
		final int randomId = connectedMazeCells.get(index);
		Node.connect(nodes[nodeId], nodes[randomId]);
	}
}
