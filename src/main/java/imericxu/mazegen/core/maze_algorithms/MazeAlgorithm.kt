package imericxu.mazegen.core.maze_algorithms

import imericxu.mazegen.core.Algorithm
import imericxu.mazegen.core.Node
import imericxu.mazegen.core.State

abstract class MazeAlgorithm(nodes: Array<Node>) : Algorithm(nodes) {
	override val states: Array<State> = Array(nodes.size) { State.EMPTY }
}
