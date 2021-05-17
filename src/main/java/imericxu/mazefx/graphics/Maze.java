package imericxu.mazefx.graphics;

import imericxu.mazefx.core.Node;
import imericxu.mazefx.core.maze_algorithm.MazeAlgorithm;
import imericxu.mazefx.core.maze_algorithm.MazeType;
import imericxu.mazefx.core.maze_algorithm.algorithms.Backtracking;
import imericxu.mazefx.core.maze_algorithm.algorithms.Kruskals;
import imericxu.mazefx.core.maze_algorithm.algorithms.Prims;
import imericxu.mazefx.core.maze_algorithm.algorithms.Wilsons;
import imericxu.mazefx.core.solve_algorithm.SolveAlgorithm;
import imericxu.mazefx.core.solve_algorithm.SolveType;
import imericxu.mazefx.core.solve_algorithm.algorithms.AStar;
import imericxu.mazefx.core.solve_algorithm.algorithms.Breadth;
import imericxu.mazefx.core.solve_algorithm.algorithms.Tremaux;
import imericxu.mazefx.graphics.timers.TimerMaze;
import imericxu.mazefx.graphics.timers.TimerSolve;
import imericxu.mazefx.user_input.MazeOptions;
import javafx.scene.canvas.Canvas;
import kotlin.Pair;
import kotlin.jvm.functions.Function2;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * A rectangular/square maze
 */
public class Maze {
	private static final Random random = new Random();
	private final MazeOptions options;
	private final Function2<Integer, Integer, Double> aStarHeuristic;
	private final MazeDrawer mazeDrawer;

	public Maze(MazeOptions options, Canvas canvas) throws IOException {
		this.options = options;
		this.aStarHeuristic = getAStarHeuristic();
		mazeDrawer = new MazeDrawer(canvas, options.getRows(), options.getCols(), options.getCellWallRatio());
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
		mazeDrawer.drawBlank();

		final var mazeAlgo = makeMazeAlgorithm(options.getMazeType());

		if (options.getDoAnimateMaze()) {
			TimerMaze timerMaze = new TimerMaze(this::solve, mazeDrawer, mazeAlgo);
			timerMaze.start();
		} else {
			mazeAlgo.finishImmediately();
			mazeDrawer.drawMaze(mazeAlgo.getNodes(), mazeAlgo.getStates());
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
			TimerSolve timerSolve = new TimerSolve(mazeDrawer, solveAlgo);
			timerSolve.start();
		} else {
			solveAlgo.finishImmediately();
			mazeDrawer.drawMaze(solveAlgo.getNodes(), solveAlgo.getStates());
			mazeDrawer.drawStartAndEnd(solveAlgo.getStartId(), solveAlgo.getEndId());
			mazeDrawer.drawPath(solveAlgo.getPath());
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

		if (type == MazeType.RANDOM) {
			final List<MazeType> values = new ArrayList<>(Arrays.asList(MazeType.values()));
			values.remove(MazeType.RANDOM);
			type = values.get(random.nextInt(values.size()));
		}

		return switch (type) {
			case PRIM -> new Prims(nodes);
			case BACKTRACKING -> new Backtracking(nodes);
			case WILSON -> new Wilsons(nodes);
			case KRUSKAL -> new Kruskals(nodes);
			default -> throw new IllegalStateException("Unexpected value: " + type);
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

		if (type == SolveType.RANDOM) {
			final List<SolveType> values = new ArrayList<>(Arrays.asList(SolveType.values()));
			values.remove(SolveType.RANDOM);
			type = values.get(random.nextInt(values.size()));
		}

		return switch (type) {
			case TREMAUX -> new Tremaux(nodes, start, end);
			case ASTAR -> new AStar(nodes, start, end, aStarHeuristic);
			case BREADTH -> new Breadth(nodes, start, end);
			default -> throw new IllegalStateException("Unexpected value: " + type);
		};
	}

	public interface MazeListener {
		/**
		 * @param nodes finished maze
		 */
		void onFinishMazeGeneration(Node[] nodes);
	}
}
