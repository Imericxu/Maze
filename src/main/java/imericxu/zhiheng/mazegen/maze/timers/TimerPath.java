package imericxu.zhiheng.mazegen.maze.timers;

import imericxu.zhiheng.mazegen.MazeOptions;
import imericxu.zhiheng.mazegen.maze.GameCanvas;
import imericxu.zhiheng.mazegen.maze.maze_algos.MazeAlgorithm;
import imericxu.zhiheng.mazegen.maze.solve_algos.PathAlgorithm;
import imericxu.zhiheng.mazegen.maze.solve_algos.Tremaux;
import javafx.animation.AnimationTimer;

public class TimerPath extends AnimationTimer
{
	private final PathAlgorithm pathAlgo;
	private final GameCanvas gameCanvas;
	
	public TimerPath(MazeOptions options, MazeAlgorithm mazeAlgo, GameCanvas canvas)
	{
		final var nodes = mazeAlgo.getNodesCopy();
		pathAlgo = switch (options.solveType)
				{
					case ASTAR, BREADTH -> throw new RuntimeException();
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
