package imericxu.mazegen.controller;

import imericxu.mazegen.core.maze_algorithm.MazeType;
import imericxu.mazegen.core.solve_algorithm.SolveType;
import imericxu.mazegen.graphics.Maze;
import imericxu.mazegen.stage.MazeStage;
import imericxu.mazegen.user_input.MazeOptions;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.function.UnaryOperator;

public class MainController {
	private static final Random random = new Random();
	@FXML
	public VBox root;
	@FXML
	public TextField inputRows;
	@FXML
	public TextField inputCols;
	@FXML
	public TextField inputRatio;
	@FXML
	public ComboBox<MazeType> comboMazeAlgo;
	@FXML
	public ToggleSwitch switchAnimateMaze;
	@FXML
	public ToggleSwitch switchDoSolve;
	@FXML
	public ComboBox<SolveType> comboSolveAlgo;
	@FXML
	public ToggleSwitch switchAnimateSolve;

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

		removeFocusOnEscape(inputRows, inputCols, inputRatio, comboMazeAlgo, comboSolveAlgo);
	}

	/**
	 * Attempts to launch the maze after pressing start button
	 */
	@FXML
	public void launchMaze() throws IOException {
		final MazeOptions options = parseInput();
		final var stage = new MazeStage(options);
		stage.setOnShown(event -> {
			try {
				new Maze(options, stage.canvas).generate();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		stage.show();
	}

	private void removeFocusOnEscape(Node... nodes) {
		final EventHandler<KeyEvent> removeFocus = event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				root.requestFocus();
			}
		};

		Arrays.stream(nodes).forEach(node -> node.setOnKeyPressed(removeFocus));
	}

	private MazeOptions parseInput() {
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

		float cellWallRatio;
		try {
			cellWallRatio = Float.parseFloat(inputRatio.getText());
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

		return new MazeOptions(
				rows,
				cols,
				cellWallRatio,
				mazeType,
				solveType,
				doAnimateMaze,
				doSolve,
				doAnimateSolve
		);
	}
}
