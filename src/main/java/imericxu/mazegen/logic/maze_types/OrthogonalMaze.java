package imericxu.mazegen.logic.maze_types;

import imericxu.mazegen.user_input.OrthoMazeOptions;
import imericxu.mazegen.graphics.canvases.MazeCanvas;
import imericxu.mazegen.logic.Node;
import imericxu.mazegen.graphics.canvases.OrthogonalCanvas;
import imericxu.mazegen.logic.solve_algos.AStar;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class OrthogonalMaze extends Maze {
	private final int rows;
	private final int cols;

	public OrthogonalMaze(OrthoMazeOptions options) {
		super(options);
		rows = options.rows;
		cols = options.cols;
	}

	@NotNull
	public static Node[] generateNodes(int rows, int cols) {
		assert rows > 0 && cols > 0;
		Node[] nodes = new Node[rows * cols];

		IntStream.range(0, nodes.length).parallel().forEach(i -> {
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
}
