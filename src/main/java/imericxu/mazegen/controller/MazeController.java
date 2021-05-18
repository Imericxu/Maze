package imericxu.mazegen.controller;

import javafx.event.ActionEvent;
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

	public void generate(ActionEvent actionEvent) {
		// TODO
	}
}
