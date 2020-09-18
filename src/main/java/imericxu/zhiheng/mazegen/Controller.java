package imericxu.zhiheng.mazegen;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.CanvasOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.TimerMaze;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.TimerPath;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Backtracker;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Maze;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Prims;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Wilson;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding.AStar;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding.BreadthFirstSearch;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding.Pathfinder;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding.Tremaux;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.ToggleSwitch;

import java.util.function.UnaryOperator;

public class Controller
{
    @FXML
    private TextField inputRows;
    @FXML
    private TextField inputCols;
    @FXML
    private TextField inputRatio;
    @FXML
    private ComboBox<String> comboMaze;
    @FXML
    private ToggleSwitch switchShowMazeGen;
    @FXML
    private ToggleSwitch switchDoSolve;
    @FXML
    private ComboBox<String> comboPath;
    @FXML
    private ToggleSwitch switchShowPathfinding;

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

        UnaryOperator<TextFormatter.Change> floatFilter = change ->
        {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*"))
            {
                return change;
            }
            return null;
        };

        inputRows.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        inputCols.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
        inputRatio.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), null, floatFilter));
        comboMaze.getItems().addAll("Prim's Algorithm", "Recursive Backtracker", "Wilson's Algorithm");
        comboPath.getItems().addAll("Trémaux", "AStar", "Breadth First Search");
    }

    /**
     * Attempts to launch the maze after pressing start button
     */
    @FXML
    public void startPressed()
    {
        int rows = Integer.parseInt(inputRows.getText());
        int cols = Integer.parseInt(inputCols.getText());
        double cellWallRatio = Double.parseDouble(inputRatio.getText());
        int mazeType = comboMaze.getSelectionModel().getSelectedIndex();
        int pathType = comboPath.getSelectionModel().getSelectedIndex();
        boolean doShowMazeGen = switchShowMazeGen.isSelected();
        boolean doSolve = switchDoSolve.isSelected();
        boolean doShowPathfinding = switchShowPathfinding.isSelected();

        Maze maze = switch (mazeType)
                {
                    case 0 -> new Prims(rows, cols);
                    case 2 -> new Wilson(rows, cols);
                    default -> new Backtracker(rows, cols);
                };

        Pathfinder pathfinder = switch (pathType)
                {
                    case 1 -> new AStar();
                    case 2 -> new BreadthFirstSearch();
                    default -> new Tremaux();
                };

        launchMaze(cellWallRatio, maze, doShowMazeGen, doSolve, pathfinder, doShowPathfinding);
    }

    private void launchMaze(double cellWallRatio, Maze maze, boolean doShowMazeGen, boolean doSolve,
                            Pathfinder pathfinder, boolean doShowPathfinding)
    {
        Stage stage = new Stage();
        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // Need to show stage to actually maximize
        stage.setOpacity(0);
        stage.show();
        stage.setMaximized(true);

        stage.setTitle("Maze");
        root.setStyle("-fx-background-color: black");

        CanvasOrthogonal canvas = new CanvasOrthogonal(scene.getWidth(), scene.getHeight(), maze, cellWallRatio);

        root.getChildren().add(canvas);

        var timerPath = new TimerPath(pathfinder, canvas);
        var timerMaze = new TimerMaze(timerPath, canvas, maze, pathfinder, doSolve, doShowPathfinding);

        if (doShowMazeGen) timerMaze.start();
        else
        {
            maze.instantSolve();
            maze.getChangeList().clear();
            canvas.drawMaze();

            if (doSolve)
            {
                TimerMaze.solveMaze(timerPath, canvas, maze, pathfinder, doShowPathfinding);
            }
        }

        stage.setOpacity(1);
        stage.setResizable(false); // Must come after stage.show() to work

        stage.setOnCloseRequest(windowEvent ->
        {
            timerPath.stop();
            timerMaze.stop();
        });
    }
}
