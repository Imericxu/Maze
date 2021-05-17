package imericxu.mazegen.graphics;

import imericxu.mazegen.core.Algorithm;
import imericxu.mazegen.core.Node;
import imericxu.mazegen.core.State;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.Function;

/**
 * {@link javafx.scene.canvas.Canvas} specifically designed to display orthogonal mazes
 */
public class MazeDrawer {
	protected final GraphicsContext gc;
	private final Canvas canvas;
	private final int rows;
	private final int cols;
	private final double cellSize;
	private final double wallSize;

	public MazeDrawer(Canvas canvas, int rows, int cols, float cellWallRatio) {
		gc = canvas.getGraphicsContext2D();
		this.canvas = canvas;
		this.rows = rows;
		this.cols = cols;

		final double maxWidth = canvas.getWidth();
		final double maxHeight = canvas.getHeight();

		double screenRatio = maxWidth / maxHeight;
		double gridRatio = (double) cols / rows;
		if (screenRatio > gridRatio) {
//			setHeight(maxHeight);
			wallSize = maxHeight / (rows * (cellWallRatio + 1) + 1);
			cellSize = wallSize * cellWallRatio;
//			setWidth(cols * (cellSize + wallSize) + wallSize);
		} else {
//			setWidth(maxWidth);
			wallSize = maxWidth / (cols * (cellWallRatio + 1) + 1);
			cellSize = wallSize * cellWallRatio;
//			setHeight(rows * (cellSize + wallSize) + wallSize);
		}

		drawBlank();
	}

	/**
	 * Draws sections of the maze as requests come in
	 */
	public void drawUpdates(Algorithm algorithm) {
		final var nodes = algorithm.getNodes();
		final var states = algorithm.getStates();

		algorithm.getChangeList().forEach(id -> {
			final Pos topLeft = calcMazePos(id);

			if (states[id] == State.EMPTY) {
				gc.setFill(getColor(State.EMPTY));
				gc.fillRect(topLeft.x - wallSize, topLeft.y - wallSize,
				            cellSize + 2 * wallSize, cellSize + 2 * wallSize);
				return;
			}

			final Color cellColor = getColor(states[id]);
			drawCell(topLeft, cellColor);

			nodes[id].getConnections().forEach(connectionId -> {
				final Color color = states[connectionId] == State.SOLID
						? Colors.SOLID.color
						: cellColor;

				// Top
				if (connectionId == id - cols) {
					drawWall(topLeft, 1, color);
				}
				// Right
				else if (connectionId == id + 1) {
					drawWall(topLeft, 2, color);
				}
				// Bottom
				else if (connectionId == id + cols) {
					drawWall(topLeft, 3, color);
				}
				// Left
				else {
					drawWall(topLeft, 4, color);
				}
			});
		});
	}

	public void drawMaze(Node[] nodes, State[] states) {
		for (int row = 0, id = 0; row < rows; ++row) {
			for (int col = 0; col < cols; ++col, ++id) {
				final Node node = nodes[id];
				final Pos pos = calcMazePos(id);
				final Color cellColor = getColor(states[id]);

				drawCell(pos, cellColor);

				final var connections = node.getConnections();
				final Function<Integer, Color> connectionColor = nodeId ->
						states[nodeId] == State.SOLID ? Colors.SOLID.color : cellColor;
				// Draw right wall if connected
				if (col < cols - 1 && connections.contains(id + 1)) {
					drawWall(pos, 2, connectionColor.apply(id + 1));
				}
				// Draw bottom wall if connected
				if (row < rows - 1 && connections.contains(id + cols)) {
					drawWall(pos, 3, connectionColor.apply(id + cols));
				}
			}
		}
	}

	public void drawPath(List<Integer> pathList) {
		if (pathList.isEmpty()) return;

		int first = pathList.get(0);
		final Pos pos1 = calcMazePos(first);
		double x1 = pos1.x + cellSize / 2.0;
		double y1 = pos1.y + cellSize / 2.0;
		double x2, y2;

		gc.setStroke(Colors.PATH.color);
		gc.setLineWidth(cellSize * 0.5);

		for (int id : pathList.subList(1, pathList.size())) {
			final Pos pos2 = calcMazePos(id);
			x2 = pos2.x + cellSize / 2.0;
			y2 = pos2.y + cellSize / 2.0;
			gc.strokeLine(x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		}
	}

	public void drawStartAndEnd(int startId, int endId) {
		drawCell(calcMazePos(startId), Colors.START.color);
		drawCell(calcMazePos(endId), Colors.END.color);
	}

	/**
	 * Fills the canvas with the set color of {@link State State}{@code .EMPTY}
	 */
	public void drawBlank() {
		gc.setFill(getColor(State.EMPTY));
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	public Color getColor(State state) {
		return switch (state) {
			case EMPTY -> Colors.EMPTY.color;
			case PARTIAL -> Colors.PARTIAL.color;
			case SOLID -> Colors.SOLID.color;
		};
	}

	protected void drawCell(Pos topLeft, Color color) {
		gc.setFill(color);
		gc.fillRect(topLeft.x, topLeft.y, cellSize, cellSize);
	}

	protected void drawWall(Pos topLeft, int side, Color color) {
		gc.setFill(color);
		switch (side) {
			// Top
			case 1 -> gc.fillRect(topLeft.x, topLeft.y - wallSize, cellSize, wallSize);
			// Right
			case 2 -> gc.fillRect(topLeft.x + cellSize, topLeft.y, wallSize, cellSize);
			// Bottom
			case 3 -> gc.fillRect(topLeft.x, topLeft.y + cellSize, cellSize, wallSize);
			// Left
			case 4 -> gc.fillRect(topLeft.x - wallSize, topLeft.y, wallSize, cellSize);
			default -> throw new IllegalArgumentException("Side " + side + " not defined!");
		}
	}

	/**
	 * @return top left corner of a cell in the form of (x, y)
	 */
	protected Pos calcMazePos(int id) {
		final int row = id / cols;
		final int col = id % cols;
		final double x = (wallSize + cellSize) * col + wallSize;
		final double y = (wallSize + cellSize) * row + wallSize;
		return new Pos(x, y);
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

	public static class Pos {
		public final double x, y;

		public Pos(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
}
