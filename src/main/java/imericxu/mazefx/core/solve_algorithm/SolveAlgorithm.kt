package imericxu.mazefx.core.solve_algorithm

import imericxu.mazefx.core.Algorithm
import imericxu.mazefx.core.Node
import imericxu.mazefx.core.State

abstract class SolveAlgorithm(nodes: Array<Node>, val startId: Int, val endId: Int) : Algorithm(nodes) {
	override val states: Array<State> = Array(nodes.size) { State.SOLID }
	val path: List<Int>
		get() = _path.toList()

	@Suppress("PropertyName")
	protected val _path = ArrayDeque<Int>()

	// TODO Remove temporary fix by adding the path to the changelist
	override fun loopOnce() {
		changeList.addAll(path)
		super.loopOnce()
	}
}
