package imericxu.mazegen.core.solve_algorithms

import imericxu.mazegen.core.Node
import imericxu.mazegen.core.State
import java.util.*

/**
 * A* Algorithm
 *
 * Uses heuristics to determine the best path to take.
 *
 * @param heuristic The heuristic is not included in the class and needs to be provided in the constructor.
 */
class AStar(
	nodes: Array<Node>,
	startId: Int,
	endId: Int,
	val heuristic: (id1: Int, id2: Int) -> Double
) : SolveAlgorithm(nodes, startId, endId) {

	private val openQueue = PriorityQueue<Int>(compareBy {
		fScores.getOrDefault(it, Double.POSITIVE_INFINITY)
	})
	private val cameFrom = HashMap<Int, Int>()
	private val gScores = HashMap<Int, Double>()
	private val fScores = HashMap<Int, Double>()

	init {
		openQueue.add(startId)
		gScores[startId] = 0.0
		fScores[startId] = heuristic(startId, endId)
		changeState(startId, State.PARTIAL)
	}

	override fun loopOnceImpl(): Boolean {
		val currentId = openQueue.peek()

		if (currentId == endId) {
			_path.addAll(constructPath(cameFrom, currentId))
			return true
		}

		openQueue.remove(currentId)

		nodes[currentId].connections
			.map { GScore(it, gScores[currentId]!! + 1) }
			.filter { it.gScore < gScores.getOrDefault(it.id, Double.POSITIVE_INFINITY) }
			.forEach {
				cameFrom[it.id] = currentId
				gScores[it.id] = it.gScore
				fScores[it.id] = it.gScore + heuristic(it.id, endId)
				if (!openQueue.contains(it.id)) {
					openQueue.add(it.id)
					changeState(it.id, State.PARTIAL)
				}
			}

		return false
	}

	companion object {
		fun constructPath(cameFrom: HashMap<Int, Int>, firstId: Int): List<Int> {
			var currentId = firstId
			val path = mutableListOf(currentId)
			do {
				currentId = cameFrom[currentId] ?: return path
				path.add(currentId)
			} while (true)
		}
	}

	private data class GScore(val id: Int, val gScore: Double)
}
