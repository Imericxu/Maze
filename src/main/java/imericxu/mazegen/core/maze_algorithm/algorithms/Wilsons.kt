package imericxu.mazegen.core.maze_algorithm.algorithms

import imericxu.mazegen.core.Node
import imericxu.mazegen.core.State
import imericxu.mazegen.core.maze_algorithm.MazeAlgorithm

/**
 * Wilson’s Algorithm
 *
 * Uses loop-erased walks to generate a uniform spanning tree/maze.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Loop-erased_random_walk">
 * Loop-erased walk on Wikipedia
 * </a>
 */
class Wilsons(nodes: Array<Node>) : MazeAlgorithm(nodes) {
	private val mazeNodes: MutableSet<Int> = HashSet()
	private val nonMazeNodes: MutableSet<Int> = nodes.map { it.id }.toMutableSet()
	private val currentWalk = ArrayDeque<Int>()

	init {
		val startId = nonMazeNodes.random()
		nonMazeNodes.remove(startId)
		mazeNodes.add(startId)
		changeState(startId, State.SOLID)
	}

	override fun loopOnceImpl(): Boolean {
		if (currentWalk.isEmpty()) {
			startNewWalk()
			return false
		}

		val currentId = currentWalk.last()

		// random neighbor not including the preceding node
		val randomId = nodes[currentId].neighbors
			.minus(with(currentWalk) { getOrElse(size - 2) { -1 } })
			.random()

		when {
			mazeNodes.contains(randomId) -> {
				nodes[currentId].connect(nodes[randomId])
				transferWalkToMaze()
			}
			currentWalk.contains(randomId) -> {
				deleteLoop(randomId)
			}
			else -> {
				currentWalk.addLast(randomId)
				nodes[currentId].connect(nodes[randomId])
				changeState(randomId, State.PARTIAL)
			}
		}

		return nonMazeNodes.isEmpty()
	}

	private fun startNewWalk() {
		val randomId = nonMazeNodes.random()
		currentWalk.addLast(randomId)
		changeState(randomId, State.PARTIAL)
	}

	private fun transferWalkToMaze() {
		mazeNodes.addAll(currentWalk)
		nonMazeNodes.removeAll(currentWalk)
		currentWalk.forEach { changeState(it, State.SOLID) }
		currentWalk.clear()
	}

	private fun deleteLoop(endId: Int) {
		var prevId: Int
		var currentId: Int
		do {
			prevId = currentWalk.removeLast()
			currentId = currentWalk.last()
			changeState(prevId, State.EMPTY)
			nodes[currentId].disconnect(nodes[prevId])
		} while (currentId != endId)
	}
}
