package imericxu.zhiheng.mazegen.maze.timers;

import imericxu.zhiheng.mazegen.maze.GameCanvas;
import imericxu.zhiheng.mazegen.maze.maze_algos.MazeAlgorithm;
import javafx.animation.AnimationTimer;

public class TimerMaze extends AnimationTimer
{
	private final TimerPath timerPath;
	private final GameCanvas gameCanvas;
	private final MazeAlgorithm mazeAlgorithm;
	private final boolean doSolve;
	
	public TimerMaze(TimerPath timerPath, GameCanvas gameCanvas, MazeAlgorithm mazeAlgorithm, boolean doSolve)
	{
		this.timerPath = timerPath;
		this.gameCanvas = gameCanvas;
		this.mazeAlgorithm = mazeAlgorithm;
		this.doSolve = doSolve;
	}
	
	@Override
	public void handle(long l)
	{
		gameCanvas.drawMaze(
				mazeAlgorithm);
		mazeAlgorithm.changeList.clear();
		if (mazeAlgorithm.isFinished())
		{
			if (doSolve) timerPath.start();
			stop();
			return;
		}
		mazeAlgorithm.loopOnce();
	}
}
