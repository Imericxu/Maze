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

	/**
	 * Draws everything currently in the changelist of an algorithm
	 */
	public abstract void drawUpdates(Algorithm algorithm);

	/**
	 * Draws a path connecting the respective nodes in the list
	 *
	 * @param path a list of connected node ids
	 */
	public abstract void drawPath(List<Integer> path);

	/**
	 * Draws a complete maze based on connections of each node
	 */
	public abstract void drawMaze(Node[] nodes);

	public abstract void drawStartAndEnd(int startId, int endId);

	/**
	 * Fills the canvas with the set color of {@link State State}{@code .EMPTY}
	 */
	public void drawBlank() {
		gc.setFill(getColor(State.EMPTY));
		gc.fillRect(0, 0, getWidth(), getHeight());
	}

	public Color getColor(State state) {
		return switch (state) {
			case EMPTY -> Colors.EMPTY.color;
			case PARTIAL -> Colors.PARTIAL.color;
			case SOLID -> Colors.SOLID.color;
		};
	}

	public enum Colors {
		EMPTY(Color.web("0x1C5188")),
		PARTIAL(Color.web("0xADD9FF")),
		SOLID(Color.WHITE),
		PATH(Color.web("0xAD360B")),
		START(Color.web("#06D6A0")),
		END(Color.web("#AF2BBF"));

		public final Color color;

		Colors(Color color) {
			this.color = color;
		}
	}
}
