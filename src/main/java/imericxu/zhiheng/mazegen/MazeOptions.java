package imericxu.zhiheng.mazegen;

public class MazeOptions
{
	public final Controller.MazeType mazeType;
	public final Controller.SolveType solveType;
	public final double cellWallRatio;
	public final boolean doAnimateMaze;
	public final boolean doSolve;
	public final boolean doAnimateSolve;
	
	public MazeOptions(Controller.MazeType mazeType,
	                   Controller.SolveType solveType,
	                   double cellWallRatio,
	                   boolean doAnimateMaze,
	                   boolean doSolve,
	                   boolean doAnimateSolve)
	{
		this.mazeType = mazeType;
		this.solveType = solveType;
		this.cellWallRatio = cellWallRatio;
		this.doAnimateMaze = doAnimateMaze;
		this.doSolve = doSolve;
		this.doAnimateSolve = doAnimateSolve;
	}
}
