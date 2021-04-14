package imericxu.zhiheng.mazegen.maze.timers;

import imericxu.zhiheng.mazegen.MazeOptions;
import imericxu.zhiheng.mazegen.maze.GameCanvas;
import imericxu.zhiheng.mazegen.maze.maze_algos.MazeAlgorithm;
import imericxu.zhiheng.mazegen.maze.solve_algos.PathAlgorithm;
import javafx.animation.AnimationTimer;

public class TimerPath extends AnimationTimer
{
	private final Pathfinder pathfinder;
	private final GameCanvas gameCanvas;
	
	public TimerPath(MazeOptions options, MazeAlgorithm mazeAlgo, GameCanvas canvas)
	{
		final var nodes = mazeAlgo.getNodesCopy();
		pathAlgo = switch (options.solveType)
				{
					case ASTAR -> null;
					case BREADTH -> null;
					case TREMAUX -> null;
				};
		gameCanvas = canvas;
	}
	
	@Override
	public void handle(long l)
	{
//		if (pathfinder.step())
//		{
//			gameCanvas.drawMaze(pathfinder.changeList);
//			gameCanvas.drawPath(pathfinder.getPath());
//		}
//		else
//		{
//			gameCanvas.drawMaze(pathfinder.changeList);
//			gameCanvas.drawPath(pathfinder.getPath());
//			stop();
//		}
	}
}
