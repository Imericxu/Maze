package imericxu.zhiheng.mazegen.maze.solve_algos;

import imericxu.zhiheng.mazegen.maze.Algorithm;
import imericxu.zhiheng.mazegen.maze.Node;
import imericxu.zhiheng.mazegen.maze.State;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public abstract class PathAlgorithm extends Algorithm
{
	public final int startId;
	public final int endId;
	protected final Stack<Integer> path = new Stack<>();
	
	public PathAlgorithm(Node[] nodes, int startId, int endId)
	{
		super(nodes, new State[nodes.length]);
		Arrays.fill(states, State.SOLID);
		this.startId = startId;
		this.endId = endId;
	}
	
	@Override
	public void loopOnce()
	{
		changeList.addAll(path);
		super.loopOnce();
	}
	
	public List<Integer> getPath()
	{
		return Collections.unmodifiableList(path);
	}
}
