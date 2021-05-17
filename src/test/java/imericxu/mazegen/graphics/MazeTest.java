package imericxu.mazegen.graphics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

class MazeTest {
	@Test
	void generate1x1() {
		final var nodes = Maze.generateNodes(1, 1);
		Assertions.assertEquals(1, nodes.length);
		Assertions.assertTrue(nodes[0].getNeighbors().isEmpty());
		Assertions.assertTrue(nodes[0].getConnections().isEmpty());
	}

	@Test
	void generate1x5() {
		final var nodes = Maze.generateNodes(1, 5);
		Assertions.assertEquals(5, nodes.length);
		Assertions.assertEquals(Set.of(1), nodes[0].getNeighbors());
		Assertions.assertEquals(Set.of(2, 4), nodes[3].getNeighbors());
		Assertions.assertEquals(Set.of(3), nodes[4].getNeighbors());
	}

	@Test
	void generate5x1() {
		final var nodes = Maze.generateNodes(5, 1);
		Assertions.assertEquals(5, nodes.length);
		Assertions.assertEquals(Set.of(1), nodes[0].getNeighbors());
		Assertions.assertEquals(Set.of(2, 4), nodes[3].getNeighbors());
		Assertions.assertEquals(Set.of(3), nodes[4].getNeighbors());
	}

	@Test
	void generate5x5() {
		final var nodes = Maze.generateNodes(5, 5);
		Assertions.assertEquals(25, nodes.length);

		// Test the 4 corners
		Assertions.assertEquals(Set.of(1, 5), nodes[0].getNeighbors());
		Assertions.assertEquals(Set.of(3, 9), nodes[4].getNeighbors());
		Assertions.assertEquals(Set.of(15, 21), nodes[20].getNeighbors());
		Assertions.assertEquals(Set.of(19, 23), nodes[24].getNeighbors());

		// Test 10 random neighbors of nodes in the middle
		final var rand = new Random();
		IntStream.range(0, 10).parallel().forEach(i -> {
			final int row = rand.nextInt(3) + 1;
			final int col = rand.nextInt(3) + 1;
			final int id = row * 5 + col;
			Assertions.assertEquals(Set.of(id - 5, id + 1, id + 5, id - 1),
			                        nodes[id].getNeighbors());
		});

		// Test a couple edges
		Assertions.assertEquals(Set.of(2, 4, 8), nodes[3].getNeighbors());
		Assertions.assertEquals(Set.of(4, 8, 14), nodes[9].getNeighbors());
		Assertions.assertEquals(Set.of(5, 11, 15), nodes[10].getNeighbors());
		Assertions.assertEquals(Set.of(22, 18, 24), nodes[23].getNeighbors());
	}
}
