package imericxu.zhiheng.mazegen.maze.maze_algos;

import imericxu.zhiheng.mazegen.maze.Algorithm;
import imericxu.zhiheng.mazegen.maze.Node;
import imericxu.zhiheng.mazegen.maze.State;

import java.util.Arrays;

public abstract class MazeAlgorithm extends Algorithm
{
	public MazeAlgorithm(Node[] nodes)
	{
		super(nodes, new State[nodes.length]);
		Arrays.fill(states, State.EMPTY);
	}
}
