package imericxu.zhiheng.mazegen.maze;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Node
{
	public final int id;
	private final Set<Integer> neighbors;
	private final Set<Integer> connections = new HashSet<>();
	
	public Node(int id, Set<Integer> neighbors)
	{
		this.id = id;
		this.neighbors = neighbors;
	}
	
	/**
	 * Pre: a and b must not be already connected<br>
	 * Adds each node to the other's connections list
	 */
	public static void connect(Node a, Node b)
	{
		assert a.neighbors.contains(b.id) && b.neighbors.contains(a.id);
		assert !a.connections.contains(b.id) && !b.connections.contains(a.id);
		a.connections.add(b.id);
		b.connections.add(a.id);
	}
	
	/**
	 * Pre: a and b must be connected<br>
	 * Removes each node from the other's connections list
	 */
	public static void disconnect(Node a, Node b)
	{
		assert a.neighbors.contains(b.id) && b.neighbors.contains(a.id);
		assert a.connections.contains(b.id) && b.connections.contains(a.id);
		a.connections.remove(b.id);
		b.connections.remove(a.id);
	}
	
	public Set<Integer> getConnections()
	{
		return Collections.unmodifiableSet(connections);
	}
	
	public Set<Integer> getNeighbors()
	{
		return Collections.unmodifiableSet(neighbors);
	}
}
