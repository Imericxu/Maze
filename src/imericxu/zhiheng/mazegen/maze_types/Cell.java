package imericxu.zhiheng.mazegen.maze_types;

/**
 * Contains 5 states:<br/>
 * OPEN = 0<br/>
 * WALL = 1<br/>
 * SEEN = 2<br/>
 * START = 3<br/>
 * END = 4<br/>
 */
public class Cell
{
    public static final int OPEN = 0;
    public static final int WALL = 1;
    public static final int SEEN = 2;
    public static final int START = 3;
    public static final int END = 4;
    // Prims
    public static final int FRONTIER = 5;
    private final int row;
    private final int col;
    private int state;
    
    public Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
        state = WALL;
    }
    
    public int getState()
    {
        return state;
    }
    
    public void setState(int state)
    {
        this.state = state;
    }
    
    public int getRow()
    {
        return row;
    }
    
    public int getCol()
    {
        return col;
    }
}
