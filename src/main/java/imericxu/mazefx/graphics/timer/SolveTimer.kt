package imericxu.mazefx.graphics.timer

import imericxu.mazefx.core.solve_algorithm.SolveAlgorithm
import imericxu.mazefx.graphics.MazeDrawer
import javafx.animation.AnimationTimer

class SolveTimer(
	private val mazeDrawer: MazeDrawer,
	private val solveAlgorithm: SolveAlgorithm
) : AnimationTimer() {
	override fun handle(now: Long) {
		mazeDrawer.update(solveAlgorithm)
		mazeDrawer.updateStartEnd(solveAlgorithm.startId, solveAlgorithm.endId)
		mazeDrawer.render()
		mazeDrawer.renderPath(solveAlgorithm.path)
		solveAlgorithm.changeList.clear()
		if (solveAlgorithm.isFinished) {
			stop()
			return
		}
		solveAlgorithm.loopOnce()
	}
}
