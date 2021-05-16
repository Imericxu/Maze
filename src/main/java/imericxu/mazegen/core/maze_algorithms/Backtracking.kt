package imericxu.mazegen.core.maze_algorithms

import imericxu.mazegen.core.Node
import imericxu.mazegen.core.State
import java.util.*
import kotlin.random.Random

/**
 * “Recursive” Backtracking Algorithm
 *
 * Randomly connects to unvisited nodes until it hits a dead end, then it “backtracks”
 */
class Backtracking(nodes: Array<Node>) : MazeAlgorithm(nodes) {
	private val exploreStack = ArrayDeque<Int>()

	init {
		val startId = Random.nextInt(0, nodes.size)
		exploreStack.addFirst(startId)
		changeState(startId, State.PARTIAL)
	}

	override fun loopOnceImpl(): Boolean {
		val current = nodes[exploreStack.first()]

		// Connect to an unvisited neighbor if it exists, else, backtrack.
		current.neighbors
			.filter { states[it] == State.EMPTY }
			.randomOrNull()
			?.let {
				current.connect(nodes[it])
				changeState(it, State.PARTIAL)
				exploreStack.addFirst(it)
			}
			?: run {
				exploreStack.removeFirst()
				changeState(current.id, State.SOLID)
			}

		return exploreStack.isEmpty()
	}
}
