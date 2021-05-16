package imericxu.mazegen.graphics.timers;

import imericxu.mazegen.core.maze_algorithms.MazeAlgorithm;
import imericxu.mazegen.graphics.canvases.MazeCanvas;
import imericxu.mazegen.maze_shapes.Maze;
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
