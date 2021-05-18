package imericxu.mazefx.controller;

import imericxu.mazefx.core.maze_algorithm.MazeType;
import imericxu.mazefx.core.solve_algorithm.SolveType;
import imericxu.mazefx.stage.MazeStage;
import imericxu.mazefx.user_input.MazeOptions;
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
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class MainController {
	@FXML
	public VBox root;
	@FXML
	public TextField inputRows;
	@FXML
	public TextField inputCols;
	@FXML
	public TextField inputRatio;
	@FXML
	public ComboBox<MazeType> comboMazeAlgorithm;
	@FXML
	public ToggleSwitch switchAnimateMaze;
	@FXML
	public ToggleSwitch switchDoSolve;
	@FXML
	public ComboBox<SolveType> comboSolveAlgorithm;
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

	public static void restrictInputs(TextField inputRows, TextField inputCols, TextField inputRatio) {
		final UnaryOperator<TextFormatter.Change> integerFilter = change ->
				change.getControlNewText().matches("\\d*") ? change : null;

		final UnaryOperator<TextFormatter.Change> floatFilter = change ->
				change.getControlNewText().matches("\\d*\\.?\\d*") ? change : null;

		inputRows.setTextFormatter(new TextFormatter<>(integerFilter));
		inputCols.setTextFormatter(new TextFormatter<>(integerFilter));
		inputRatio.setTextFormatter(new TextFormatter<>(floatFilter));
	}

	/**
	 * Puts focus on the root if the escape key is pressed when the given nodes are active
	 */
	public static void removeFocusOnEscape(Node root, Node... nodes) {
		final EventHandler<KeyEvent> removeFocus = event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				root.requestFocus();
			}
		};

		Arrays.stream(nodes).forEach(node -> node.setOnKeyPressed(removeFocus));
	}

	public static MazeOptions parseInput(
			TextField inputRows,
			TextField inputCols,
			TextField inputRatio,
			ComboBox<MazeType> comboMazeAlgorithm,
			ComboBox<SolveType> comboSolveAlgorithm,
			ToggleSwitch switchAnimateMaze,
			ToggleSwitch switchDoSolve,
			ToggleSwitch switchAnimateSolve
	) {
		final int rows = parseOrDefault(inputRows.getText(), 20, Integer::parseUnsignedInt);
		final int cols = parseOrDefault(inputCols.getText(), 20, Integer::parseUnsignedInt);
		final float ratio = parseOrDefault(inputRatio.getText(), 3.0f, Float::parseFloat);
		final MazeType mazeType = comboMazeAlgorithm.getValue();
		final SolveType solveType = comboSolveAlgorithm.getValue();
		final boolean doAnimateMaze = switchAnimateMaze.isSelected();
		final boolean doSolve = switchDoSolve.isSelected();
		final boolean doAnimateSolve = switchAnimateSolve.isSelected();

		return new MazeOptions(
				rows, cols, ratio,
				mazeType, solveType,
				doAnimateMaze, doSolve, doAnimateSolve
		);
	}

	@FXML
	public void initialize() {
		restrictInputs(inputRows, inputCols, inputRatio);

		comboMazeAlgorithm.getItems().addAll(MazeType.values());
		comboMazeAlgorithm.getSelectionModel().select(MazeType.RANDOM);

		comboSolveAlgorithm.getItems().addAll(SolveType.values());
		comboSolveAlgorithm.getSelectionModel().select(SolveType.RANDOM);

		removeFocusOnEscape(inputRows, inputCols, inputRatio, comboMazeAlgorithm, comboSolveAlgorithm);
	}

	/**
	 * Attempts to launch the maze after pressing start button
	 */
	@FXML
	public void launchMaze() throws IOException {
		final MazeOptions options = parseInput();
		final var stage = new MazeStage(options);
		stage.show();
		stage.controller.generate();
	}

	private MazeOptions parseInput() {
		return parseInput(
				inputRows,
				inputCols,
				inputRatio,
				comboMazeAlgorithm,
				comboSolveAlgorithm,
				switchAnimateMaze,
				switchDoSolve,
				switchAnimateSolve
		);
	}
}
