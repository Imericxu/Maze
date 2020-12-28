package imericxu.zhiheng.mazegen;

import imericxu.zhiheng.mazegen.maze.GameCanvas;
import imericxu.zhiheng.mazegen.maze.Maze;
import imericxu.zhiheng.mazegen.maze.maze_algos.*;
import imericxu.zhiheng.mazegen.maze.timers.TimerMaze;
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

import java.util.Random;
import java.util.function.UnaryOperator;

public class Controller
{
    private final Random random = new Random();
    @FXML
    private TextField inputRows;
    @FXML
    private TextField inputCols;
    @FXML
    private TextField inputRatio;
    @FXML
    private ComboBox<MazeAlgo> comboMazeAlgo;
    @FXML
    private ToggleSwitch switchShowMazeGen;
    @FXML
    private ToggleSwitch switchDoSolve;
    @FXML
    private ComboBox<SolveAlgo> comboSolveAlgo;
    @FXML
    private ToggleSwitch switchShowPathfinding;
    private int rows;
    private int cols;
    private double cellWallRatio;
    private boolean doShowMazeGen;
    private boolean doSolve;
    private boolean doShowPathfinding;
    
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
        comboMazeAlgo.getItems().addAll(MazeAlgo.values());
        comboSolveAlgo.getItems().addAll(SolveAlgo.values());
    }
    
    /**
     * Attempts to launch the maze after pressing start button
     */
    @FXML
    public void startPressed()
    {
        try
        {
            rows = Integer.parseInt(inputRows.getText());
        }
        catch (NumberFormatException e)
        {
            rows = 20;
        }
        
        try
        {
            cols = Integer.parseInt(inputCols.getText());
        }
        catch (NumberFormatException e)
        {
            cols = 20;
        }
        
        try
        {
            cellWallRatio = Double.parseDouble(inputRatio.getText());
        }
        catch (NumberFormatException e)
        {
            cellWallRatio = 2;
        }
        
        MazeAlgo mazeType = comboMazeAlgo.getSelectionModel().getSelectedItem();
        if (mazeType == null)
            mazeType = randomEnum(MazeAlgo.class);
        
        SolveAlgo pathType = comboSolveAlgo.getSelectionModel().getSelectedItem();
        if (pathType == null)
            pathType = randomEnum(SolveAlgo.class);
        
        doShowMazeGen = switchShowMazeGen.isSelected();
        doSolve = switchDoSolve.isSelected();
        doShowPathfinding = switchShowPathfinding.isSelected();
        
        final Node[] nodes = Maze.generate(rows, cols);
        MazeAlgorithm mazeAlgorithm = switch (mazeType)
                {
                    case PRIM -> new Prims(nodes);
                    case WILSON -> new Wilson(nodes);
                    case RECURSIVE -> new Backtracker(nodes);
                };
        
//        Pathfinder pathfinder = switch (pathType)
//                {
//                    case TREMAUX -> new Tremaux();
//                    case ASTAR -> new AStar();
//                    case BREADTH -> new BreadthFirstSearch();
//                };
        
        launchMaze(mazeAlgorithm/*, pathfinder*/);
    }
    
    private <T extends Enum<?>> T randomEnum(Class<T> clazz)
    {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
    
    private void launchMaze(MazeAlgorithm mazeAlgorithm/*, Pathfinder pathfinder*/)
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
        
        var gameCanvas = new GameCanvas(scene.getWidth(), scene.getHeight(), rows, cols, cellWallRatio);
        
        root.getChildren().add(gameCanvas);
        
//        var timerPath = new TimerPath(pathfinder, gameCanvas);
        var timerMaze = new TimerMaze(/*timerPath, */gameCanvas, mazeAlgorithm, /*pathfinder, */doSolve, doShowPathfinding);
        
        /*if (doShowMazeGen) timerMaze.start();
        else
        {
            mazeAlgorithm.instantSolve();
            mazeAlgorithm.getChangeList().clear();
            gameCanvas.drawMaze();
            
            if (doSolve)
            {
                TimerMaze.solveMaze(timerPath, gameCanvas, mazeAlgorithm, pathfinder, doShowPathfinding);
            }
        }*/
        
        timerMaze.start();
        
        stage.setOpacity(1);
        stage.setResizable(false); // Must come after stage.show() to work
        
        stage.setOnCloseRequest(windowEvent ->
        {
//            timerPath.stop();
            timerMaze.stop();
        });
    }
    
    private enum MazeAlgo
    {
        PRIM("Prim's Algorithm"),
        RECURSIVE("Recursive Backtracker"),
        WILSON("Wilson's Algorithm");
        
        private final String label;
        
        MazeAlgo(String label)
        {
            this.label = label;
        }
        
        @Override
        public String toString()
        {
            return label;
        }
    }
    
    private enum SolveAlgo
    {
        TREMAUX("Trémaux"),
        ASTAR("A*"),
        BREADTH("Breadth First Search");
        
        private final String label;
        
        SolveAlgo(String label)
        {
            this.label = label;
        }
        
        @Override
        public String toString()
        {
            return label;
        }
    }
}
