package imericxu.mazegen.stage;

import imericxu.mazegen.controller.MazeController;
import imericxu.mazegen.user_input.MazeOptions;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MazeStage extends Stage {
	public final Canvas canvas;

	public MazeStage(MazeOptions mazeOptions) throws IOException {
		final var loader = new FXMLLoader(getClass().getResource("/fxml/maze_scene.fxml"));
		final Pane root = loader.load();

		final MazeController controller = loader.getController();
		controller.injectValues(mazeOptions);
		canvas = controller.canvas;

		setTitle("Maze Generator");
		setScene(new Scene(root));
		setMinWidth(500);
		setMinHeight(500);
		root.requestFocus();
	}
}
