package imericxu.mazegen.user_input

import imericxu.mazegen.controller.MainController

data class MazeOptions(
	val rows: Int,
	val cols: Int,
	val cellWallRatio: Float,
	val mazeType: MainController.MazeType,
	val solveType: MainController.SolveType,
	val doAnimateMaze: Boolean,
	val doSolve: Boolean,
	val doAnimateSolve: Boolean
)
