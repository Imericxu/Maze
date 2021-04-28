package imericxu.mazegen.graphics.canvases;

import imericxu.mazegen.logic.Algorithm;
import imericxu.mazegen.logic.Node;
import imericxu.mazegen.logic.State;
import imericxu.mazegen.logic.maze_types.OrthogonalMaze;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * {@link javafx.scene.canvas.Canvas} specifically designed to display {@link OrthogonalMaze orthogonal} mazes
 */
public class OrthogonalCanvas extends MazeCanvas {
	private final int rows;
	private final int cols;
	private final double cellSize;
	private final double wallSize;

	public OrthogonalCanvas(double maxWidth, double maxHeight,
	                        int rows, int cols, double cellWallRatio) {
		this.rows = rows;
		this.cols = cols;

		double screenRatio = maxWidth / maxHeight;
		double gridRatio = (double) cols / rows;
		if (screenRatio > gridRatio) {
			setHeight(maxHeight);
			wallSize = maxHeight / (rows * (cellWallRatio + 1) + 1);
			cellSize = wallSize * cellWallRatio;
			setWidth(cols * (cellSize + wallSize) + wallSize);
		} else {
			setWidth(maxWidth);
			wallSize = maxWidth / (cols * (cellWallRatio + 1) + 1);
			cellSize = wallSize * cellWallRatio;
			setHeight(rows * (cellSize + wallSize) + wallSize);
		}

		drawBlank();
	}

	/**
	 * Draws sections of the maze as requests come in
	 */
	@Override
	public void drawUpdates(Algorithm algorithm) {
		algorithm.changeList.forEach(id -> {
			final Pos topLeft = calcMazePos(id);

			if (algorithm.getState(id) == State.EMPTY) {
				gc.setFill(getColor(State.EMPTY));
				gc.fillRect(topLeft.x - wallSize, topLeft.y - wallSize,
				            cellSize + 2 * wallSize, cellSize + 2 * wallSize);
				return;
			}

			final Color cellColor = getColor(algorithm.getState(id));
			drawCell(topLeft, cellColor);

			for (final int connectionId : algorithm.getConnectionsOf(id)) {
				final Color color = algorithm.getState(connectionId) == State.SOLID
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
			}
		});
	}

	@Override
	public void drawMaze(Node[] nodes) {
		gc.setFill(getColor(State.SOLID));
		for (int row = 0; row < rows; ++row) {
			for (int col = 0; col < cols; ++col) {
				final Node node = nodes[row * cols + col];
				final Pos pos = calcMazePos(node.id);

				drawCell(pos, Colors.SOLID.color);

				final var connections = node.getConnections();
				// Draw right wall if connected
				if (col < cols - 1 && connections.contains(node.id + 1)) {
					drawWall(pos, 2, Colors.SOLID.color);
				}
				// Draw bottom wall if connected
				if (row < rows - 1 && connections.contains(node.id + cols)) {
					drawWall(pos, 3, Colors.SOLID.color);
				}
			}
		}
	}

	@Override
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

	@Override
	public void drawStartAndEnd(int startId, int endId) {
		drawCell(calcMazePos(startId), Colors.START.color);
		drawCell(calcMazePos(endId), Colors.END.color);
	}

	@Override
	protected void drawCell(Pos topLeft, Color color) {
		gc.setFill(color);
		gc.fillRect(topLeft.x, topLeft.y, cellSize, cellSize);
	}

	@Override
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
	@Override
	protected Pos calcMazePos(int id) {
		final int row = id / cols;
		final int col = id % cols;
		final double x = (wallSize + cellSize) * col + wallSize;
		final double y = (wallSize + cellSize) * row + wallSize;
		return new Pos(x, y);
	}
}
