package imericxu.zhiheng.mazegen.maze;

import imericxu.zhiheng.mazegen.maze.maze_algos.MazeAlgorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public abstract class Pathfinder
{
	public final Queue<Node> changeList = new LinkedList<>();
	protected final Node[] nodes;
	//	protected final Node start;
//	protected final Node end;
	protected Stack<Node> path;
	
	public Pathfinder(MazeAlgorithm mazeAlgorithm)
	{
		path = new Stack<>();
		nodes = mazeAlgorithm.nodes;
//		start = mazeAlgorithm.getStart();
//		end = mazeAlgorithm.getEnd();

//		path.push(start);
	}
	
	public abstract boolean step();
	
	public Stack<Node> getPath()
	{
		return path;
	}
	
	protected void queueUpdate(Node node)
	{
		changeList.add(node);
	}
}
