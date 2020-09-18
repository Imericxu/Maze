package imericxu.zhiheng.mazegen.maze_types.orthogonal;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Maze;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding.Pathfinder;
import javafx.animation.AnimationTimer;

public class TimerMaze extends AnimationTimer
{
    private final TimerPath timerPath;
    private final CanvasOrthogonal canvas;
    private final Maze maze;
    private final Pathfinder pathfinder;
    private final boolean doSolve;
    private final boolean doShowPathfinding;
    
    public TimerMaze(TimerPath timerPath, CanvasOrthogonal canvas, Maze maze, Pathfinder pathfinder, boolean doSolve,
            boolean doShowPathfinding)
    {
        this.timerPath = timerPath;
        this.canvas = canvas;
        this.maze = maze;
        this.pathfinder = pathfinder;
        this.doSolve = doSolve;
        this.doShowPathfinding = doShowPathfinding;
    }
    
    public static void solveMaze(TimerPath timerPath, CanvasOrthogonal canvas, Maze maze, Pathfinder pathfinder,
            boolean doShowPathfinding)
    {
        pathfinder.setMaze(maze);
        if (doShowPathfinding) timerPath.start();
        else
        {
            pathfinder.instantSolve();
            canvas.drawMaze();
            canvas.drawPath(pathfinder.getPath());
        }
    }
    
    @Override
    public void handle(long l)
    {
        if (maze.step())
        {
            canvas.drawMaze();
            if (doSolve) solveMaze(timerPath, canvas, maze, pathfinder, doShowPathfinding);
            stop();
        }
        // canvas.drawMaze(maze.getChangeList());
        canvas.drawMaze();
    }
}
