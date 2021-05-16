package imericxu.mazegen.logic;

import imericxu.mazegen.core.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.IntStream;

class NodeTest {
	private Node[] nodes;

	@BeforeEach
	void setUp() {
		nodes = new Node[5];
		nodes[0] = new Node(0, Set.of(1));
		IntStream.range(1, 4).forEach(i -> nodes[i] = new Node(i, Set.of(i - 1, i + 1)));
		nodes[4] = new Node(4, Set.of(3));
		assert (nodes[0].getConnections().isEmpty());
	}

	@Test
	void connect() {
		nodes[0].connect(nodes[1]);
		Assertions.assertEquals(Set.of(1), nodes[0].getConnections());
		Assertions.assertEquals(Set.of(0), nodes[1].getConnections());
	}

	@Test
	void disconnect() {
		nodes[0].connect(nodes[1]);
		nodes[0].disconnect(nodes[1]);
		Assertions.assertTrue(nodes[0].getConnections().isEmpty());
		Assertions.assertTrue(nodes[1].getConnections().isEmpty());
	}
}
