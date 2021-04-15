package imericxu.mazegen.user_input;

import imericxu.mazegen.Controller;

public class OrthoMazeOptions extends MazeOptions {
	public final int rows;
	public final int cols;

	public OrthoMazeOptions(Controller.MazeType mazeType,
	                        Controller.SolveType solveType,
	                        int rows,
	                        int cols,
	                        double cellWallRatio,
	                        boolean doAnimateMaze,
	                        boolean doSolve,
	                        boolean doAnimateSolve) {
		super(mazeType, solveType, cellWallRatio, doAnimateMaze, doSolve, doAnimateSolve);
		this.rows = rows;
		this.cols = cols;
	}
}
