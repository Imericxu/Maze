package imericxu.zhiheng.mazegen.maze_types.orthogonal;

import imericxu.zhiheng.mazegen.maze_types.Cell;

/**
 * A cell specialized for orthogonal mazes.<br/>
 * Contains 4 constants representing the walls:<br/>
 * {@link #TOP}, {@link #RIGHT}, {@link #BOTTOM}, {@link #LEFT}
 */
public class OCell extends Cell
{
    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;
    /**
     * An array of walls going clockwise from the top
     */
    private final boolean[] walls;
    
    /**
     * Defaults to having walls on all sides
     */
    public OCell(int row, int col)
    {
        super(row, col);
        walls = new boolean[]{true, true, true, true};
    }
    
    public OCell(OCell other)
    {
        super(other);
        walls = other.getWalls();
    }
    
    public boolean[] getWalls()
    {
        return walls;
    }
    
    /**
     * Used to alter a single wall
     *
     * @param wall    use one of the {@link OCell} constants
     * @param hasWall set the wall to true or false
     */
    public void setWall(int wall, boolean hasWall)
    {
        walls[wall] = hasWall;
    }
}
