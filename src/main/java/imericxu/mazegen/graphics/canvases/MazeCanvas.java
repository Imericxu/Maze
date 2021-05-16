package imericxu.mazegen.graphics.canvases;

import imericxu.mazegen.core.Algorithm;
import imericxu.mazegen.core.Node;
import imericxu.mazegen.core.State;
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
	 * Instead of drawing from a changelist, this method draws every single node.
	 */
	public abstract void drawMaze(Node[] nodes, State[] states);

	/**
	 * Colors the start and end nodes.
	 *
	 * @apiNote Make sure to call in the correct order!
	 */
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

	/**
	 * Colors a cell given an implementation-dependent position
	 */
	protected abstract void drawCell(Pos pos, Color color);

	/**
	 * @implSpec {@code pos} can be anything (e.g., top left corner, center, etc.).<br>
	 * Define numbers for each side/wall.
	 */
	protected abstract void drawWall(Pos pos, int side, Color color);

	/**
	 * @return an implementation-dependent position used for {@link #drawCell} and {@link #drawWall}
	 */
	protected abstract Pos calcMazePos(int id);

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

	public static class Pos {
		public final double x, y;

		public Pos(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
}
