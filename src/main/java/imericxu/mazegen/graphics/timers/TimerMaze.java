package imericxu.mazegen.graphics.timers;

import imericxu.mazegen.core.maze_algorithm.MazeAlgorithm;
import imericxu.mazegen.graphics.Maze;
import imericxu.mazegen.graphics.MazeCanvas;
import javafx.animation.AnimationTimer;

/**
 * A timer for animating maze generation
 */
public class TimerMaze extends AnimationTimer {
	private final Maze.MazeListener listener;
	private final MazeCanvas mazeCanvas;
	private final MazeAlgorithm mazeAlgorithm;

	public TimerMaze(Maze.MazeListener listener, MazeCanvas mazeCanvas, MazeAlgorithm mazeAlgorithm) {
		this.listener = listener;
		this.mazeCanvas = mazeCanvas;
		this.mazeAlgorithm = mazeAlgorithm;
	}

	@Override
	public void handle(long l) {
		mazeCanvas.drawUpdates(mazeAlgorithm);
		mazeAlgorithm.getChangeList().clear();
		if (mazeAlgorithm.getFinished()) {
			listener.onFinishMazeGeneration(mazeAlgorithm.getNodes());
			stop();
			return;
		}
		mazeAlgorithm.loopOnce();
	}
}
