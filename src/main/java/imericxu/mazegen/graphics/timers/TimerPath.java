package imericxu.mazegen.graphics.timers;

import imericxu.mazegen.graphics.canvases.MazeCanvas;
import imericxu.mazegen.logic.solve_algos.SolveAlgorithm;
import javafx.animation.AnimationTimer;

public class TimerPath extends AnimationTimer {
	private final MazeCanvas canvas;
	private final SolveAlgorithm solveAlgo;

	public TimerPath(MazeCanvas canvas, SolveAlgorithm solveAlgo) {
		this.canvas = canvas;
		this.solveAlgo = solveAlgo;
	}

	@Override
	public void handle(long l) {
		canvas.drawUpdates(solveAlgo);
		canvas.drawPath(solveAlgo.getPath());
		solveAlgo.changeList.clear();
		if (solveAlgo.isFinished()) {
			stop();
			return;
		}
		solveAlgo.loopOnce();
	}
}
