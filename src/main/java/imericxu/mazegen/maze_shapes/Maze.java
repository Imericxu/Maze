package imericxu.mazegen.maze_shapes;

import imericxu.mazegen.Controller;
import imericxu.mazegen.core.Node;
import imericxu.mazegen.core.maze_algorithms.*;
import imericxu.mazegen.core.solve_algorithms.AStar;
import imericxu.mazegen.core.solve_algorithms.Breadth;
import imericxu.mazegen.core.solve_algorithms.SolveAlgorithm;
import imericxu.mazegen.core.solve_algorithms.Tremaux;
import imericxu.mazegen.graphics.MazeStage;
import imericxu.mazegen.graphics.canvases.MazeCanvas;
import imericxu.mazegen.graphics.timers.TimerMaze;
import imericxu.mazegen.graphics.timers.TimerSolve;
import imericxu.mazegen.user_input.MazeOptions;
import javafx.util.Pair;
import kotlin.jvm.functions.Function2;

import java.util.Random;

public abstract class Maze {
	protected final Random rand = new Random();
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
		stage = new MazeStage();
		aStarHeuristic = getAStarHeuristic();
		mazeListener = this::solve;
		cellWallRatio = options.cellWallRatio;
		mazeType = options.mazeType;
		solveType = options.solveType;
		doAnimateMaze = options.doAnimateMaze;
		doSolve = options.doSolve;
		doAnimateSolve = options.doAnimateSolve;
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

	/**
	 * All derived classes must implement their own canvas
	 */
	protected abstract MazeCanvas makeCanvas(double maxWidth, double maxHeight);

	/**
	 * Since maze shapes are all different, each derived class must implement
	 * their own heuristic for the {@link AStar A*} pathfinding algorithm
	 */
	protected abstract Function2<Integer, Integer, Double> getAStarHeuristic();

	/**
	 * @return a template of nodes with neighbors based on the shape of the maze
	 */
	protected abstract Node[] generateNodes();

	/**
	 * @return (startId, endId)
	 * @apiNote Needs to be implemented by base classes because of maze shapes
	 * are different and we want the start and end to be at the edges.
	 */
	protected abstract Pair<Integer, Integer> randomStartEnd();

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
