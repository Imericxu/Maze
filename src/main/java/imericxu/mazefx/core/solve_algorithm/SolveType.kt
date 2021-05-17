package imericxu.mazefx.core.solve_algorithm

enum class SolveType(private val label: String) {
	RANDOM("Random"),
	TREMAUX("Trémaux"),
	ASTAR("A*"),
	BREADTH("Breadth First Search");

	override fun toString(): String = label
}
