package imericxu.mazefx.graphics

import imericxu.mazefx.core.Algorithm
import imericxu.mazefx.core.Node
import imericxu.mazefx.core.State
import javafx.geometry.Side
import javafx.scene.canvas.Canvas
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import kotlin.math.min
import kotlin.math.roundToInt

class MazeDrawer(
	canvas: Canvas,
	private val rows: Int,
	private val cols: Int,
	cellWallRatio: Float
) {
	companion object {
		/**
		 * Size of [wallSize] + [cellSize]
		 */
		private const val FULL_SIZE = 30
	}

	private val gc = canvas.graphicsContext2D
	private val drawList = mutableListOf<Rect>()
	private val wallSize: Int = (FULL_SIZE / (cellWallRatio + 1)).roundToInt()
	private val cellSize: Int = FULL_SIZE - wallSize
	private val width: Int = FULL_SIZE * cols + wallSize
	private val height: Int = FULL_SIZE * rows + wallSize
	private val image: WritableImage = WritableImage(width, height)
	private var scale: Double

	init {
		// Fill the background of the image with the EMPTY color and add the entire image to the draw list
		for (x in 0 until width) for (y in 0 until height)
			image.pixelWriter.setArgb(x, y, MazeColor.EMPTY.argb)

		drawList.add(Rect(0, 0, width, height))

		// Calculate the default scale of the image based on the canvas size
		val mazeRatio: Double = width.toDouble() / height
		val canvasRatio: Double = canvas.width / canvas.height

		scale = if (mazeRatio < canvasRatio) {
			canvas.height / height
		} else {
			canvas.width / width
		}

		// Image smoothing causes antialiasing of our pixels, which we don't want, because we want sharp pixels
		// Although, we do want it on when we're drawing less than a pixel
		gc.isImageSmoothing = scale * min(wallSize, cellSize) < 1.0

		// Clear the canvas
		gc.clearRect(0.0, 0.0, canvas.width, canvas.height)
	}

	/**
	 * Updates the image with the [change list][Algorithm.changeList] in [algorithm]
	 */
	fun update(algorithm: Algorithm) {
		val nodes = algorithm.nodes
		val states = algorithm.states

		algorithm.changeList.forEach { id ->
			val topLeft = calcCellTopLeftPos(id)

			val rect = topLeft.cellUpdateRect()

			drawList.add(rect)

			if (states[id] == State.EMPTY) {
				fillRect(MazeColor.EMPTY.argb, rect.x, rect.y, rect.width, rect.height)
				return@forEach
			}

			val cellArgb = states[id].argb

			updateCell(cellArgb, topLeft)

			nodes[id].connections.forEach { connectionId ->
				val wallArgb = if (states[connectionId] == State.SOLID) MazeColor.SOLID.argb else cellArgb

				when (connectionId) {
					id - cols -> updateWall(wallArgb, topLeft, Side.TOP)
					id + 1 -> updateWall(wallArgb, topLeft, Side.RIGHT)
					id + cols -> updateWall(wallArgb, topLeft, Side.BOTTOM)
					else -> updateWall(wallArgb, topLeft, Side.LEFT)
				}
			}
		}
	}

	/**
	 * Updates the entire image given the [nodes] and their [states] and adds a full-sized [Rect] to the [drawList]
	 */
	fun update(nodes: Array<Node>, states: Array<State>) {
		drawList.add(Rect(0, 0, width, height))

		var id = 0
		for (row in 0 until rows) for (col in 0 until cols) {
			val topLeft = calcCellTopLeftPos(id)
			val cellArgb = states[id].argb

			updateCell(cellArgb, topLeft)

			fun getWallColor(connectionId: Int): Int =
				if (states[connectionId] == State.SOLID) MazeColor.SOLID.argb else cellArgb

			val connections = nodes[id].connections
			with(id + 1) {
				if (col < cols - 1 && connections.contains(this))
					updateWall(getWallColor(this), topLeft, Side.RIGHT)
			}
			with(id + cols) {
				if (row < rows - 1 && connections.contains(this))
					updateWall(getWallColor(this), topLeft, Side.BOTTOM)
			}

			++id
		}
	}

	/**
	 * Colors and adds the respective rects for [startId] and [endId] to the [drawList].
	 *
	 * Should be called every draw loop to make sure they stay visible
	 */
	fun updateStartEnd(startId: Int, endId: Int) {
		with(calcCellTopLeftPos(startId)) {
			drawList.add(cellUpdateRect())
			updateCell(MazeColor.START.argb, this)
		}
		with(calcCellTopLeftPos(endId)) {
			drawList.add(cellUpdateRect())
			updateCell(MazeColor.END.argb, calcCellTopLeftPos(endId))
		}
	}

	/**
	 * Renders the scaled version of all the dirty [Rect]s in [drawList] then clears it
	 */
	fun render() {
		drawList.forEach { rect ->
			val cropped = WritableImage(image.pixelReader, rect.x, rect.y, rect.width, rect.height)
			gc.drawImage(cropped, rect.x * scale, rect.y * scale, rect.width * scale, rect.height * scale)
		}
		drawList.clear()
	}

	/**
	 * Called after [render] to draw the path directly onto the canvas using [gc]
	 */
	fun renderPath(pathList: List<Int>) {
		if (pathList.isEmpty()) return

		gc.stroke = MazeColor.PATH.color
		gc.lineWidth = scale * cellSize * 0.5

		val halfCellSize = cellSize / 2.0

		var prevPos = with(calcCellTopLeftPos(pathList.first())) {
			Pos(scale * (x + halfCellSize), scale * (y + halfCellSize))
		}
		var currentPos: Pos

		gc.beginPath()
		gc.moveTo(prevPos.x, prevPos.y)

		pathList
			.drop(1)
			.forEach {
				currentPos = with(calcCellTopLeftPos(it)) {
					Pos(scale * (x + halfCellSize), scale * (y + halfCellSize))
				}
				gc.lineTo(currentPos.x, currentPos.y)
				gc.moveTo(currentPos.x, currentPos.y)
				prevPos = currentPos
			}

		gc.closePath()
		gc.stroke()
	}

	private fun IntPos.cellUpdateRect() =
		Rect(
			x = x - wallSize,
			y = y - wallSize,
			width = FULL_SIZE + wallSize,
			height = FULL_SIZE + wallSize
		)

	/**
	 * Fill a section of the [image] with a given [color][argb]
	 */
	private fun fillRect(argb: Int, _x: Int, _y: Int, width: Int, height: Int) {
		for (x in _x until _x + width) {
			for (y in _y until _y + height) {
				image.pixelWriter.setArgb(x, y, argb)
			}
		}
	}

	/**
	 * Given the [topLeft] position of a cell, fill that cell in the [image], where [argb] is the color
	 */
	private fun updateCell(argb: Int, topLeft: IntPos) = fillRect(argb, topLeft.x, topLeft.y, cellSize, cellSize)

	/**
	 * Given the [topLeft] position of a cell and the [side] of the wall, fill the respective part of the [image] with
	 * the [color][argb]
	 */
	private fun updateWall(argb: Int, topLeft: IntPos, side: Side) {
		val (x, y) = topLeft
		when (side) {
			Side.TOP -> fillRect(argb, x, y - wallSize, cellSize, wallSize)
			Side.RIGHT -> fillRect(argb, x + cellSize, y, wallSize, cellSize)
			Side.BOTTOM -> fillRect(argb, x, y + cellSize, cellSize, wallSize)
			Side.LEFT -> fillRect(argb, x - wallSize, y, wallSize, cellSize)
		}
	}

	/**
	 * Calculates the top-left-corner coordinates of a cell given its [id]
	 */
	private fun calcCellTopLeftPos(id: Int): IntPos {
		val row = id / cols
		val col = id % cols
		return IntPos(FULL_SIZE * col + wallSize, FULL_SIZE * row + wallSize)
	}

	/**
	 * [Int] coordinates
	 */
	private data class IntPos(val x: Int, val y: Int)

	/**
	 * [Double] coordinates
	 */
	private data class Pos(val x: Double, val y: Double)

	/**
	 * Holds the top-left coordinates and dimensions of a rectangle
	 */
	private data class Rect(val x: Int, val y: Int, val width: Int, val height: Int)

	/**
	 * The respective [MazeColor.argb] color value of a [State]
	 */
	private val State.argb: Int
		get() = when (this) {
			State.EMPTY -> MazeColor.EMPTY.argb
			State.PARTIAL -> MazeColor.PARTIAL.argb
			State.SOLID -> MazeColor.SOLID.argb
		}

	/**
	 * Colors used by the maze
	 */
	private enum class MazeColor(argb: String) {
		EMPTY("ff1c5188"),
		PARTIAL("ffadd9ff"),
		SOLID("ffffffff"),
		PATH("ffad360b"),
		START("ff06d6a0"),
		END("ffaf2bbf");

		val color: Color = Color.web("#" + argb.drop(2))
		val argb: Int = argb.toLong(16).toInt()
	}
}
