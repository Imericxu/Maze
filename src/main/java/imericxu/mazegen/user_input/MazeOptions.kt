package imericxu.mazegen.user_input

import imericxu.mazegen.controller.Controller

data class MazeOptions(
	val rows: Int,
	val cols: Int,
	val cellWallRatio: Float,
	val mazeType: Controller.MazeType,
	val solveType: Controller.SolveType,
	val doAnimateMaze: Boolean,
	val doSolve: Boolean,
	val doAnimateSolve: Boolean
)
