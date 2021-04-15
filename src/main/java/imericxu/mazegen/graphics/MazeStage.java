package imericxu.mazegen.graphics;

import imericxu.mazegen.graphics.canvases.MazeCanvas;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MazeStage extends Stage {
	private final StackPane root;
	private final Scene scene;
	private MazeCanvas canvas;

	public MazeStage() {
		setTitle("Maze Generator");
		root = new StackPane();
		root.setStyle("-fx-background-color: black");
		scene = new Scene(root);
		setScene(scene);

		setOpacity(0);
		show();
		setMaximized(true);
		hide();
		setOpacity(1);
	}

	public double getSceneWidth() {
		return scene.getWidth();
	}

	public double getSceneHeight() {
		return scene.getHeight();
	}

	public MazeCanvas getCanvas() {
		return canvas;
	}

	public void setCanvas(MazeCanvas canvas) {
		this.canvas = canvas;
		root.getChildren().clear();
		root.getChildren().add(canvas);
	}
}
