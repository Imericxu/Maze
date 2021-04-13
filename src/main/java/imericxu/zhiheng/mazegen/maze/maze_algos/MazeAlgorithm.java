package imericxu.zhiheng.mazegen.maze.maze_algos;

import imericxu.zhiheng.mazegen.maze.Node;
import imericxu.zhiheng.mazegen.maze.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class MazeAlgorithm
{
	public final List<Integer> changeList = new ArrayList<>();
	public final Node[] nodes;
	public final State[] states;
	protected final Random rand = new Random();
	
	public MazeAlgorithm(Node[] nodes)
	{
		this.nodes = nodes;
		states = new State[nodes.length];
		Arrays.fill(states, State.EMPTY);
	}
	
	/**
	 *
	 */
	public abstract void loopOnce();
	
	public void instantSolve()
	{
		do
		{
			changeList.clear();
			loopOnce();
		} while (changeList.isEmpty());
	}
	
	protected void changeState(int index, State state)
	{
		states[index] = state;
		changeList.add(index);
	}
	
	protected void changeState(Node node, State state)
	{
		changeState(node.id, state);
	}
}
