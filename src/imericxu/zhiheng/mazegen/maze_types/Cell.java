package imericxu.zhiheng.mazegen.maze_types;

public class Cell
{
    private final int row;
    private final int col;
    private State state;
    
    /**
     * Defaults to a wall
     */
    public Cell(int row, int col)
    {
        this.row = row;
        this.col = col;
        state = State.WALL;
    }

    public Cell shift(int row, int col)
    {
        return new Cell(this.row + row, this.col + col);
    }
    
    public int getRow()
    {
        return row;
    }
    
    public int getCol()
    {
        return col;
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
        return state.toString();
    }
    
    public enum State
    {
        OPEN, WALL, FOUND, START, END
    }
}
