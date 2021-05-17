package imericxu.mazefx.core.maze_algorithm

import imericxu.mazefx.core.Algorithm
import imericxu.mazefx.core.Node
import imericxu.mazefx.core.State

abstract class MazeAlgorithm(nodes: Array<Node>) : Algorithm(nodes) {
	override val states: Array<State> = Array(nodes.size) { State.EMPTY }
}
