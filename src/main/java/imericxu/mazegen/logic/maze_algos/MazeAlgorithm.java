package imericxu.mazegen.logic.maze_algos;

import imericxu.mazegen.logic.Algorithm;
import imericxu.mazegen.logic.Node;
import imericxu.mazegen.logic.State;

import java.util.Arrays;

public abstract class MazeAlgorithm extends Algorithm {
	public MazeAlgorithm(Node[] nodes) {
		super(nodes, new State[nodes.length]);
		Arrays.fill(states, State.EMPTY);
	}
}
