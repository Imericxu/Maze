package imericxu.mazegen.core.solve_algorithm

import imericxu.mazegen.core.Algorithm
import imericxu.mazegen.core.Node
import imericxu.mazegen.core.State

abstract class SolveAlgorithm(nodes: Array<Node>, val startId: Int, val endId: Int) : Algorithm(nodes) {
	override val states: Array<State> = Array(nodes.size) { State.SOLID }
	val path: List<Int>
		get() = _path.toList()

	@Suppress("PropertyName")
	protected val _path = ArrayDeque<Int>()
}
