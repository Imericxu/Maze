package imericxu.mazegen.core.solve_algorithm.algorithm

import imericxu.mazegen.core.Node
import imericxu.mazegen.core.State
import imericxu.mazegen.core.solve_algorithm.SolveAlgorithm

/**
 * Breadth-First Search
 *
 * Simply expands in every direction until the target node is found.
 */
class Breadth(nodes: Array<Node>, startId: Int, endId: Int) : SolveAlgorithm(nodes, startId, endId) {
	private val queue = ArrayDeque<Int>()
	private val cameFrom = HashMap<Int, Int>()

	init {
		changeState(startId, State.PARTIAL)
		queue.addLast(startId)
	}

	override fun loopOnceImpl(): Boolean {
		val currentQueue = ArrayDeque(queue).also { queue.clear() }

		do {
			val currentId = currentQueue.removeFirst()

			if (currentId == endId) {
				_path.addAll(AStar.constructPath(cameFrom, endId))
				return true
			}

			nodes[currentId].connections
				.filter { states[it] == State.SOLID }
				.forEach {
					cameFrom[it] = currentId
					changeState(it, State.PARTIAL)
					queue.addLast(it)
				}
		} while (!currentQueue.isEmpty())

		return false
	}
}
