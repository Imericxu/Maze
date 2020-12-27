package imericxu.zhiheng.mazegen.maze;

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
    public State state;
    private int visited;
    
    public Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
        visited = 0;
        state = State.DEFAULT;
        walls = new boolean[]{true, true, true, true};
    }
    
    public Cell(Cell other)
    {
        row = other.row;
        col = other.col;
        visited = other.visited;
        state = other.state;
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
    
    public State getState()
    {
        return state;
    }
    
    public void setState(State state)
    {
        this.state = state;
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
    
    public enum State
    {
        DEFAULT, EXPLORE, DONE,
    }
}
