package imericxu.mazefx.core

class Node(val id: Int, val neighbors: Set<Int>) {
	val connections: Set<Int>
		get() = _connections.toSet()
	private val _connections: MutableSet<Int> = HashSet()

	fun connect(other: Node) {
		assert(neighbors.contains(other.id)) { "node $other.id not a neighbor of node $id" }
		assert(other.neighbors.contains(id)) { "node $id not a neighbor of node $other.id" }

		_connections.add(other.id)
		other._connections.add(id)
	}

	fun disconnect(other: Node) {
		assert(neighbors.contains(other.id)) { "node $other.id not a neighbor of node $id" }
		assert(other.neighbors.contains(id)) { "node $id not a neighbor of node $other.id" }

		_connections.remove(other.id)
		other._connections.remove(id)
	}
}
