package imericxu.mazefx.user_input

import imericxu.mazefx.core.maze_algorithm.MazeType
import imericxu.mazefx.core.solve_algorithm.SolveType

data class MazeOptions(
	val rows: Int,
	val cols: Int,
	val cellWallRatio: Float,
	val mazeType: MazeType,
	val solveType: SolveType,
	val doAnimateMaze: Boolean,
	val doSolve: Boolean,
	val doAnimateSolve: Boolean
)
