package imericxu.mazefx.graphics.timer

import imericxu.mazefx.core.Node
import imericxu.mazefx.core.maze_algorithm.MazeAlgorithm
import imericxu.mazefx.graphics.MazeDrawer
import javafx.animation.AnimationTimer

class MazeTimer(
	private val mazeDrawer: MazeDrawer,
	private val mazeAlgorithm: MazeAlgorithm,
	private val solveFunction: (Array<Node>) -> Unit
) : AnimationTimer() {
	override fun handle(now: Long) {
		mazeDrawer.update(mazeAlgorithm)
		mazeDrawer.render()
		mazeAlgorithm.changeList.clear()
		if (mazeAlgorithm.isFinished) {
			solveFunction(mazeAlgorithm.nodes)
			stop()
			return
		}
		mazeAlgorithm.loopOnce()
	}
}
