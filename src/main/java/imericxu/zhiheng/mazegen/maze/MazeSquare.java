package imericxu.zhiheng.mazegen.maze;

import imericxu.zhiheng.mazegen.maze.maze_algos.Node;

import java.util.ArrayList;

public class MazeSquare
{
    public static Node[] generate(int rows, int cols)
    {
        Node[] nodes = new Node[rows * cols];
        
        for (int i = nodes.length - 1; i >= 0; --i)
        {
            nodes[i] = new Node(i);
        }
        
        for (int row = 0, i = 0; row < rows; ++row)
        {
            for (int col = 0; col < cols; ++col, ++i)
            {
                final int index = row * cols + col;
                final var neighbors = new ArrayList<Node>();
                
                // Top
                if (row > 0) neighbors.add(nodes[index - cols]);
                // Right
                if (col < cols - 1) neighbors.add(nodes[index + 1]);
                // Bottom
                if (row < rows - 1) neighbors.add(nodes[index + cols]);
                // Left
                if (col > 0) neighbors.add(nodes[index - 1]);
                
                nodes[index].init(neighbors);
            }
        }
        
        return nodes;
    }
}