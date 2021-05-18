package imericxu.mazefx.controller;

import imericxu.mazefx.core.maze_algorithm.MazeType;
import imericxu.mazefx.core.solve_algorithm.SolveType;
import imericxu.mazefx.graphics.Maze;
import imericxu.mazefx.user_input.MazeOptions;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.ToggleSwitch;

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
		comboMazeAlgorithm.getSelectionModel().select(mazeOptions.getMazeType());
		comboSolveAlgorithm.getSelectionModel().select(mazeOptions.getSolveType());
		switchAnimateMaze.setSelected(mazeOptions.getDoAnimateMaze());
		switchDoSolve.setSelected(mazeOptions.getDoSolve());
		switchAnimateSolve.setSelected(mazeOptions.getDoAnimateSolve());
	}

	@FXML
	public void generate() {
		final Maze maze = new Maze(parseInput(), canvas);
		// TODO Stop timers before generating
		maze.generate();
	}

	private MazeOptions parseInput() {
		return MainController.parseInput(
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
