package imericxu.zhiheng.mazegen;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.CanvasOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.MazeOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.BacktrackerOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.PrimsOrthogonal;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

public class Controller
{
    @FXML
    private TextField inputRows;
    @FXML
    private TextField inputCols;
    @FXML
    private ComboBox<String> comboMaze;
    @FXML
    private ComboBox<String> comboPath;
    @FXML
    private Button btnStart;
    
    @FXML
    public void initialize()
    {
        UnaryOperator<TextFormatter.Change> integerFilter = change ->
        {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*"))
            {
                return change;
            }
            return null;
        };
        
        inputRows.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        inputCols.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        comboMaze.getItems().addAll("Prim's Algorithm", "Recursive Backtracker");
        comboPath.getItems().addAll("None", "AStar");
    }
    
    /**
     * Attempts to launch the maze after pressing {@link #btnStart start button}
     */
    @FXML
    public void launchMaze()
    {
        Stage stage = new Stage();
        stage.setTitle("Maze");
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        // Need to show stage to actually maximize
        stage.setOpacity(0);
        stage.show();
        
        MazeOrthogonal maze = new PrimsOrthogonal(30, 80);
        CanvasOrthogonal canvas = new CanvasOrthogonal(maze, scene.getWidth(), scene.getHeight());
        AnimationTimer timer = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                canvas.drawMaze();
                maze.step();
            }
        };
        timer.start();
        root.getChildren().add(canvas);
        
        stage.setOpacity(1);
        stage.setResizable(false); // Must come after stage.show() to work
    }
}
