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
    public static final int VISITED = 2;
    public static final int START = 3;
    public static final int END = 4;
    // Prims
    public static final int SPECIAL = 5;
    private final int row;
    private final int col;
    private int state;
    private int visited;
    
    public Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
        state = WALL;
        visited = 0;
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
    
    public int getVisited()
    {
        return visited;
    }
    
    public void visited()
    {
        state = VISITED;
        ++visited;
    }
    
    @Override
    public String toString()
    {
        String name = switch (state)
                {
                    case OPEN -> "Open";
                    case WALL -> "Wall";
                    case VISITED -> "Visited";
                    case START -> "Start";
                    case END -> "End";
                    case SPECIAL -> "Special";
                    default -> "???";
                };
        return name + String.format(" (%d, %d)", row, col);
    }
}
