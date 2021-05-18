package imericxu.mazegen.user_input

import imericxu.mazegen.core.maze_algorithm.MazeType
import imericxu.mazegen.core.solve_algorithm.SolveType

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
