package imericxu.zhiheng.mazegen;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.CanvasOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.BacktrackerOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.PrimsOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding.AStarOrthogonal;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        // PrimsOrthogonal maze = new PrimsOrthogonal(30, 30);
        BacktrackerOrthogonal maze = new BacktrackerOrthogonal(20, 20);
        CanvasOrthogonal canvas = new CanvasOrthogonal(maze);
        StackPane root = new StackPane(canvas);
    
        AStarOrthogonal alg = new AStarOrthogonal(maze);
        AnimationTimer pathTimer = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                canvas.drawMaze();
                alg.step();
                // if (!alg.step())
                // {
                //     stop();
                // }
                canvas.drawPath(alg.getPath());
            }
        };

        AnimationTimer timerRender = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                canvas.drawMaze();
                if (!maze.step())
                {
                    stop();
                    pathTimer.start();
                }
            }
        };
        timerRender.start();
        
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
