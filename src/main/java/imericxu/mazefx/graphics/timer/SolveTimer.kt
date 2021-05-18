package imericxu.mazefx.graphics.timer

import imericxu.mazefx.core.solve_algorithm.SolveAlgorithm
import imericxu.mazefx.graphics.MazeDrawer
import javafx.animation.AnimationTimer

class SolveTimer(
	private val mazeDrawer: MazeDrawer,
	private val solveAlgorithm: SolveAlgorithm,
	private val doAnimate: Boolean
) : AnimationTimer() {
	override fun start() {
		if (doAnimate) super.start()
		else {
			stop()
			solveAlgorithm.finishImmediately()
			mazeDrawer.update(solveAlgorithm.nodes, solveAlgorithm.states)
			render()
		}
	}

	override fun handle(now: Long) {
		mazeDrawer.update(solveAlgorithm)
		render()
		solveAlgorithm.changeList.clear()
		if (solveAlgorithm.isFinished) {
			stop()
			return
		}
		solveAlgorithm.loopOnce()
	}

	private fun render() {
		mazeDrawer.updateStartEnd(solveAlgorithm.startId, solveAlgorithm.endId)
		mazeDrawer.render()
		mazeDrawer.renderPath(solveAlgorithm.path)
	}
}
