package imericxu.mazegen.controller;

import imericxu.mazegen.core.maze_algorithm.MazeType;
import imericxu.mazegen.core.solve_algorithm.SolveType;
import imericxu.mazegen.graphics.Maze;
import imericxu.mazegen.user_input.MazeOptions;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;

public class MazeController {
	@FXML
	public VBox root;
	@FXML
	public HBox menu;
	@FXML
	public Pane canvasWrapper;
	@FXML
	public Canvas canvas;
	@FXML
	public TextField inputRows;
	@FXML
	public TextField inputCols;
	@FXML
	public TextField inputRatio;
	@FXML
	public ComboBox<MazeType> comboMazeAlgorithm;
	@FXML
	public ComboBox<SolveType> comboSolveAlgorithm;
	@FXML
	public ToggleSwitch switchAnimateMaze;
	@FXML
	public ToggleSwitch switchDoSolve;
	@FXML
	public ToggleSwitch switchAnimateSolve;

	@FXML
	public void initialize() {
		MainController.restrictInputs(inputRows, inputCols, inputRatio);

		comboMazeAlgorithm.getItems().addAll(MazeType.values());
		comboMazeAlgorithm.getSelectionModel().select(MazeType.RANDOM);

		comboSolveAlgorithm.getItems().addAll(SolveType.values());
		comboSolveAlgorithm.getSelectionModel().select(SolveType.RANDOM);

		MainController.removeFocusOnEscape(root, menu.getChildren().toArray(Node[]::new));
	}

	public void injectValues(MazeOptions mazeOptions) {
		inputRows.setText(String.valueOf(mazeOptions.getRows()));
		inputCols.setText(String.valueOf(mazeOptions.getCols()));
		inputRatio.setText(String.valueOf(mazeOptions.getCellWallRatio()));
	}

	@FXML
	public void generate() throws IOException {
		final MazeOptions options = parseInput();
		final Maze maze = new Maze(options, canvas);
		// TODO Stop timers before generating
		maze.generate();
	}

	private MazeOptions parseInput() {
		final int rows = MainController.parseOrDefault(inputRows.getText(), 20, Integer::parseUnsignedInt);
		final int cols = MainController.parseOrDefault(inputCols.getText(), 20, Integer::parseUnsignedInt);
		final float ratio = MainController.parseOrDefault(inputRatio.getText(), 3.0f, Float::parseFloat);
		final MazeType mazeType = MainController.getMazeType(comboMazeAlgorithm);
		final SolveType solveType = MainController.getSolveType(comboSolveAlgorithm);
		final boolean doAnimateMaze = switchAnimateMaze.isSelected();
		final boolean doSolve = switchDoSolve.isSelected();
		final boolean doAnimateSolve = switchAnimateSolve.isSelected();
		return new MazeOptions(
				rows, cols, ratio,
				mazeType, solveType,
				doAnimateMaze, doSolve, doAnimateSolve
		);
	}
}
