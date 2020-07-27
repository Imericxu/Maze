package imericxu.zhiheng.mazegen.maze_types.orthogonal;

import imericxu.zhiheng.mazegen.maze_types.Cell;

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
    
    public OCell(int row, int col)
    {
        super(row, col);
        walls = new boolean[]{true, true, true, true};
    }
    
    public boolean[] getWalls()
    {
        return walls;
    }
    
    public void setWall(int wall, boolean hasWall)
    {
        walls[wall] = hasWall;
    }
}
