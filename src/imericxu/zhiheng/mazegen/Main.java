package imericxu.zhiheng.mazegen;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.CanvasOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.MazeOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.BacktrackerOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.PrimsOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding.Tremaux;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        MazeOrthogonal maze = new BacktrackerOrthogonal(30, 30);
        CanvasOrthogonal canvas = new CanvasOrthogonal(maze);
        StackPane root = new StackPane(canvas);
        
        while (true)
        {
            if (!maze.step()) break;
        }
    
        Tremaux alg = new Tremaux(maze);
        AnimationTimer timer = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                canvas.drawMaze();
                if (!alg.step())
                {
                    canvas.drawMaze();
                    canvas.drawPath(alg.getPath());
                    stop();
                }
                canvas.drawPath(alg.getPath());
            }
        };
        timer.start();
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Maze Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
