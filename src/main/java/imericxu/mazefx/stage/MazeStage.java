package imericxu.mazefx.stage;

import imericxu.mazefx.controller.MazeController;
import imericxu.mazefx.user_input.MazeOptions;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MazeStage extends Stage {
	public final MazeController controller;

	public MazeStage(MazeOptions mazeOptions) throws IOException {
		final var loader = new FXMLLoader(getClass().getResource("/fxml/maze.fxml"));
		final Pane root = loader.load();

		controller = loader.getController();
		controller.injectValues(mazeOptions);

		setTitle("Maze Generator");
		setScene(new Scene(root));
		setMinWidth(500);
		setMinHeight(500);
		root.requestFocus();
	}
}
