package imericxu.zhiheng.mazegen.maze_types;

public class Cell
{
    private final int row;
    private final int col;
    private int visited;
    private Display display;
    
    public Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
        visited = 0;
        display = Display.HIDE;
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
    
    public Display getDisplay()
    {
        return display;
    }
    
    public void setDisplay(Display display)
    {
        this.display = display;
    }
    
    @Override
    public String toString()
    {
        return String.format("(%d, %d)", row, col);
    }
    
    public enum Display
    {
        HIDE, SHOW, COLOR_1, COLOR_2
    }
}
