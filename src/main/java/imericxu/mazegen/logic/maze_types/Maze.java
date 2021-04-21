package imericxu.mazegen.logic.maze_types;

import imericxu.mazegen.Controller;
import imericxu.mazegen.graphics.timers.TimerMaze;
import imericxu.mazegen.graphics.timers.TimerSolve;
import imericxu.mazegen.logic.Node;
import imericxu.mazegen.logic.maze_algos.Backtracker;
import imericxu.mazegen.logic.maze_algos.MazeAlgorithm;
import imericxu.mazegen.logic.maze_algos.Prims;
import imericxu.mazegen.logic.maze_algos.Wilson;
import imericxu.mazegen.logic.solve_algos.AStar;
import imericxu.mazegen.logic.solve_algos.BreadthFirstSearch;
import imericxu.mazegen.logic.solve_algos.SolveAlgorithm;
import imericxu.mazegen.logic.solve_algos.Tremaux;
import imericxu.mazegen.user_input.MazeOptions;
import imericxu.mazegen.graphics.MazeStage;
import imericxu.mazegen.graphics.canvases.MazeCanvas;
import org.jetbrains.annotations.NotNull;

public abstract class Maze {
	private final MazeStage stage;
	private final AStar.Heuristic aStarHeuristic;
	private final MazeListener mazeListener;
	private final Controller.MazeType mazeType;
	private final Controller.SolveType solveType;
	private final boolean doAnimateMaze;
	private final boolean doSolve;
	private final boolean doAnimateSolve;
	protected double cellWallRatio;

	public Maze(@NotNull MazeOptions options) {
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
			mazeAlgo.instantSolve();
			canvas.drawMaze(mazeAlgo.getNodesCopy());
			solve(mazeAlgo.getNodesCopy());
		}
	}

	public void solve(Node[] nodes) {
		if (!doSolve) return;

		final var solveAlgo = makeSolveAlgorithm(solveType, nodes);
		final var canvas = stage.getCanvas();

		if (doAnimateSolve) {
			TimerSolve timerSolve = new TimerSolve(canvas, solveAlgo);
			timerSolve.start();
		} else {
			solveAlgo.instantSolve();
			canvas.drawMaze(solveAlgo.getNodesCopy());
			canvas.drawPath(solveAlgo.getPath());
		}
	}

	protected abstract MazeCanvas makeCanvas(double maxWidth, double maxHeight);

	private MazeAlgorithm makeMazeAlgorithm(Controller.MazeType type) {
		final var nodes = generateNodes();
		return switch (type) {
			case PRIM -> new Prims(nodes);
			case RECURSIVE -> new Backtracker(nodes);
			case WILSON -> new Wilson(nodes);
		};
	}

	private SolveAlgorithm makeSolveAlgorithm(Controller.SolveType type, @NotNull Node[] nodes) {
		final int start = 0;
		final int end = nodes.length - 1;

		return switch (type) {
			case TREMAUX -> new Tremaux(nodes, start, end);
			case ASTAR -> new AStar(nodes, start, end, aStarHeuristic);
			case BREADTH -> new BreadthFirstSearch(nodes, start, end);
		};
	}

	protected abstract AStar.Heuristic getAStarHeuristic();

	protected abstract Node[] generateNodes();

	public interface MazeListener {
		void onFinishMazeGeneration(Node[] nodes);
	}
}
