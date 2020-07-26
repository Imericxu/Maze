package imericxu.zhiheng.mazegen;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.OrthogonalCanvas;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OrthogonalMaze;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.algorithms.OrthogonalPrims;
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
        OrthogonalMaze maze = new OrthogonalPrims(30, 30);
        OrthogonalCanvas canvas = new OrthogonalCanvas(maze);
        StackPane root = new StackPane(canvas);
        
        AnimationTimer timerRender = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                canvas.drawMaze();
                maze.step();
            }
        };
        timerRender.start();
        
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
