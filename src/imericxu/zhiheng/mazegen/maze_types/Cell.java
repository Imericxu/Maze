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
    private final int row;
    private final int col;
    private int visited;
    
    public Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
        visited = 0;
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
        ++visited;
    }
    
    @Override
    public String toString()
    {
        return String.format("(%d, %d)", row, col);
    }
}
