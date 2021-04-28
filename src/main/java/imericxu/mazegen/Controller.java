package imericxu.mazegen;

import imericxu.mazegen.logic.maze_shapes.Maze;
import imericxu.mazegen.logic.maze_shapes.OrthogonalMaze;
import imericxu.mazegen.user_input.OrthoMazeOptions;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.controlsfx.control.ToggleSwitch;

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
	private ToggleSwitch switchAnimateMaze;
	@FXML
	private ToggleSwitch switchDoSolve;
	@FXML
	private ComboBox<SolveType> comboSolveAlgo;
	@FXML
	private ToggleSwitch switchAnimateSolve;

	@FXML
	public void initialize() {
		UnaryOperator<TextFormatter.Change> integerFilter = change ->
				change.getControlNewText().matches("\\d*") ? change : null;

		UnaryOperator<TextFormatter.Change> floatFilter = change ->
				change.getControlNewText().matches("\\d*\\.?\\d*") ? change : null;

		inputRows.setTextFormatter(new TextFormatter<>(integerFilter));
		inputCols.setTextFormatter(new TextFormatter<>(integerFilter));
		inputRatio.setTextFormatter(new TextFormatter<>(floatFilter));

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
			cellWallRatio = 3;
		}

		MazeType mazeType = comboMazeAlgo.getSelectionModel().getSelectedItem();
		if (mazeType == null || mazeType == MazeType.RANDOM) {
			final var types = MazeType.values();
			mazeType = types[random.nextInt(types.length - 1) + 1];
		}

		SolveType solveType = comboSolveAlgo.getSelectionModel().getSelectedItem();
		if (solveType == null || solveType == SolveType.RANDOM) {
			final var types = SolveType.values();
			solveType = types[random.nextInt(types.length - 1) + 1];
		}

		final boolean doAnimateMaze = switchAnimateMaze.isSelected();
		final boolean doSolve = switchDoSolve.isSelected();
		final boolean doAnimateSolve = switchAnimateSolve.isSelected();

		return new OrthoMazeOptions(mazeType, solveType,
		                            rows, cols, cellWallRatio,
		                            doAnimateMaze, doSolve, doAnimateSolve);
	}

	public enum MazeType {
		RANDOM("Random"),
		PRIM("Prim’s Algorithm"),
		RECURSIVE("Recursive Backtracking Algorithm"),
		WILSON("Wilson’s Algorithm"),
		KRUSKAL("Kruskal’s Algorithm");

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
		RANDOM("Random"),
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
