package imericxu.mazegen.logic.maze_shapes;

import imericxu.mazegen.graphics.canvases.MazeCanvas;
import imericxu.mazegen.graphics.canvases.OrthogonalCanvas;
import imericxu.mazegen.logic.Node;
import imericxu.mazegen.logic.solve_algos.AStar;
import imericxu.mazegen.user_input.OrthoMazeOptions;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * A rectangular/square maze
 */
public class OrthogonalMaze extends Maze {
	private final int rows;
	private final int cols;

	public OrthogonalMaze(OrthoMazeOptions options) {
		super(options);
		rows = options.rows;
		cols = options.cols;
	}

	public static Node[] generateNodes(int rows, int cols) {
		assert rows > 0 && cols > 0;
		Node[] nodes = new Node[rows * cols];

		IntStream.range(0, nodes.length).forEach(i -> {
			final Set<Integer> neighbors = new HashSet<>();

			final int row = i / cols;
			final int col = i % cols;

			if (row > 0) neighbors.add(i - cols);

			if (col < cols - 1) neighbors.add(i + 1);

			if (row < rows - 1) neighbors.add(i + cols);

			if (col > 0) neighbors.add(i - 1);

			nodes[i] = new Node(i, neighbors);
		});

		return nodes;
	}

	@Override
	protected MazeCanvas makeCanvas(double maxWidth, double maxHeight) {
		return new OrthogonalCanvas(maxWidth, maxHeight, rows, cols, cellWallRatio);
	}

	@Override
	protected AStar.Heuristic getAStarHeuristic() {
		return (id1, id2) -> {
			final int startRow = id1 / cols;
			final int startCol = id1 % cols;
			final int endRow = id2 / cols;
			final int endCol = id2 % cols;
			return Math.hypot(endCol - startCol, endRow - startRow);
		};
	}

	@Override
	protected Node[] generateNodes() {
		return generateNodes(rows, cols);
	}

	@Override
	protected Pair<Integer, Integer> randomStartEnd() {
		final boolean isHorizontal = rand.nextBoolean();
		int id1, id2;
		if (isHorizontal) {
			id1 = rand.nextInt(rows) * cols;
			id2 = (rand.nextInt(rows) + 1) * cols - 1;
		} else {
			id1 = rand.nextInt(cols);
			id2 = (rows - 1) * cols + rand.nextInt(cols);
		}
		// Randomize start and end
		return rand.nextBoolean() ? new Pair<>(id1, id2) : new Pair<>(id2, id1);
	}
}
