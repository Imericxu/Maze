package imericxu.zhiheng.mazegen.maze_types;

import javafx.scene.paint.Color;

public class Cell
{
    public static final int TOP = 0;
    public static final int RIGHT = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;
    /**
     * An array of walls going clockwise from the top
     */
    protected final boolean[] walls;
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
        walls = new boolean[]{true, true, true, true};
    }
    
    public Cell(Cell other)
    {
        row = other.row;
        col = other.col;
        visited = other.visited;
        display = other.display;
        walls = other.getWalls();
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

    public boolean[] getWalls()
    {
        return walls;
    }

    /**
     * Used to alter a single wall
     *
     * @param wall    use one of the {@link Cell} constants
     * @param hasWall set the wall to true or false
     */
    public void setWall(int wall, boolean hasWall)
    {
        walls[wall] = hasWall;
    }

    public enum Display
    {
        HIDE(Color.web("0x1C518B")),
        SHOW(Color.WHITE),
        EXPLORE(Color.web("0xADD9FF")),
        CURRENT(Color.web("0x1B998B")),
        PATH(Color.web("0xAD360B"));
        private Color color;
    
        Display(Color color)
        {
            this.color = color;
        }
    
        public Color getColor()
        {
            return color;
        }
    
        public void setColor(Color color)
        {
            this.color = color;
        }
    }
}
