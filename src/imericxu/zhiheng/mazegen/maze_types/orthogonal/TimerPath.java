package imericxu.zhiheng.mazegen.maze_types.orthogonal;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding.Pathfinder;
import javafx.animation.AnimationTimer;

public class TimerPath extends AnimationTimer
{
    private final Pathfinder pathfinder;
    private final CanvasOrthogonal canvas;
    
    public TimerPath(Pathfinder pathfinder, CanvasOrthogonal canvas)
    {
        
        this.pathfinder = pathfinder;
        this.canvas = canvas;
    }
    
    @Override
    public void handle(long l)
    {
        if (!pathfinder.step())
        {
            canvas.drawPath(pathfinder.getPath());
            canvas.drawMaze();
            canvas.drawPath(pathfinder.getPath());
            stop();
        }
        canvas.drawMaze(pathfinder.getChangeList());
        canvas.drawPath(pathfinder.getPath());
    }
}
