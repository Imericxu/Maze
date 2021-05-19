package imericxu.mazegen.core.maze_algorithm.algorithms

import imericxu.mazegen.core.Node
import imericxu.mazegen.core.State
import imericxu.mazegen.core.maze_algorithm.MazeAlgorithm

/**
 * Randomized Prim’s Algorithm
 *
 * Prim’s algorithm with no weights and connections are randomly selected.
 * Visually, the maze spreads from a center in a somewhat circular fashion.
 */
class Prims(nodes: Array<Node>) : MazeAlgorithm(nodes) {
	private val frontiers: MutableSet<Int> = HashSet()

	init {
		val start = nodes.random()
		changeState(start.id, State.SOLID)
		start.neighbors.forEach {
			frontiers.add(it)
			changeState(it, State.PARTIAL)
		}
	}

	override fun loopOnceImpl(): Boolean {
		val randomFrontier = nodes[frontiers.random()]

		frontiers.remove(randomFrontier.id)
		changeState(randomFrontier.id, State.SOLID)

		// Add all surrounding unvisited cells to frontiers
		randomFrontier.neighbors
			.filter { states[it] == State.EMPTY }
			.filterNot { frontiers.contains(it) }
			.forEach {
				frontiers.add(it)
				changeState(it, State.PARTIAL)
			}

		// Connect the frontier to a random neighbor maze cell
		randomFrontier.neighbors
			.filter { states[it] == State.SOLID }
			.map { nodes[it] }
			.random()
			.connect(randomFrontier)

		return frontiers.isEmpty()
	}
}
