package imericxu.zhiheng.mazegen;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.CanvasOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.MazeOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.BacktrackerOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.PrimsOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding.AStarOrthogonal;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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
        comboPath.getItems().addAll("None, AStar");
    }
    
    public void launchMaze()
    {
        int rows = Integer.parseInt(inputRows.getText());
        int cols = Integer.parseInt(inputCols.getText());
        int mazeType = comboMaze.getSelectionModel().getSelectedIndex();
        int pathType = comboPath.getSelectionModel().getSelectedIndex();
    
        MazeOrthogonal maze;
        switch (mazeType)
        {
        case 0 -> maze = new PrimsOrthogonal(rows, cols);
        case 1 -> maze = new BacktrackerOrthogonal(rows, cols);
        default -> throw new IllegalStateException("Unexpected value: " + mazeType);
        }
    
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
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
