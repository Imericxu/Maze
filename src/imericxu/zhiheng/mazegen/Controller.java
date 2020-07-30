package imericxu.zhiheng.mazegen;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.CanvasOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Backtracker;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Maze;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Prims;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding.AStar;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding.Pathfinder;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding.Tremaux;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

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
    private CheckBox checkShowMazeGen;
    @FXML
    private CheckBox checkDoSolve;
    @FXML
    private ComboBox<String> comboPath;
    @FXML
    private CheckBox checkShowPathfinding;
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
        comboMaze.getItems().addAll("Prim's Algorithm", "Recursive Backtracker");
        comboPath.getItems().addAll("Trémaux", "AStar");
    }
    
    /**
     * Attempts to launch the maze after pressing {@link #btnStart start button}
     */
    @FXML
    public void startPressed()
    {
        int rows = Integer.parseInt(inputRows.getText());
        int cols = Integer.parseInt(inputCols.getText());
        double cellWallRatio = Double.parseDouble(inputRatio.getText());
        int mazeType = comboMaze.getSelectionModel().getSelectedIndex();
        boolean doShowMazeGen = checkShowMazeGen.isSelected();
        boolean doSolve = checkDoSolve.isSelected();
        int algType = comboPath.getSelectionModel().getSelectedIndex();
        boolean doShowPathfinding = checkShowPathfinding.isSelected();
    
        Maze maze;
        switch (mazeType)
        {
        case 0 -> maze = new Prims(rows, cols);
        case 1 -> maze = new Backtracker(rows, cols);
        default -> maze = new Backtracker(rows, cols);
        }
    
        Pathfinder pathfinder;
        switch (algType)
        {
        case 0 -> pathfinder = new Tremaux(maze);
        case 1 -> pathfinder = new AStar(maze);
        default -> pathfinder = new Tremaux(maze);
        }
    
        launchMaze(cellWallRatio, maze, doShowMazeGen, doSolve, pathfinder, doShowPathfinding);
    }
    
    private void launchMaze(double cellWallRatio, Maze maze, boolean doShowMazeGen, boolean doSolve,
            Pathfinder pathfinder, boolean doShowPathfinding)
    {
        Stage stage = new Stage();
        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        // Need to show stage to actually maximize
        stage.setOpacity(0);
        stage.show();
        
        stage.setTitle("Maze");
        root.setStyle("-fx-background-color: black");
        
        CanvasOrthogonal canvas = new CanvasOrthogonal(scene.getWidth(), scene.getHeight(), maze, cellWallRatio);
        
        root.getChildren().add(canvas);
        
        AnimationTimer pathTimer = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                canvas.drawMaze(pathfinder.getChangeList());
                if (!pathfinder.step())
                {
                    canvas.drawPath(pathfinder.getPath());
                    stop();
                }
                canvas.drawPath(pathfinder.getPath());
            }
        };
        
        AnimationTimer mazeTimer = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                canvas.drawMaze(maze.getChangeList());
                if (!maze.step())
                {
                    if (doSolve)
                    {
                        if (doShowPathfinding)
                        {
                            pathTimer.start();
                        }
                        else
                        {
                            pathfinder.instantSolve();
                            canvas.drawPath(pathfinder.getPath());
                        }
                    }
                    stop();
                }
            }
        };
        
        if (doShowMazeGen)
        {
            mazeTimer.start();
        }
        else
        {
            maze.instantSolve();
            canvas.drawMaze();
            if (doSolve)
            {
                if (doShowPathfinding)
                {
                    pathTimer.start();
                }
                else
                {
                    pathfinder.instantSolve();
                    canvas.drawMaze();
                    canvas.drawPath(pathfinder.getPath());
                }
            }
        }
        
        stage.setOpacity(1);
        stage.setResizable(false); // Must come after stage.show() to work
        
        stage.setOnCloseRequest(windowEvent ->
        {
            mazeTimer.stop();
            pathTimer.stop();
        });
    }
}
