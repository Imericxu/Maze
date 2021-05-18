package imericxu.mazegen.graphics;

import imericxu.mazegen.core.Node;
import imericxu.mazegen.core.maze_algorithm.MazeAlgorithm;
import imericxu.mazegen.core.maze_algorithm.MazeType;
import imericxu.mazegen.core.maze_algorithm.algorithm.Backtracking;
import imericxu.mazegen.core.maze_algorithm.algorithm.Kruskals;
import imericxu.mazegen.core.maze_algorithm.algorithm.Prims;
import imericxu.mazegen.core.maze_algorithm.algorithm.Wilsons;
import imericxu.mazegen.core.solve_algorithm.SolveAlgorithm;
import imericxu.mazegen.core.solve_algorithm.SolveType;
import imericxu.mazegen.core.solve_algorithm.algorithm.AStar;
import imericxu.mazegen.core.solve_algorithm.algorithm.Breadth;
import imericxu.mazegen.core.solve_algorithm.algorithm.Tremaux;
import imericxu.mazegen.graphics.timers.TimerMaze;
import imericxu.mazegen.graphics.timers.TimerSolve;
import imericxu.mazegen.user_input.MazeOptions;
import javafx.scene.canvas.Canvas;
import kotlin.Pair;
import kotlin.jvm.functions.Function2;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * A rectangular/square maze
 */
public class Maze {
	private static final Random random = new Random();
	private final MazeOptions options;
	private final Function2<Integer, Integer, Double> aStarHeuristic;
	private final MazeCanvas mazeCanvas;

	public Maze(MazeOptions options, Canvas canvas) throws IOException {
		this.options = options;
		this.aStarHeuristic = getAStarHeuristic();
		mazeCanvas = new MazeCanvas(canvas, options.getRows(), options.getCols(), options.getCellWallRatio());
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

	/**
	 * Launches a canvas to generate the maze.<br>
	 * Affected by a number of options.
	 *
	 * @see MazeOptions
	 */
	public void generate() {
		mazeCanvas.drawBlank();

		final var mazeAlgo = makeMazeAlgorithm(options.getMazeType());

		if (options.getDoAnimateMaze()) {
			TimerMaze timerMaze = new TimerMaze(this::solve, mazeCanvas, mazeAlgo);
			timerMaze.start();
		} else {
			mazeAlgo.finishImmediately();
			mazeCanvas.drawMaze(mazeAlgo.getNodes(), mazeAlgo.getStates());
			solve(mazeAlgo.getNodes());
		}
	}

	/**
	 * Solves the given maze. Whether it’s animated depends on the options.
	 *
	 * @param nodes a list of nodes representing a maze
	 */
	public void solve(Node[] nodes) {
		if (!options.getDoSolve()) return;

		final var solveAlgo = makeSolveAlgorithm(options.getSolveType(), nodes);

		if (options.getDoAnimateSolve()) {
			TimerSolve timerSolve = new TimerSolve(mazeCanvas, solveAlgo);
			timerSolve.start();
		} else {
			solveAlgo.finishImmediately();
			mazeCanvas.drawMaze(solveAlgo.getNodes(), solveAlgo.getStates());
			mazeCanvas.drawStartAndEnd(solveAlgo.getStartId(), solveAlgo.getEndId());
			mazeCanvas.drawPath(solveAlgo.getPath());
		}
	}

	private Function2<Integer, Integer, Double> getAStarHeuristic() {
		final int cols = options.getCols();

		return (id1, id2) -> {
			final int startRow = id1 / cols;
			final int startCol = id1 % cols;
			final int endRow = id2 / cols;
			final int endCol = id2 % cols;
			return (double) (Math.abs(endCol - startCol) + Math.abs(endRow - startRow));
		};
	}

	private Pair<Integer, Integer> randomStartEnd() {
		final int rows = options.getRows();
		final int cols = options.getCols();
		final boolean isHorizontal = random.nextBoolean();

		int id1, id2;
		if (isHorizontal) {
			id1 = random.nextInt(rows) * cols;
			id2 = (random.nextInt(rows) + 1) * cols - 1;
		} else {
			id1 = random.nextInt(cols);
			id2 = (rows - 1) * cols + random.nextInt(cols);
		}
		// Randomize start and end
		return random.nextBoolean() ? new Pair<>(id1, id2) : new Pair<>(id2, id1);
	}

	/**
	 * @return a runnable maze algorithm based on the type enum
	 */
	private MazeAlgorithm makeMazeAlgorithm(MazeType type) {
		final var nodes = generateNodes(options.getRows(), options.getCols());
		return switch (type) {
			case PRIM -> new Prims(nodes);
			case RECURSIVE -> new Backtracking(nodes);
			case WILSON -> new Wilsons(nodes);
			case KRUSKAL -> new Kruskals(nodes);
			default -> throw new IllegalArgumentException();
		};
	}

	/**
	 * @param nodes the maze to be solved
	 * @return a runnable solve algorithm based on the type enum
	 */
	private SolveAlgorithm makeSolveAlgorithm(SolveType type, Node[] nodes) {
		final var startEnd = randomStartEnd();
		final int start = startEnd.getFirst();
		final int end = startEnd.getSecond();

		return switch (type) {
			case TREMAUX -> new Tremaux(nodes, start, end);
			case ASTAR -> new AStar(nodes, start, end, aStarHeuristic);
			case BREADTH -> new Breadth(nodes, start, end);
			default -> throw new IllegalArgumentException();
		};
	}

	public interface MazeListener {
		/**
		 * @param nodes finished maze
		 */
		void onFinishMazeGeneration(Node[] nodes);
	}
}
