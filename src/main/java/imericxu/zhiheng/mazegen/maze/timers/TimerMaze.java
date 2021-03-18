package imericxu.zhiheng.mazegen.maze.timers;

import imericxu.zhiheng.mazegen.maze.GameCanvas;
import imericxu.zhiheng.mazegen.maze.Pathfinder;
import imericxu.zhiheng.mazegen.maze.maze_algos.MazeAlgorithm;
import javafx.animation.AnimationTimer;

public class TimerMaze extends AnimationTimer
{
	private final TimerPath timerPath;
	private final GameCanvas gameCanvas;
	private final MazeAlgorithm mazeAlgorithm;
	private final Pathfinder pathfinder;
	private final boolean doSolve;
	private final boolean doShowPathfinding;
	
	public TimerMaze(TimerPath timerPath, GameCanvas gameCanvas, MazeAlgorithm mazeAlgorithm, Pathfinder pathfinder,
	                 boolean doSolve, boolean doShowPathfinding)
	{
		this.timerPath = timerPath;
		this.gameCanvas = gameCanvas;
		this.mazeAlgorithm = mazeAlgorithm;
		this.pathfinder = pathfinder;
		this.doSolve = doSolve;
		this.doShowPathfinding = doShowPathfinding;
	}
    
  /*  public static void solveMaze(TimerPath timerPath, GameCanvas gameCanvas, MazeSquare maze, Pathfinder pathfinder,
                                 boolean doShowPathfinding)
    {
        pathfinder.setMaze(maze);
        if (doShowPathfinding) timerPath.start();
        else
        {
            pathfinder.instantSolve();
            gameCanvas.drawMaze();
            gameCanvas.drawPath(pathfinder.getPath());
        }
    }*/
	
	@Override
	public void handle(long l)
	{
		if (mazeAlgorithm.step())
		{
			gameCanvas.drawMaze(mazeAlgorithm.changeList);
		}
		else
		{
			if (doSolve)
				timerPath.start();
			stop();
		}
	}
}
