package imericxu.zhiheng.mazegen.maze.maze_algos;

import imericxu.zhiheng.mazegen.maze.Cell;
import imericxu.zhiheng.mazegen.maze.Maze;

import java.util.*;

/**
 * Wilson's algorithm is slower but generates a completely unbiased maze
 */
public class Wilson extends Maze
{
	private final HashSet<Cell> knownCells;
	private final HashSet<Cell> unknownCells;
	private final Stack<Cell> currentWalk;
	
	/**
	 * Generates a rectangular maze
	 */
	public Wilson(int rows, int cols)
	{
		super(rows, cols);
		knownCells = new HashSet<>();
		unknownCells = new HashSet<>();
		currentWalk = new Stack<>();
		for (var row : grid)
		{
			unknownCells.addAll(Arrays.asList(row));
		}
		
		var begin = unknownCells.iterator().next();
		unknownCells.remove(begin);
		knownCells.add(begin);
		begin.setState(Cell.State.DONE);
		queueUpdate(begin);
		
		startNewWalk();
	}
	
	@Override
	public boolean step()
	{
		if (!unknownCells.isEmpty())
		{
			if (currentWalk.isEmpty())
			{
				startNewWalk();
			}
			else
			{
				var current = currentWalk.peek();
				var neighbors = getNeighbors(current);
				var random = neighbors.get(r.nextInt(neighbors.size()));
				
				if (currentWalk.contains(random))
				{
					if (currentWalk.size() > 1) deleteLoop(random);
				}
				else if (knownCells.contains(random))
				{
					setWallsBetween(current, random, false);
					knownCells.addAll(currentWalk);
					unknownCells.removeAll(currentWalk);
					for (var cell : currentWalk)
					{
						cell.setState(Cell.State.DONE);
					}
					currentWalk.clear();
				}
				else
				{
					random.setState(Cell.State.EXPLORE);
					setWallsBetween(current, random, false);
					queueUpdate(random);
					
					currentWalk.push(random);
				}
			}
			return false;
		}
		return true;
	}
	
	@Override
	public ArrayList<Cell> getNeighbors(Cell current)
	{
		var neighbors = super.getNeighbors(current);
		int size = currentWalk.size();
		if (size > 1) neighbors.remove(currentWalk.get(size - 2));
		return neighbors;
	}
	
	private void deleteLoop(Cell random)
	{
		var current = currentWalk.pop();
		current.setState(Cell.State.DEFAULT);
		
		if (currentWalk.size() == 1)
		{
			setWallsBetween(current, currentWalk.peek(), true);
			return;
		}
		
		var next = currentWalk.pop();
		next.setState(Cell.State.DEFAULT);
		setWallsBetween(current, next, true);
		
		Collections.addAll(changeList, current, next);
		
		while (currentWalk.size() > 1 && next != random)
		{
			current = next;
			next = currentWalk.pop();
			next.setState(Cell.State.DEFAULT);
			setWallsBetween(current, next, true);
			queueUpdate(next);
		}
		
		setWallsBetween(next, currentWalk.peek(), true);
	}
	
	private void startNewWalk()
	{
		var walkStart = unknownCells.iterator().next();
		walkStart.setState(Cell.State.EXPLORE);
		currentWalk.push(walkStart);
		queueUpdate(walkStart);
	}
}
