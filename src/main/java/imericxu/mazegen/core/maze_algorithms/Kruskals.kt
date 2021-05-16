package imericxu.mazegen.core.maze_algorithms

import imericxu.mazegen.core.Node
import imericxu.mazegen.core.State
import imericxu.mazegen.core.maze_algorithms.helper.UnionFind

/**
 * Randomized Kruskal’s Algorithm
 *
 * Generates a minimum spanning tree by randomly removing walls between cells that don’t have a path between them.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Kruskal%27s_algorithm">Kruskal’s Algorithm (Wikipedia)</a>
 */
class Kruskals(nodes: Array<Node>) : MazeAlgorithm(nodes) {
	private val disjointSet = UnionFind(nodes.map { it.id }.toSet())

	/**
	 * A pre-shuffled list of all the walls
	 */
	private val walls: Array<Pair<Int, Int>> =
		nodes
			.flatMap { node ->
				node.neighbors
					.flatMap { neighborId ->
						// Order does not matter, so we need to sort each pair
						listOf(node.id, neighborId).sorted().zipWithNext()
					}
			}
			.shuffled()
			.toTypedArray()

	/**
	 * Since the walls are already shuffled, we can simply iterate through them with an index
	 */
	private var index = 0

	override fun loopOnceImpl(): Boolean {
		val wall = walls[index++]

		// Ignore if there's already a path between the two nodes
		if (disjointSet.inSameSet(wall.first, wall.second))
			return false

		// Merge the paths/sets of the two nodes and connect them
		disjointSet.union(wall.first, wall.second)
		nodes[wall.first].connect(nodes[wall.second])

		changeState(wall.first, State.SOLID)
		changeState(wall.second, State.SOLID)

		// The maze is finished once all the nodes are part of the same set
		return disjointSet.numberOfSets() == 1
	}
}
