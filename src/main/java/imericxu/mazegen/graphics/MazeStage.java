package imericxu.mazegen.graphics;

import imericxu.mazegen.MazeScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MazeStage extends Stage {
	public final Canvas canvas;

	public MazeStage() throws IOException {
		final var loader = new FXMLLoader(getClass().getResource("/fxml/maze_scene.fxml"));
		final Pane root = loader.load();

		final MazeScene controller = loader.getController();
		canvas = controller.canvas;

		setTitle("Maze Generator");
		setScene(new Scene(root));
		setMinWidth(500);
		setMinHeight(500);
	}
}
