package imericxu.mazefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		final Pane root = FXMLLoader.load(Objects.requireNonNull(
				getClass().getResource("/fxml/main.fxml")
		));

		primaryStage.setTitle("MazeSquare Generator");
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
		primaryStage.show();
		root.requestFocus();
	}
}
