package imericxu.zhiheng.mazegen.maze.timers;

import imericxu.zhiheng.mazegen.maze.GameCanvas;
import imericxu.zhiheng.mazegen.maze.Pathfinder;
import javafx.animation.AnimationTimer;

public class TimerPath extends AnimationTimer
{
	private final Pathfinder pathfinder;
	private final GameCanvas gameCanvas;

	public TimerPath(Pathfinder pathfinder, GameCanvas gameCanvas)
	{
		
		this.pathfinder = pathfinder;
		this.gameCanvas = gameCanvas;
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
