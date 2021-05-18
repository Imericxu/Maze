package imericxu.mazefx.graphics

import imericxu.mazefx.core.Algorithm
import imericxu.mazefx.core.Node
import imericxu.mazefx.core.State
import javafx.geometry.Side
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color

class MazeDrawer(
	private val canvas: Canvas,
	private val rows: Int,
	private val cols: Int,
	cellWallRatio: Float
) {
	private val gc = canvas.graphicsContext2D
	private val wallSize: Double
	private val cellSize: Double

	init {
		val canvasRatio: Double = canvas.width / canvas.height
		val gridRatio: Double = cols.toDouble() / rows

		wallSize = if (canvasRatio > gridRatio) {
			canvas.height / (rows * (cellWallRatio + 1) + 1)
		} else {
			canvas.width / (cols * (cellWallRatio + 1) + 1)
		}

		cellSize = wallSize * cellWallRatio

		drawBlank()
	}

	fun drawUpdates(algorithm: Algorithm) {
		val nodes = algorithm.nodes
		val states = algorithm.states

		algorithm.changeList.forEach { id ->
			val topLeft = calcCellTopLeftPos(id)

			if (states[id] == State.EMPTY) {
				gc.fill = State.EMPTY.color
				val size = cellSize + 2 * wallSize
				gc.fillRect(topLeft.x - wallSize, topLeft.y - wallSize, size, size)
				return@forEach
			}

			val cellColor = states[id].color

			drawCell(topLeft, cellColor)

			nodes[id].connections.forEach { connectionId ->
				val wallColor = if (states[connectionId] == State.SOLID) MazeColor.SOLID.color else cellColor

				when (connectionId) {
					id - cols -> drawWall(topLeft, Side.TOP, wallColor)
					id + 1 -> drawWall(topLeft, Side.RIGHT, wallColor)
					id + cols -> drawWall(topLeft, Side.BOTTOM, wallColor)
					else -> drawWall(topLeft, Side.LEFT, wallColor)
				}
			}
		}
	}

	fun drawMaze(nodes: Array<Node>, states: Array<State>) {
		var id = 0
		for (row in 0 until rows) {
			for (col in 0 until cols) {
				val topLeft = calcCellTopLeftPos(id)
				val cellColor = states[id].color

				drawCell(topLeft, cellColor)

				fun getWallColor(connectionId: Int): Color =
					if (states[connectionId] == State.SOLID) MazeColor.SOLID.color else cellColor

				val connections = nodes[id].connections
				with(id + 1) {
					if (col < cols - 1 && connections.contains(this))
						drawWall(topLeft, Side.RIGHT, getWallColor(this))
				}
				with(id + cols) {
					if (row < rows - 1 && connections.contains(this))
						drawWall(topLeft, Side.BOTTOM, getWallColor(this))
				}

				++id
			}
		}
	}

	fun drawPath(pathList: List<Int>) {
		if (pathList.isEmpty()) return

		gc.stroke = MazeColor.PATH.color
		gc.lineWidth = cellSize * 0.5

		val halfCellSize = cellSize / 2.0

		var prevPos = with(calcCellTopLeftPos(pathList.first())) {
			Pos(x + halfCellSize, y + halfCellSize)
		}
		var currentPos: Pos

		pathList
			.drop(1)
			.forEach {
				currentPos = with(calcCellTopLeftPos(it)) {
					Pos(x + halfCellSize, y + halfCellSize)
				}
				gc.strokeLine(prevPos.x, prevPos.y, currentPos.x, currentPos.y)
				prevPos = currentPos
			}
	}

	fun drawStartAndEnd(startId: Int, endId: Int) {
		drawCell(calcCellTopLeftPos(startId), MazeColor.START.color)
		drawCell(calcCellTopLeftPos(endId), MazeColor.END.color)
	}

	fun drawBlank() {
		gc.fill = MazeColor.EMPTY.color
		gc.fillRect(0.0, 0.0, canvas.width, canvas.height)
	}

	private fun drawCell(topLeft: Pos, color: Color) {
		gc.fill = color
		gc.fillRect(topLeft.x, topLeft.y, cellSize, cellSize)
	}

	private fun drawWall(topLeft: Pos, side: Side, color: Color) {
		gc.fill = color
		when (side) {
			Side.TOP -> gc.fillRect(topLeft.x, topLeft.y - wallSize, cellSize, wallSize)
			Side.RIGHT -> gc.fillRect(topLeft.x + cellSize, topLeft.y, wallSize, cellSize)
			Side.BOTTOM -> gc.fillRect(topLeft.x, topLeft.y + cellSize, cellSize, wallSize)
			Side.LEFT -> gc.fillRect(topLeft.x - wallSize, topLeft.y, wallSize, cellSize)
		}
	}

	private fun calcCellTopLeftPos(id: Int): Pos {
		val row = id / cols
		val col = id % cols
		return with(wallSize + cellSize) {
			Pos(this * col + wallSize, this * row + wallSize)
		}
	}

	private data class Pos(val x: Double, val y: Double)

	private val State.color: Color
		get() = when (this) {
			State.EMPTY -> MazeColor.EMPTY.color
			State.PARTIAL -> MazeColor.PARTIAL.color
			State.SOLID -> MazeColor.SOLID.color
		}

	private enum class MazeColor(val color: Color) {
		EMPTY(Color.web("0x1c5188")),
		PARTIAL(Color.web("0xADD9FF")),
		SOLID(Color.WHITE),
		PATH(Color.web("0xAD360B")),
		START(Color.web("#06D6A0")),
		END(Color.web("#AF2BBF"))
	}
}
