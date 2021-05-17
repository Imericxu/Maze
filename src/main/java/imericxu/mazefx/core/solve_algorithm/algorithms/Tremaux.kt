package imericxu.mazefx.core.solve_algorithm.algorithms

import imericxu.mazefx.core.Node
import imericxu.mazefx.core.State
import imericxu.mazefx.core.solve_algorithm.SolveAlgorithm

/**
 * Trémaux’s Algorithm
 *
 * Randomly turns at every intersection.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Maze-solving_algorithm#Tr%C3%A9maux%27s_algorithm">
 * Wikipedia description
 * </a>
 */
class Tremaux(nodes: Array<Node>, startId: Int, endId: Int) : SolveAlgorithm(nodes, startId, endId) {
	init {
		_path.addLast(startId)
		changeState(startId, State.PARTIAL)
	}

	override fun loopOnceImpl(): Boolean {
		val currentId = path.last()

		// Add a random unvisited node to the path or pop back if there are none
		nodes[currentId].connections
			.minus(with(_path) { getOrElse(size - 2) { -1 } })
			.filter { states[it] == State.SOLID }
			.randomOrNull()
			?.let {
				_path.addLast(it)
				changeState(it, State.PARTIAL)
				return it == endId
			}
			?: run {
				_path.removeLast()
				return false
			}
	}
}
