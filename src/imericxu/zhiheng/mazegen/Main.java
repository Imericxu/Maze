package imericxu.zhiheng.mazegen;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.CanvasOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.algorithms.PrimsOrthogonal;
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
        PrimsOrthogonal maze = new PrimsOrthogonal(20, 20);
        CanvasOrthogonal canvas = new CanvasOrthogonal(maze);
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
