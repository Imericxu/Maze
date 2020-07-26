package imericxu.zhiheng.mazegen.maze_types.orthogonal;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class OrthogonalCanvas extends Canvas
{
    private final int cellSize;
    private final int wallSize;
    private final OrthogonalMaze maze;
    private final Cell[][] grid;
    private final Cell start;
    private final Cell end;
    private final GraphicsContext gc;
    
    public OrthogonalCanvas(OrthogonalMaze maze)
    {
        // TODO: Calculate canvas width based on rows and cols
        cellSize = 14;
        wallSize = 2;
        this.maze = maze;
        this.grid = maze.getGrid();
        setWidth((cellSize - wallSize) * this.grid[0].length);
        setHeight((cellSize - wallSize) * this.grid.length);
        gc = getGraphicsContext2D();
        start = maze.getStart();
        end = maze.getEnd();
    }
    
    public void drawMaze()
    {
        // gc.setFill(Color.BLACK);
        // gc.fillRect(0, 0, getWidth(), getHeight());
        
        // TODO: Draw walls with variable width
        // Vertical walls
        for (int row = 1; row < grid.length; row += 2)
        {
            for (int col = 0; col < grid[row].length; col += 2)
            {
                gc.setFill(grid[row][col].getState() == Cell.State.WALL ? Color.BLACK : Color.WHITE);
                // 0, 2, 14
                // 0, 2, 14, 16, 28
                int cWall = (int) Math.ceil(col / 2.0);
                int cCell = col - cWall;
                int x = wallSize * cWall + cellSize * cCell;
                cWall = (int) Math.ceil(row / 2.0);
                cCell = row - cWall;
                int y = wallSize * cWall + cellSize * cCell;
                gc.fillRect(x, y, wallSize, cellSize);
            }
        }
        
        // Horizontal walls
        for (int row = 0; row < grid.length; row += 2)
        {
            for (int col = 1; col < grid[row].length; col += 2)
            {
                gc.setFill(grid[row][col].getState() == Cell.State.WALL ? Color.BLACK : Color.WHITE);
                int cWall = (int) Math.ceil(col / 2.0);
                int cCell = col - cWall;
                int x = wallSize * cWall + cellSize * cCell;
                cWall = (int) Math.ceil(row / 2.0);
                cCell = row - cWall;
                int y = wallSize * cWall + cellSize * cCell;
                gc.fillRect(x, y, cellSize, wallSize);
            }
        }
        
        // Discovered cells
        gc.setFill(Color.WHITE);
        for (int row = 1; row < grid.length; row += 2)
        {
            for (int col = 1; col < grid[0].length; col += 2)
            {
                if (grid[row][col].getState() == Cell.State.FOUND)
                {
                    // 2, 14, 28, 40
                    // 1,  3,  5,  7
                    int x = 2 * (7 * col - 6) + 2;
                    int y = 2 * (7 * row - 6) + 2;
                    gc.fillRect(x, y, cellSize, cellSize);
                }
            }
        }
        
        gc.setFill(Color.WHITE);
        int x = 2 + start.getCol() * (cellSize + wallSize);
        int y = 2 + start.getRow() * (cellSize + wallSize);
        gc.fillRect(x, y, cellSize, cellSize);
    
        x = 2 + end.getCol() * (cellSize + wallSize);
        y = 2 + end.getRow() * (cellSize + wallSize);
        gc.fillRect(x, y, cellSize, cellSize);
    }
}
