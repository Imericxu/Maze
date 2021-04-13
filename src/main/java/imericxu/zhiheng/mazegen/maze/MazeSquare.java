package imericxu.zhiheng.mazegen.maze;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class MazeSquare
{
	public static Node[] generate(int rows, int cols)
	{
		assert rows > 0 && cols > 0;
		Node[] nodes = new Node[rows * cols];
		
		IntStream.range(0, nodes.length).parallel().forEach(i -> {
			final Set<Integer> neighbors = new HashSet<>();
			
			final int row = i / cols;
			final int col = i % cols;
			
			if (row > 0) neighbors.add(i - cols);
			
			if (col < cols - 1) neighbors.add(i + 1);
			
			if (row < rows - 1) neighbors.add(i + cols);
			
			if (col > 0) neighbors.add(i - 1);
			
			nodes[i] = new Node(i, neighbors);
		});
		
		return nodes;
	}
}
