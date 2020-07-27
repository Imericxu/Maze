package imericxu.zhiheng.mazegen;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OrthogonalCanvas;
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
        OrthogonalPrims maze = new OrthogonalPrims(20, 20);
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
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
