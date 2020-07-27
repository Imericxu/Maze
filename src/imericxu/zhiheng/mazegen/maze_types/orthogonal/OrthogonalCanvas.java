package imericxu.zhiheng.mazegen.maze_types.orthogonal;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class OrthogonalCanvas extends Canvas
{
    private final int cellSize;
    private final int wallSize;
    private final OCell[][] grid;
    private final GraphicsContext gc;
    
    public OrthogonalCanvas(OrthogonalMaze maze)
    {
        // TODO: Calculate canvas width based on rows and cols
        cellSize = 20;
        wallSize = 3;
        grid = maze.getGrid();
        setWidth(grid[0].length * cellSize + (grid[0].length + 1) * wallSize);
        setHeight(grid.length * cellSize + (grid.length + 1) * wallSize);
        gc = getGraphicsContext2D();
        drawGrid();
    }
    
    public void drawMaze()
    {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());
        
        int x, y;
        
        // Draw cells and erase walls
        for (int row = 0; row < grid.length; ++row)
        {
            for (int col = 0; col < grid[row].length; ++col)
            {
                gc.setFill(Color.WHITE);
                
                x = (wallSize + cellSize) * col + wallSize;
                y = (wallSize + cellSize) * row + wallSize;
                gc.fillRect(x, y, cellSize, cellSize);
                
                var walls = grid[row][col].getWalls();
                eraseWalls(x, y, walls);
            }
        }
    }
    
    private void drawGrid()
    {
        int x, y;
        
        // Horizontal walls
        for (int row = 0; row < grid.length; ++row)
        {
            
            for (int col = 0; col < grid[0].length; ++col)
            {
                if (grid[row][col] != null)
                {
                    x = (cellSize + wallSize) * col;
                    y = (cellSize + wallSize) * row;
                    int longSide = cellSize + 2 * wallSize;
                    
                    // Top and left
                    gc.fillRect(x, y, longSide, wallSize);
                    gc.fillRect(x, y, wallSize, longSide);
                    
                    // Right
                    int x1 = x + cellSize + wallSize;
                    gc.fillRect(x1, y, wallSize, longSide);
                    
                    // Bottom
                    int y1 = y + cellSize + wallSize;
                    gc.fillRect(x, y1, longSide, wallSize);
                }
            }
        }
    }
    
    private void eraseWalls(int x, int y, boolean[] walls)
    {
        if (!walls[OCell.TOP])
        {
            gc.fillRect(x, y - wallSize, cellSize, wallSize);
        }
        if (!walls[OCell.RIGHT])
        {
            gc.fillRect(x + cellSize, y, wallSize, cellSize);
        }
        if (!walls[OCell.BOTTOM])
        {
            gc.fillRect(x, y + cellSize, cellSize, wallSize);
        }
        if (!walls[OCell.LEFT])
        {
            gc.fillRect(x - wallSize, y, wallSize, cellSize);
        }
    }
}
