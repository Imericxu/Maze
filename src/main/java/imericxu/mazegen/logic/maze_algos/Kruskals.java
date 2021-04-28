package imericxu.mazegen.logic.maze_algos;

import data_structures.UnionFind;
import imericxu.mazegen.logic.Node;
import imericxu.mazegen.logic.State;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h2>Randomized Kruskal’s Algorithm</h2>
 * Generates a minimum spanning tree by randomly removing walls between cells that don’t have a path between them.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Kruskal%27s_algorithm">Kruskal’s Algorithm (Wikipedia)</a>
 */
public class Kruskals extends MazeAlgorithm {
	private final UnionFind<Integer> disjointSet;
	private final Wall[] walls;
	private int index = 0;

	public Kruskals(Node[] nodes) {
		super(nodes);
		disjointSet = new UnionFind<>(Arrays.stream(nodes)
		                                    .map(node -> node.id)
		                                    .collect(Collectors.toSet()));
		Set<Wall> walls = new HashSet<>();
		Arrays.stream(nodes)
		      .forEach(node -> walls.addAll(node.getNeighbors().stream()
		                                        .map(neighbor -> new Wall(node.id, neighbor))
		                                        .collect(Collectors.toSet())));
		this.walls = walls.toArray(new Wall[0]);
		shuffleArray(this.walls);
	}

	private static <T> void shuffleArray(T[] array) {
		int index;
		T temp;
		Random random = new Random();
		for (int i = array.length - 1; i > 0; --i) {
			index = random.nextInt(i + 1);
			temp = array[index];
			array[index] = array[i];
			array[i] = temp;
		}
	}

	@Override
	protected boolean loopOnceImpl() {
		final Wall wall = walls[index++];
		if (disjointSet.inSameSet(wall.a, wall.b)) return false;

		disjointSet.union(wall.a, wall.b);
		Node.connect(nodes[wall.a], nodes[wall.b]);
		changeState(wall.a, State.SOLID);
		changeState(wall.b, State.SOLID);

		return disjointSet.numberOfSets() == 1;
	}

	private static class Wall {
		public final int a;
		public final int b;

		public Wall(int a, int b) {
			if (a < b) {
				this.a = a;
				this.b = b;
			} else {
				this.b = a;
				this.a = b;
			}
		}
	}
}
