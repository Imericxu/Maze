package imericxu.mazegen.graphics.timers;

import imericxu.mazegen.core.solve_algorithms.SolveAlgorithm;
import imericxu.mazegen.graphics.canvases.MazeCanvas;
import javafx.animation.AnimationTimer;

/**
 * A timer for animating maze solving
 */
public class TimerSolve extends AnimationTimer {
	private final MazeCanvas canvas;
	private final SolveAlgorithm solveAlgo;

	public TimerSolve(MazeCanvas canvas, SolveAlgorithm solveAlgo) {
		this.canvas = canvas;
		this.solveAlgo = solveAlgo;
	}

	@Override
	public void handle(long l) {
		canvas.drawUpdates(solveAlgo);
		canvas.drawStartAndEnd(solveAlgo.getStartId(), solveAlgo.getEndId());
		canvas.drawPath(solveAlgo.getPath());
		solveAlgo.getChangeList().clear();
		if (solveAlgo.getFinished()) {
			stop();
			return;
		}
		solveAlgo.loopOnce();
	}
}
