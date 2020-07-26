package imericxu.zhiheng.mazegen;

import imericxu.zhiheng.mazegen.maze_types.Cell;
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
    private long wait;
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        OrthogonalMaze maze = new OrthogonalPrims(20, 30);
        OrthogonalCanvas canvas = new OrthogonalCanvas(maze);
        StackPane root = new StackPane(canvas);
    
        wait = 0;
        AnimationTimer timerRender = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                if (l - wait >= 100_000_000)
                {
                    canvas.drawMaze();
                    // maze.step();
                    wait = l;
                }
            }
        };
        timerRender.start();
        
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
