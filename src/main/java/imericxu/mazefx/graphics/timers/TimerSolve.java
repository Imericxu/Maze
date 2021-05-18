package imericxu.mazefx.graphics.timers;

import imericxu.mazefx.core.solve_algorithm.SolveAlgorithm;
import imericxu.mazefx.graphics.MazeDrawer;
import javafx.animation.AnimationTimer;

/**
 * A timer for animating maze solving
 */
public class TimerSolve extends AnimationTimer {
	private final MazeDrawer canvas;
	private final SolveAlgorithm solveAlgo;

	public TimerSolve(MazeDrawer canvas, SolveAlgorithm solveAlgo) {
		this.canvas = canvas;
		this.solveAlgo = solveAlgo;
	}

	@Override
	public void handle(long l) {
		canvas.drawUpdates(solveAlgo);
		canvas.drawStartAndEnd(solveAlgo.getStartId(), solveAlgo.getEndId());
		// TODO represent the maze in a new way to prevent trails
		canvas.drawPath(solveAlgo.getPath());
		solveAlgo.getChangeList().clear();
		if (solveAlgo.isFinished()) {
			stop();
			return;
		}
		solveAlgo.loopOnce();
	}
}
