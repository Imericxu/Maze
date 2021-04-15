package imericxu.mazegen.logic.maze_algos;

import imericxu.mazegen.logic.Node;
import imericxu.mazegen.logic.State;

import java.util.*;
import java.util.stream.Collectors;

public class Wilson extends MazeAlgorithm {
	private final Set<Integer> mazeNodes = new HashSet<>();
	private final Set<Integer> nonMazeNodes = new HashSet<>();
	private final Stack<Integer> currentWalk = new Stack<>();

	public Wilson(Node[] nodes) {
		super(nodes);
		nonMazeNodes.addAll(Arrays.stream(nodes)
				                    .map(node -> node.id)
				                    .collect(Collectors.toSet())
		);
		// Select a random node to be part of the maze
		final int startId = nonMazeNodes.stream()
				.skip(rand.nextInt(nonMazeNodes.size()))
				.findFirst().orElseThrow();
		nonMazeNodes.remove(startId);
		mazeNodes.add(startId);
		changeState(startId, State.SOLID);
	}

	@Override
	public boolean loopOnceImpl() {
		if (currentWalk.isEmpty()) {
			startNewWalk();
			return false;
		}

		int currentId = currentWalk.peek();
		// Select a random neighbor that's not the one right before it in the walk
		final var options = new ArrayList<>(nodes[currentId].getNeighbors());
		if (currentWalk.size() > 1)
			options.remove(currentWalk.get(currentWalk.size() - 2));
		final int randomId = options.get(rand.nextInt(options.size()));

		if (mazeNodes.contains(randomId)) {
			Node.connect(nodes[currentId], nodes[randomId]);
			addWalkToMaze();
		} else if (currentWalk.contains(randomId)) {
			deleteLoop(randomId);
		} else {
			currentWalk.push(randomId);
			Node.connect(nodes[currentId], nodes[randomId]);
			changeState(randomId, State.PARTIAL);
		}

		return nonMazeNodes.isEmpty();
	}

	private void startNewWalk() {
		final int startId = nonMazeNodes.stream()
				.skip(rand.nextInt(nonMazeNodes.size()))
				.findFirst().orElseThrow();
		currentWalk.push(startId);
		changeState(startId, State.PARTIAL);
	}

	private void addWalkToMaze() {
		mazeNodes.addAll(currentWalk);
		nonMazeNodes.removeAll(currentWalk);
		currentWalk.forEach(id -> changeState(id, State.SOLID));
		currentWalk.clear();
	}

	/**
	 * Removes nodes in the walk up to but not including the collision point.
	 */
	private void deleteLoop(int endId) {
		int prevId, currentId;
		do {
			prevId = currentWalk.pop();
			currentId = currentWalk.peek();
			changeState(prevId, State.EMPTY);
			Node.disconnect(nodes[currentId], nodes[prevId]);
		} while (currentId != endId);
	}
}
