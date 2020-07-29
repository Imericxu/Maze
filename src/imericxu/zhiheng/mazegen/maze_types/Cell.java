package imericxu.zhiheng.mazegen.maze_types;

import javafx.scene.paint.Color;

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
    
    public Cell(Cell other)
    {
        row = other.row;
        col = other.col;
        visited = other.visited;
        display = other.display;
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
    
    public void clearVisited()
    {
        visited = 0;
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
        HIDE(Color.web("0x1C518B")),
        SHOW(Color.WHITE),
        EXPLORE(Color.web("0x82C7FF")),
        DARK(Color.web("0xFF5E4D")),
        PATH(Color.web("0xFF4422"));
        private final Color color;
        
        Display(Color color)
        {
            this.color = color;
        }
        
        public Color getColor()
        {
            return color;
        }
    }
}
