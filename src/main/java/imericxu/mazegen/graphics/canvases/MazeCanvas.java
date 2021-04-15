package imericxu.mazegen.graphics.canvases;

import imericxu.mazegen.logic.Algorithm;
import imericxu.mazegen.logic.Node;
import imericxu.mazegen.logic.State;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public abstract class MazeCanvas extends Canvas {
	protected final GraphicsContext gc = getGraphicsContext2D();

	public abstract void drawUpdates(Algorithm algorithm);

	public abstract void drawPath(List<Integer> path);

	public abstract void drawMaze(Node[] nodes);

	public void drawBlank() {
		gc.setFill(getColor(State.EMPTY));
		gc.fillRect(0, 0, getWidth(), getHeight());
	}

	public Color getColor(State state) {
		return switch (state) {
			case EMPTY -> OrthogonalCanvas.Colors.EMPTY.color;
			case PARTIAL -> OrthogonalCanvas.Colors.PARTIAL.color;
			case SOLID -> OrthogonalCanvas.Colors.SOLID.color;
		};
	}

	public enum Colors {
		EMPTY(Color.web("0x1C5188")),
		PARTIAL(Color.web("0xADD9FF")),
		SOLID(Color.WHITE),
		PATH(Color.web("0xAD360B"));

		public Color color;

		Colors(Color color) {
			this.color = color;
		}
	}
}
