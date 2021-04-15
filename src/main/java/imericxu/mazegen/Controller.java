package imericxu.mazegen;

import imericxu.mazegen.logic.maze_types.OrthogonalMaze;
import imericxu.mazegen.user_input.OrthoMazeOptions;
import imericxu.mazegen.logic.maze_types.Maze;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.controlsfx.control.ToggleSwitch;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.UnaryOperator;

public class Controller {
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
	public void initialize() {
		UnaryOperator<TextFormatter.Change> integerFilter = change ->
		{
			String newText = change.getControlNewText();
			if (newText.matches("\\d*")) {
				return change;
			}
			return null;
		};

		UnaryOperator<TextFormatter.Change> floatFilter = change ->
		{
			String newText = change.getControlNewText();
			if (newText.matches("\\d*\\.?\\d*")) {
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
	public void launchOrthogonal() {
		Maze maze = new OrthogonalMaze(parseOrthogonalInput());
		maze.generate();
	}

	@NotNull
	private OrthoMazeOptions parseOrthogonalInput() {
		int rows;
		try {
			rows = Integer.parseInt(inputRows.getText());
		} catch (NumberFormatException e) {
			rows = 20;
		}

		int cols;
		try {
			cols = Integer.parseInt(inputCols.getText());
		} catch (NumberFormatException e) {
			cols = 20;
		}

		double cellWallRatio;
		try {
			cellWallRatio = Double.parseDouble(inputRatio.getText());
		} catch (NumberFormatException e) {
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

		return new OrthoMazeOptions(mazeType, pathType,
		                            rows, cols, cellWallRatio,
		                            doAnimateMaze, doSolve, doAnimateSolve);
	}

	private <T extends Enum<?>> T randomEnum(@NotNull Class<T> clazz) {
		int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}

	public enum MazeType {
		PRIM("Prim's Algorithm"),
		RECURSIVE("Recursive Backtracker"),
		WILSON("Wilson's Algorithm");

		private final String label;

		MazeType(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return label;
		}
	}

	public enum SolveType {
		TREMAUX("Trémaux"),
		ASTAR("A*"),
		BREADTH("Breadth First Search");

		private final String label;

		SolveType(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return label;
		}
	}
}
