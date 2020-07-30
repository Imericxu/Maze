package imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

/**
 * Wilson's algorithm is slower but generates a completely unbiased maze
 */
public class Wilson extends Maze
{
    private final HashSet<OCell> knownCells;
    private final HashSet<OCell> unknownCells;
    private Stack<OCell> currentLoop;
    
    /**
     * Generates a rectangular maze
     */
    protected Wilson(int rows, int cols)
    {
        super(rows, cols);
        knownCells = new HashSet<>();
        unknownCells = new HashSet<>();
        for (var row : grid)
        {
            unknownCells.addAll(Arrays.asList(row));
        }
        var begin = grid[r.nextInt(rows)][r.nextInt(cols)];
        knownCells.add(begin);
        begin.setDisplay(Cell.Display.SHOW);
        changeList.push(begin);
    }
    
    @Override
    public boolean step()
    {
        if (!unknownCells.isEmpty())
        {
            var current = knownCells.iterator().next();
            currentLoop.push(current);
            getNeighbors(current);
        }
        return false;
    }
}
