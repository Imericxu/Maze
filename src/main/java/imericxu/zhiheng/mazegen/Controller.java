package imericxu.zhiheng.mazegen;

import imericxu.zhiheng.mazegen.maze.GameCanvas;
import imericxu.zhiheng.mazegen.maze.MazeSquare;
import imericxu.zhiheng.mazegen.maze.maze_algos.Backtracker;
import imericxu.zhiheng.mazegen.maze.maze_algos.Prims;
import imericxu.zhiheng.mazegen.maze.maze_algos.Wilson;
import imericxu.zhiheng.mazegen.maze.timers.TimerMaze;
import imericxu.zhiheng.mazegen.maze.timers.TimerPath;
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
	private ComboBox<MazeType> comboMazeAlgo;
	@FXML
	private ToggleSwitch switchShowMazeGen;
	@FXML
	private ToggleSwitch switchDoSolve;
	@FXML
	private ComboBox<SolveType> comboSolveAlgo;
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
		comboMazeAlgo.getItems().addAll(MazeType.values());
		comboSolveAlgo.getItems().addAll(SolveType.values());
	}
	
	/**
	 * Attempts to launch the maze after pressing start button
	 */
	@FXML
	public void startPressed()
	{
		int rows, cols;
		
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
		
		double cellWallRatio;
		
		try
		{
			cellWallRatio = Double.parseDouble(inputRatio.getText());
		}
		catch (NumberFormatException e)
		{
			cellWallRatio = 2;
		}
		
		MazeType mazeType = comboMazeAlgo.getSelectionModel().getSelectedItem();
		if (mazeType == null)
			mazeType = randomEnum(MazeType.class);
		
		SolveType pathType = comboSolveAlgo.getSelectionModel().getSelectedItem();
		if (pathType == null)
			pathType = randomEnum(SolveType.class);
		
		final boolean doAnimateMaze = switchShowMazeGen.isSelected();
		final boolean doSolve = switchDoSolve.isSelected();
		final boolean doAnimateSolve = switchShowPathfinding.isSelected();
		
		SquareMazeOptions options = new SquareMazeOptions(mazeType, pathType,
		                                                  rows, cols, cellWallRatio,
		                                                  doAnimateMaze,
		                                                  doSolve, doAnimateSolve);
		
		launchMaze(options);
	}
	
	private <T extends Enum<?>> T randomEnum(Class<T> clazz)
	{
		int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
	
	private void launchMaze(SquareMazeOptions options)
	{
		MazeGeneratorStage stage = new MazeGeneratorStage(options);
		
		final var nodes = MazeSquare.generate(options.rows, options.cols);
		final var mazeAlgorithm = switch (options.mazeType)
				{
					case PRIM -> new Prims(nodes);
					case RECURSIVE -> new Backtracker(nodes);
					case WILSON -> new Wilson(nodes);
				};
		
		final var timerPath = new TimerPath(options, mazeAlgorithm, stage.canvas);
		final var timerMaze = new TimerMaze(timerPath, stage.canvas, mazeAlgorithm, options.doSolve);
		
		stage.setOpacity(1);
		stage.setResizable(false); // Must come after stage.show() to work
		
		timerMaze.start();
		
		stage.setOnCloseRequest(windowEvent -> {
			timerPath.stop();
			timerMaze.stop();
		});
	}
	
	public enum MazeType
	{
		PRIM("Prim's Algorithm"),
		RECURSIVE("Recursive Backtracker"),
		WILSON("Wilson's Algorithm");
		
		private final String label;
		
		MazeType(String label)
		{
			this.label = label;
		}
		
		@Override
		public String toString()
		{
			return label;
		}
	}
	
	public enum SolveType
	{
		TREMAUX("Trémaux"),
		ASTAR("A*"),
		BREADTH("Breadth First Search");
		
		private final String label;
		
		SolveType(String label)
		{
			this.label = label;
		}
		
		@Override
		public String toString()
		{
			return label;
		}
	}
	
	private static class MazeGeneratorStage extends Stage
	{
		public final GameCanvas canvas;
		
		public MazeGeneratorStage(SquareMazeOptions squareMazeOptions)
		{
			StackPane root = new StackPane();
			Scene scene = new Scene(root);
			setScene(scene);
			// Need to show stage to actually maximize
			setOpacity(0);
			show();
			setMaximized(true);
			
			setTitle("Maze Generator");
			root.setStyle("-fx-background-color: black");
			
			canvas = new GameCanvas(scene.getWidth(), scene.getHeight(),
			                        squareMazeOptions.rows, squareMazeOptions.cols,
			                        squareMazeOptions.cellWallRatio);
			root.getChildren().add(canvas);
		}
	}
}
