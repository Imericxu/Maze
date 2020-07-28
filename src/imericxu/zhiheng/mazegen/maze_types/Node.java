package imericxu.zhiheng.mazegen.maze_types;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;

/**
 * Path-finding {@link Cell} specialized for the algorithms
 */
public class Node extends OCell
{
    private double f;
    private double g;
    
    /**
     * {@link #f} and {@link #g} default to infinity
     */
    public Node(OCell cell)
    {
        super(cell);
        f = Double.POSITIVE_INFINITY;
        g = Double.POSITIVE_INFINITY;
    }
    
    public static double heuristic(Cell c1, Cell c2, Heuristic h)
    {
        int row1 = c1.getRow();
        int col1 = c1.getCol();
        int row2 = c2.getRow();
        int col2 = c2.getCol();
        
        return switch (h)
                {
                    case MANHATTAN -> Math.abs(row2 - row1) + Math.abs(col2 - col1);
                    case EUCLIDEAN -> Math.hypot(row2 - row1, col2 - col1);
                };
    }
    
    public double getF()
    {
        return f;
    }

    public void setF(double f)
    {
        this.f = f;
    }
    
    public double getG()
    {
        return g;
    }
    
    public void setG(double g)
    {
        this.g = g;
    }
    
    public enum Heuristic
    {
        MANHATTAN, EUCLIDEAN
    }
}
