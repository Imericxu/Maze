package imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;

import java.util.*;

/**
 * Wilson's algorithm is slower but generates a completely unbiased maze
 */
public class Wilson extends Maze
{
    private final HashSet<OCell> knownCells;
    private final HashSet<OCell> unknownCells;
    private final Stack<OCell> currentWalk;
    
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
        begin.setDisplay(Cell.Display.SHOW);
        changeList.push(begin);
        
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
                        cell.setDisplay(Cell.Display.SHOW);
                    }
                    currentWalk.clear();
                }
                else
                {
                    random.setDisplay(Cell.Display.EXPLORE);
                    setWallsBetween(current, random, false);
                    changeList.push(random);
    
                    currentWalk.push(random);
                }
            }
            return false;
        }
        return true;
    }
    
    @Override
    public void instantSolve()
    {
        while (!step())
        {
            for (int i = 0; i < 50; ++i)
            {
                step();
                changeList.clear();
            }
        }
    }
    
    @Override
    public ArrayList<OCell> getNeighbors(OCell current)
    {
        var neighbors = super.getNeighbors(current);
        int size = currentWalk.size();
        if (size > 1) neighbors.remove(currentWalk.get(size - 2));
        return neighbors;
    }
    
    private void deleteLoop(OCell random)
    {
        var current = currentWalk.pop();
        current.setDisplay(Cell.Display.HIDE);
        
        if (currentWalk.size() == 1)
        {
            setWallsBetween(current, currentWalk.peek(), true);
            return;
        }
        
        var next = currentWalk.pop();
        next.setDisplay(Cell.Display.HIDE);
        setWallsBetween(current, next, true);
        
        Collections.addAll(changeList, current, next);
        
        while (currentWalk.size() > 1 && next != random)
        {
            current = next;
            next = currentWalk.pop();
            next.setDisplay(Cell.Display.HIDE);
            setWallsBetween(current, next, true);
            changeList.push(next);
        }
        
        setWallsBetween(next, currentWalk.peek(), true);
    }
    
    private void startNewWalk()
    {
        var walkStart = unknownCells.iterator().next();
        walkStart.setDisplay(Cell.Display.EXPLORE);
        currentWalk.push(walkStart);
        changeList.push(walkStart);
    }
}
