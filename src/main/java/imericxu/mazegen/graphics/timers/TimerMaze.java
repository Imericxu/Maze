package imericxu.mazegen.graphics.timers;

import imericxu.mazegen.logic.maze_algos.MazeAlgorithm;
import imericxu.mazegen.logic.maze_types.Maze;
import imericxu.mazegen.graphics.canvases.MazeCanvas;
import javafx.animation.AnimationTimer;

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
		mazeAlgorithm.changeList.clear();
		if (mazeAlgorithm.isFinished()) {
			listener.onFinishMazeGeneration(mazeAlgorithm.getNodesCopy());
			stop();
			return;
		}
		mazeAlgorithm.loopOnce();
	}
}
