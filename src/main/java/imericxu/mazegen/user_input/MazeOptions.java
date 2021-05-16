package imericxu.mazegen.user_input;

import imericxu.mazegen.Controller;

public class MazeOptions {
	public final int rows;
	public final int cols;
	public final Controller.MazeType mazeType;
	public final Controller.SolveType solveType;
	public final double cellWallRatio;
	public final boolean doAnimateMaze;
	public final boolean doSolve;
	public final boolean doAnimateSolve;

	public MazeOptions(
			Controller.MazeType mazeType,
			Controller.SolveType solveType,
			int rows,
			int cols,
			double cellWallRatio,
			boolean doAnimateMaze,
			boolean doSolve,
			boolean doAnimateSolve
	) {
		this.mazeType = mazeType;
		this.solveType = solveType;
		this.cellWallRatio = cellWallRatio;
		this.doAnimateMaze = doAnimateMaze;
		this.doSolve = doSolve;
		this.doAnimateSolve = doAnimateSolve;
		this.rows = rows;
		this.cols = cols;
	}
}
