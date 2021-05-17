package imericxu.mazegen.graphics.timers;

import imericxu.mazegen.core.maze_algorithm.MazeAlgorithm;
import imericxu.mazegen.graphics.Maze;
import imericxu.mazegen.graphics.MazeDrawer;
import javafx.animation.AnimationTimer;

/**
 * A timer for animating maze generation
 */
public class TimerMaze extends AnimationTimer {
	private final Maze.MazeListener listener;
	private final MazeDrawer mazeDrawer;
	private final MazeAlgorithm mazeAlgorithm;

	public TimerMaze(Maze.MazeListener listener, MazeDrawer mazeDrawer, MazeAlgorithm mazeAlgorithm) {
		this.listener = listener;
		this.mazeDrawer = mazeDrawer;
		this.mazeAlgorithm = mazeAlgorithm;
	}

	@Override
	public void handle(long l) {
		mazeDrawer.drawUpdates(mazeAlgorithm);
		mazeAlgorithm.getChangeList().clear();
		if (mazeAlgorithm.getFinished()) {
			listener.onFinishMazeGeneration(mazeAlgorithm.getNodes());
			stop();
			return;
		}
		mazeAlgorithm.loopOnce();
	}
}
