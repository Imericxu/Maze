package imericxu.zhiheng.mazegen.maze.timers;

import imericxu.zhiheng.mazegen.maze.GameCanvas;
import imericxu.zhiheng.mazegen.maze.Maze;
import imericxu.zhiheng.mazegen.maze.Pathfinder;
import javafx.animation.AnimationTimer;

public class TimerMaze extends AnimationTimer
{
    private final TimerPath timerPath;
    private final GameCanvas gameCanvas;
    private final Maze maze;
    private final Pathfinder pathfinder;
    private final boolean doSolve;
    private final boolean doShowPathfinding;
    
    public TimerMaze(TimerPath timerPath, GameCanvas gameCanvas, Maze maze, Pathfinder pathfinder, boolean doSolve,
                     boolean doShowPathfinding)
    {
        this.timerPath = timerPath;
        this.gameCanvas = gameCanvas;
        this.maze = maze;
        this.pathfinder = pathfinder;
        this.doSolve = doSolve;
        this.doShowPathfinding = doShowPathfinding;
    }
    
    public static void solveMaze(TimerPath timerPath, GameCanvas gameCanvas, Maze maze, Pathfinder pathfinder,
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
    }
    
    @Override
    public void handle(long l)
    {
        if (maze.step())
        {
            gameCanvas.drawMaze();
            if (doSolve) solveMaze(timerPath, gameCanvas, maze, pathfinder, doShowPathfinding);
            stop();
        }
        // canvas.drawMaze(maze.getChangeList());
        gameCanvas.drawMaze();
    }
}
