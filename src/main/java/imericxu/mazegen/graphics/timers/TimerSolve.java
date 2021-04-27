package imericxu.mazegen.graphics.timers;

import imericxu.mazegen.graphics.canvases.MazeCanvas;
import imericxu.mazegen.logic.solve_algos.SolveAlgorithm;
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
		canvas.drawStartAndEnd(solveAlgo.startId, solveAlgo.endId);
		canvas.drawPath(solveAlgo.getPath());
		solveAlgo.changeList.clear();
		if (solveAlgo.isFinished()) {
			stop();
			return;
		}
		solveAlgo.loopOnce();
	}
}
