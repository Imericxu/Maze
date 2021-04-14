package imericxu.zhiheng.mazegen.maze.timers;

import imericxu.zhiheng.mazegen.SquareMazeOptions;
import imericxu.zhiheng.mazegen.maze.GameCanvas;
import imericxu.zhiheng.mazegen.maze.maze_algos.MazeAlgorithm;
import imericxu.zhiheng.mazegen.maze.solve_algos.AStar;
import imericxu.zhiheng.mazegen.maze.solve_algos.PathAlgorithm;
import imericxu.zhiheng.mazegen.maze.solve_algos.Tremaux;
import javafx.animation.AnimationTimer;

public class TimerPath extends AnimationTimer
{
	private final PathAlgorithm pathAlgo;
	private final GameCanvas gameCanvas;
	
	public TimerPath(SquareMazeOptions options, MazeAlgorithm mazeAlgo, GameCanvas canvas)
	{
		final var nodes = mazeAlgo.getNodesCopy();
		pathAlgo = switch (options.solveType)
				{
					case ASTAR -> new AStar(nodes, 0, nodes.length - 1, (start, end) -> {
						final int startRow = start / options.cols;
						final int startCol = start % options.cols;
						final int endRow = end / options.cols;
						final int endCol = end % options.cols;
						return Math.hypot(endCol - startCol, endRow - startRow);
					});
					case BREADTH -> null;
					case TREMAUX -> new Tremaux(nodes, 0, nodes.length - 1);
				};
		gameCanvas = canvas;
	}
	
	@Override
	public void handle(long l)
	{
		gameCanvas.drawMaze(pathAlgo);
		gameCanvas.drawPath(pathAlgo.getPath());
		pathAlgo.changeList.clear();
		if (pathAlgo.isFinished())
		{
			stop();
			return;
		}
		pathAlgo.loopOnce();
	}
}
