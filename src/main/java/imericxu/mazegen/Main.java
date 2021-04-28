package imericxu.mazegen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/main.fxml")));
		Scene scene = new Scene(root);
		primaryStage.setTitle("MazeSquare Generator");
		primaryStage.setScene(scene);
		root.requestFocus();
		primaryStage.show();
	}
}
