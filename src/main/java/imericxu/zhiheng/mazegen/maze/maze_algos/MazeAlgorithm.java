package imericxu.zhiheng.mazegen.maze.maze_algos;

import imericxu.zhiheng.mazegen.maze.Node;
import imericxu.zhiheng.mazegen.maze.State;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public abstract class MazeAlgorithm
{
	public final Queue<Integer> changeList = new LinkedList<>();
	public final Node[] nodes;
	public final State[] states;
	protected final Random rand = new Random();
	
	public MazeAlgorithm(Node[] nodes)
	{
		this.nodes = nodes;
		states = new State[nodes.length];
		Arrays.fill(states, State.DEFAULT);
	}
	
	/**
	 *
	 */
	public abstract void loopOnce();
	
	public void instantSolve()
	{
		while (!changeList.isEmpty())
			loopOnce();
	}
	
	protected void changeState(int index, State state)
	{
		states[index] = state;
		changeList.offer(index);
	}
	
	protected void changeState(Node node, State state)
	{
		changeState(node.id, state);
	}
}
