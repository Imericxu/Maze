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
import java.util.function.Function;
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

	public static <T> T parseOrDefault(String string, T defaultValue, Function<String, T> parseFunction) {
		T value;
		try {
			value = parseFunction.apply(string);
		} catch (Exception e) {
			value = defaultValue;
		}
		return value;
	}

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
		comboMazeAlgo.getSelectionModel().select(MazeType.RANDOM);

		comboSolveAlgo.getItems().addAll(SolveType.values());
		comboSolveAlgo.getSelectionModel().select(SolveType.RANDOM);

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

	/**
	 * Puts focus on the root if the escape key is pressed when the given nodes are active
	 */
	private void removeFocusOnEscape(Node... nodes) {
		final EventHandler<KeyEvent> removeFocus = event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				root.requestFocus();
			}
		};

		Arrays.stream(nodes).forEach(node -> node.setOnKeyPressed(removeFocus));
	}

	private MazeOptions parseInput() {
		final int rows = parseOrDefault(inputRows.getText(), 20, Integer::parseInt);
		final int cols = parseOrDefault(inputCols.getText(), 20, Integer::parseInt);
		final float ratio = parseOrDefault(inputRatio.getText(), 3.0f, Float::parseFloat);
		final MazeType mazeType = getMazeType();
		final SolveType solveType = getSolveType();
		final boolean doAnimateMaze = switchAnimateMaze.isSelected();
		final boolean doSolve = switchDoSolve.isSelected();
		final boolean doAnimateSolve = switchAnimateSolve.isSelected();

		return new MazeOptions(
				rows,
				cols,
				ratio,
				mazeType,
				solveType,
				doAnimateMaze,
				doSolve,
				doAnimateSolve
		);
	}

	private SolveType getSolveType() {
		final SolveType solveType;
		SolveType selected = comboSolveAlgo.getSelectionModel().getSelectedItem();
		if (selected == SolveType.RANDOM) {
			final SolveType[] types = SolveType.values();
			selected = types[random.nextInt(types.length - 1) + 1];
		}
		solveType = selected;
		return solveType;
	}

	private MazeType getMazeType() {
		final MazeType mazeType;
		MazeType selected = comboMazeAlgo.getSelectionModel().getSelectedItem();
		if (selected == MazeType.RANDOM) {
			final MazeType[] types = MazeType.values();
			selected = types[random.nextInt(types.length - 1) + 1];
		}
		mazeType = selected;
		return mazeType;
	}
}
