package imericxu.mazegen.controller;

import imericxu.mazegen.user_input.MazeOptions;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class MazeController {
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

	public void injectValues(MazeOptions mazeOptions) {
		inputRows.setText(String.valueOf(mazeOptions.getRows()));
		inputCols.setText(String.valueOf(mazeOptions.getCols()));
		inputRatio.setText(String.valueOf(mazeOptions.getCellWallRatio()));
	}

	@FXML
	public void generate() {
		// TODO
	}
}
