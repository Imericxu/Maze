package imericxu.mazefx.graphics

import imericxu.mazefx.core.Node
import imericxu.mazefx.core.maze_algorithm.MazeAlgorithm
import imericxu.mazefx.core.maze_algorithm.MazeType
import imericxu.mazefx.core.maze_algorithm.algorithms.Backtracking
import imericxu.mazefx.core.maze_algorithm.algorithms.Kruskals
import imericxu.mazefx.core.maze_algorithm.algorithms.Prims
import imericxu.mazefx.core.maze_algorithm.algorithms.Wilsons
import imericxu.mazefx.core.solve_algorithm.SolveAlgorithm
import imericxu.mazefx.core.solve_algorithm.SolveType
import imericxu.mazefx.core.solve_algorithm.algorithms.AStar
import imericxu.mazefx.core.solve_algorithm.algorithms.Breadth
import imericxu.mazefx.core.solve_algorithm.algorithms.Tremaux
import imericxu.mazefx.graphics.timers.TimerMaze
import imericxu.mazefx.graphics.timers.TimerSolve
import imericxu.mazefx.user_input.MazeOptions
import javafx.animation.AnimationTimer
import javafx.scene.canvas.Canvas
import kotlin.math.abs
import kotlin.random.Random

class Maze(private val options: MazeOptions, canvas: Canvas) {
	private val mazeDrawer = MazeDrawer(canvas, options.rows, options.cols, options.cellWallRatio)
	private var timerMaze: AnimationTimer? = null
	private var timerSolve: AnimationTimer? = null
	private val aStarHeuristic = { id1: Int, id2: Int ->
		val start = idToRowCol(id1, options.cols)
		val end = idToRowCol(id2, options.cols)
		(abs(end.col - start.col) + abs(end.row - start.row)).toDouble()
	}

	companion object {
		@JvmStatic
		fun generateNodes(rows: Int, cols: Int): Array<Node> =
			Array(rows * cols) { id ->
				val neighbors = HashSet<Int>()
				with(idToRowCol(id, cols)) {
					if (row > 0) neighbors.add(id - cols)
					if (col < cols - 1) neighbors.add(id + 1)
					if (row < rows - 1) neighbors.add(id + cols)
					if (col > 0) neighbors.add(id - 1)
				}
				Node(id, neighbors)
			}

		fun idToRowCol(id: Int, cols: Int) = RowCol(id / cols, id % cols)

		fun rowColToId(row: Int, col: Int, cols: Int): Int = row * cols + col

		data class RowCol(val row: Int, val col: Int)
	}

	fun generate() {
		// TODO Remove unnecessary call
		mazeDrawer.drawBlank()

		val mazeAlgorithm = makeMazeAlgorithm(options.mazeType)

		if (options.doAnimateMaze) {
			timerMaze = TimerMaze(this::solve, mazeDrawer, mazeAlgorithm)
			timerMaze!!.start()
		} else {
			mazeAlgorithm.finishImmediately()
			mazeDrawer.drawMaze(mazeAlgorithm.nodes, mazeAlgorithm.states)
			solve(mazeAlgorithm.nodes)
		}
	}

	fun solve(nodes: Array<Node>) {
		if (!options.doSolve) return

		val solveAlgorithm = makeSolveAlgorithm(options.solveType, nodes)

		if (options.doAnimateSolve) {
			timerSolve = TimerSolve(mazeDrawer, solveAlgorithm)
			timerSolve!!.start()
		} else {
			solveAlgorithm.finishImmediately()
			mazeDrawer.drawMaze(solveAlgorithm.nodes, solveAlgorithm.states)
			mazeDrawer.drawStartAndEnd(solveAlgorithm.startId, solveAlgorithm.endId)
			mazeDrawer.drawPath(solveAlgorithm.path)
		}
	}

	fun stop() {
		timerMaze?.stop()
		timerSolve?.stop()
	}

	fun interface MazeListener {
		fun onFinishMazeGeneration(nodes: Array<Node>)
	}

	// TODO Clean up redundant parameter
	private fun makeMazeAlgorithm(_mazeType: MazeType): MazeAlgorithm {
		// TODO Move randomizer to enum itself
		val mazeType = when (_mazeType) {
			MazeType.RANDOM -> MazeType.values()
				.filterNot { it == MazeType.RANDOM }
				.random()
			else -> _mazeType
		}

		val nodes = generateNodes(options.rows, options.cols)
		return when (mazeType) {
			MazeType.PRIM -> Prims(nodes)
			MazeType.BACKTRACKING -> Backtracking(nodes)
			MazeType.WILSON -> Wilsons(nodes)
			MazeType.KRUSKAL -> Kruskals(nodes)
			else -> throw IllegalStateException("Type $mazeType unexpected!")
		}
	}

	private fun makeSolveAlgorithm(_solveType: SolveType, nodes: Array<Node>): SolveAlgorithm {
		val solveType = when (_solveType) {
			SolveType.RANDOM -> SolveType.values()
				.filterNot { it == SolveType.RANDOM }
				.random()
			else -> _solveType
		}

		val (startId, endId) = randomStartEnd(options.rows, options.cols)
		return when (solveType) {
			SolveType.TREMAUX -> Tremaux(nodes, startId, endId)
			SolveType.ASTAR -> AStar(nodes, startId, endId, aStarHeuristic)
			SolveType.BREADTH -> Breadth(nodes, startId, endId)
			else -> throw IllegalStateException("Unexpected type $solveType")
		}
	}

	private fun randomStartEnd(rows: Int, cols: Int): StartEnd {
		val id1: Int
		val id2: Int
		if (Random.nextBoolean()) {
			// Horizontal
			id1 = rowColToId(Random.nextInt(rows), 0, cols)
			id2 = rowColToId(Random.nextInt(rows), cols - 1, cols)
		} else {
			// Vertical
			id1 = rowColToId(0, Random.nextInt(cols), cols)
			id2 = rowColToId(rows - 1, Random.nextInt(cols), cols)
		}
		return with(listOf(id1, id2).shuffled()) {
			StartEnd(get(0), get(1))
		}
	}

	private data class StartEnd(val startId: Int, val endId: Int)
}
