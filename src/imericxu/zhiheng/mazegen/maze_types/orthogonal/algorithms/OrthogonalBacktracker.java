package imericxu.zhiheng.mazegen.maze_types.orthogonal.algorithms;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OrthogonalMaze;

import java.util.Stack;

public class OrthogonalBacktracker extends OrthogonalMaze
{
    private final Stack<Cell> stack;
    
    /**
     * Generates a rectangular maze
     */
    protected OrthogonalBacktracker(int rows, int cols)
    {
        super(rows, cols);
        stack = new Stack<>();
    }
    
    @Override
    public void step()
    {
    
    }
}
