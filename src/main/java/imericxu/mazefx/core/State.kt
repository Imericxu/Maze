package imericxu.mazefx.core

/**
 * The states of nodes used by the algorithms
 */
enum class State {
	/**
	 * Represents a non-maze node
	 */
	EMPTY,

	/**
	 * Represents a node in an “explore state”
	 */
	PARTIAL,

	/**
	 * A maze node
	 */
	SOLID
}
