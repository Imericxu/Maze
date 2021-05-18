package imericxu.mazegen.core.maze_algorithm

enum class MazeType(private val label: String) {
	RANDOM("Random"),
	PRIM("Prim’s Algorithm"),
	RECURSIVE("Backtracking Algorithm"),
	WILSON("Wilson’s Algorithm"),
	KRUSKAL("Kruskal’s Algorithm");

	override fun toString(): String = label
}
