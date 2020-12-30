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
		if (pathfinder.step())
		{
//            gameCanvas.drawPath(pathfinder.getPath());
			gameCanvas.drawMaze();
			gameCanvas.drawPath(pathfinder.getPath());
			stop();
		}
//        gameCanvas.drawMaze();
		gameCanvas.drawMaze(pathfinder.getChangeList());
		gameCanvas.drawPath(pathfinder.getPath());
	}
}
