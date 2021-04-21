package imericxu.mazegen.graphics.canvases;

import imericxu.mazegen.logic.Algorithm;
import imericxu.mazegen.logic.Node;
import imericxu.mazegen.logic.State;
import imericxu.mazegen.logic.maze_types.OrthogonalMaze;

import java.util.List;
import java.util.stream.IntStream;

/**
 * {@link javafx.scene.canvas.Canvas} specifically designed to display {@link OrthogonalMaze orthogonal} mazes
 */
public class OrthogonalCanvas extends MazeCanvas {
	private final NodeMeasurements[] nodeMeasurements;
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

		nodeMeasurements = new NodeMeasurements[rows * cols];
		IntStream.range(0, nodeMeasurements.length).parallel().forEach(i -> {
			final int row = i / cols;
			final int col = i % cols;
			final double x = (wallSize + cellSize) * (double) col + wallSize;
			final double y = (wallSize + cellSize) * (double) row + wallSize;
			nodeMeasurements[i] = new NodeMeasurements(x, y);
		});

		// drawGrid();
		gc.setFill(Colors.EMPTY.color);
		gc.fillRect(0, 0, getWidth(), getHeight());
	}

	/**
	 * Draws sections of the maze as requests come in
	 *
	 * @param algorithm
	 */
	@Override
	public void drawUpdates(Algorithm algorithm) {
		algorithm.changeList.forEach(id -> {
			final NodeMeasurements nodeMeasurements = this.nodeMeasurements[id];

			if (algorithm.getState(id) == State.EMPTY) {
				gc.setFill(getColor(State.EMPTY));
				gc.fillRect(nodeMeasurements.x - wallSize, nodeMeasurements.y - wallSize,
				            cellSize + 2 * wallSize, cellSize + 2 * wallSize);
				return;
			}

			gc.setFill(getColor(algorithm.getState(id)));
			gc.fillRect(nodeMeasurements.x, nodeMeasurements.y, cellSize, cellSize);

			for (final int connectionId : algorithm.getConnectionsOf(id)) {
				if (connectionId == id - cols)
					fillRect(nodeMeasurements.top);
				else if (connectionId == id + 1)
					fillRect(nodeMeasurements.right);
				else if (connectionId == id + cols)
					fillRect(nodeMeasurements.bottom);
				else
					fillRect(nodeMeasurements.left);
			}
		});
	}

	@Override
	public void drawMaze(Node[] nodes) {
		gc.setFill(getColor(State.SOLID));
		for (int row = 0; row < rows; ++row) {
			for (int col = 0; col < cols; ++col) {
				final Node node = nodes[row * cols + col];
				final var measures = nodeMeasurements[node.id];
				gc.fillRect(measures.x, measures.y, cellSize, cellSize);

				final var connections = node.getConnections();
				if (col < cols - 1 && connections.contains(node.id + 1))
					fillRect(measures.right);
				if (row < rows - 1 && connections.contains(node.id + cols))
					fillRect(measures.bottom);
			}
		}
	}

	@Override
	public void drawPath(List<Integer> pathList) {
		if (pathList.isEmpty()) return;

		int first = pathList.get(0);
		int row = first / cols;
		int col = first % cols;
		double x1 = (wallSize + cellSize) * col + wallSize + cellSize / 2.0;
		double y1 = (wallSize + cellSize) * row + wallSize + cellSize / 2.0;
		double x2, y2;

		gc.setStroke(Colors.PATH.color);
		gc.setLineWidth(cellSize * 0.5);

		for (int id : pathList.subList(1, pathList.size())) {
			row = id / cols;
			col = id % cols;
			x2 = (wallSize + cellSize) * col + wallSize + cellSize / 2.0;
			y2 = (wallSize + cellSize) * row + wallSize + cellSize / 2.0;
			gc.strokeLine(x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		}
	}


    /* * * * * * * * * * * * * * * * * * * * *
    Helper Methods
    * * * * * * * * * * * * * * * * * * * * */

	private void fillRect(NodeMeasurements.Rect rect) {
		gc.fillRect(rect.x, rect.y, rect.width, rect.height);
	}

	private class NodeMeasurements {
		public final double x;
		public final double y;
		public final Rect top;
		public final Rect right;
		public final Rect bottom;
		public final Rect left;

		NodeMeasurements(double x, double y) {
			this.x = x;
			this.y = y;
			top = new Rect(x, y - wallSize, cellSize, wallSize);
			right = new Rect(x + cellSize, y, wallSize, cellSize);
			bottom = new Rect(x, y + cellSize, cellSize, wallSize);
			left = new Rect(x - wallSize, y, wallSize, cellSize);
		}

		class Rect {
			public final double x;
			public final double y;
			public final double width;
			public final double height;

			public Rect(double x, double y, double width, double height) {
				this.x = x;
				this.y = y;
				this.width = width;
				this.height = height;
			}
		}
	}
}
