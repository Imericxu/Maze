package imericxu.zhiheng.mazegen;

public class SquareMazeOptions extends MazeOptions
{
	public final int rows;
	public final int cols;
	
	public SquareMazeOptions(Controller.MazeType mazeType,
	                         Controller.SolveType solveType,
	                         int rows,
	                         int cols,
	                         double cellWallRatio,
	                         boolean doAnimateMaze,
	                         boolean doSolve,
	                         boolean doAnimateSolve)
	{
		super(mazeType, solveType, cellWallRatio, doAnimateMaze, doSolve, doAnimateSolve);
		this.rows = rows;
		this.cols = cols;
	}
}
