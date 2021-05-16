package imericxu.mazegen.graphics;

import imericxu.mazegen.Controller;
import imericxu.mazegen.core.Node;
import imericxu.mazegen.core.maze_algorithms.*;
import imericxu.mazegen.core.solve_algorithms.AStar;
import imericxu.mazegen.core.solve_algorithms.Breadth;
import imericxu.mazegen.core.solve_algorithms.SolveAlgorithm;
import imericxu.mazegen.core.solve_algorithms.Tremaux;
import imericxu.mazegen.graphics.timers.TimerMaze;
import imericxu.mazegen.graphics.timers.TimerSolve;
import imericxu.mazegen.user_input.MazeOptions;
import javafx.util.Pair;
import kotlin.jvm.functions.Function2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * A rectangular/square maze
 */
public class Maze {
	protected final Random rand = new Random();
	private final int rows;
	private final int cols;
	private final MazeStage stage;
	private final Function2<Integer, Integer, Double> aStarHeuristic;
	private final MazeListener mazeListener;
	private final Controller.MazeType mazeType;
	private final Controller.SolveType solveType;
	private final boolean doAnimateMaze;
	private final boolean doSolve;
	private final boolean doAnimateSolve;
	protected double cellWallRatio;

	public Maze(MazeOptions options) {
		this.stage = new MazeStage();
		this.aStarHeuristic = getAStarHeuristic();
		this.mazeListener = this::solve;
		this.cellWallRatio = options.cellWallRatio;
		this.mazeType = options.mazeType;
		this.solveType = options.solveType;
		this.doAnimateMaze = options.doAnimateMaze;
		this.doSolve = options.doSolve;
		this.doAnimateSolve = options.doAnimateSolve;
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

	/**
	 * Launches a canvas to generate the maze.<br>
	 * Affected by a number of options.
	 *
	 * @see MazeOptions
	 */
	public void generate() {
		final var canvas = makeCanvas(stage.getSceneWidth(), stage.getSceneHeight());
		stage.setCanvas(canvas);
		canvas.drawBlank();
		stage.show();

		final var mazeAlgo = makeMazeAlgorithm(mazeType);

		if (doAnimateMaze) {
			TimerMaze timerMaze = new TimerMaze(mazeListener, canvas, mazeAlgo);
			timerMaze.start();
		} else {
			mazeAlgo.finishImmediately();
			canvas.drawMaze(mazeAlgo.getNodes(), mazeAlgo.getStates());
			solve(mazeAlgo.getNodes());
		}
	}

	/**
	 * Solves the given maze. Whether it’s animated depends on the options.
	 *
	 * @param nodes a list of nodes representing a maze
	 */
	public void solve(Node[] nodes) {
		if (!doSolve) return;

		final var solveAlgo = makeSolveAlgorithm(solveType, nodes);
		final var canvas = stage.getCanvas();

		if (doAnimateSolve) {
			TimerSolve timerSolve = new TimerSolve(canvas, solveAlgo);
			timerSolve.start();
		} else {
			solveAlgo.finishImmediately();
			canvas.drawMaze(solveAlgo.getNodes(), solveAlgo.getStates());
			canvas.drawStartAndEnd(solveAlgo.getStartId(), solveAlgo.getEndId());
			canvas.drawPath(solveAlgo.getPath());
		}
	}

	protected MazeCanvas makeCanvas(double maxWidth, double maxHeight) {
		return new MazeCanvas(maxWidth, maxHeight, rows, cols, cellWallRatio);
	}

	protected Function2<Integer, Integer, Double> getAStarHeuristic() {
		return (id1, id2) -> {
			final int startRow = id1 / cols;
			final int startCol = id1 % cols;
			final int endRow = id2 / cols;
			final int endCol = id2 % cols;
			return Math.hypot(endCol - startCol, endRow - startRow);
		};
	}

	protected Node[] generateNodes() {
		return generateNodes(rows, cols);
	}

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

	/**
	 * @return a runnable maze algorithm based on the type enum
	 */
	private MazeAlgorithm makeMazeAlgorithm(Controller.MazeType type) {
		final var nodes = generateNodes();
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
	private SolveAlgorithm makeSolveAlgorithm(Controller.SolveType type, Node[] nodes) {
		final var startEnd = randomStartEnd();
		final int start = startEnd.getKey();
		final int end = startEnd.getValue();

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
