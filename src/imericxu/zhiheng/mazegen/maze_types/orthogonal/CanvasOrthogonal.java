package imericxu.zhiheng.mazegen.maze_types.orthogonal;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static imericxu.zhiheng.mazegen.maze_types.Cell.Display;

/**
 * {@link Canvas} specifically designed to display {@link MazeOrthogonal orthogonal} mazes
 */
public class CanvasOrthogonal extends Canvas
{
    private final int cellSize;
    private final int wallSize;
    private final OCell[][] grid;
    private final GraphicsContext gc;
    
    /**
     * Covered in black until algorithm generates paths
     *
     * @param maze any subtype of {@link MazeOrthogonal}
     */
    public CanvasOrthogonal(MazeOrthogonal maze)
    {
        cellSize = 18;
        wallSize = 6;
        grid = maze.getGrid();
        gc = getGraphicsContext2D();
        
        int rows = grid.length;
        int cols = grid[0].length;
        setWidth(cols * (cellSize + wallSize) + wallSize);
        setHeight(rows * (cellSize + wallSize) + wallSize);
        // drawGrid();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());
    }
    
    /**
     * Colors cells if they're found (based on their {@link Display Display} property)
     */
    public void drawMaze()
    {
        int x, y;
        
        // Draw cells and erase walls
        for (int row = 0; row < grid.length; ++row)
        {
            for (int col = 0; col < grid[row].length; ++col)
            {
                switch (grid[row][col].getDisplay())
                {
                case SHOW -> gc.setFill(Color.WHITE);
                case COLOR_1 -> gc.setFill(Color.PINK);
                case HIDE -> {
                    continue;
                }
                }
                
                x = (wallSize + cellSize) * col + wallSize;
                y = (wallSize + cellSize) * row + wallSize;
                gc.fillRect(x, y, cellSize, cellSize);
                
                var walls = grid[row][col].getWalls();
                eraseWalls(x, y, walls);
            }
        }
    }
    
    // /**
    //  * Currently unused method that draws the actual grid
    //  */
    //
    // private void drawGrid()
    // {
    //     int x, y;
    //
    //     // Horizontal walls
    //     for (int row = 0; row < grid.length; ++row)
    //     {
    //
    //         for (int col = 0; col < grid[0].length; ++col)
    //         {
    //             if (grid[row][col] != null)
    //             {
    //                 x = (cellSize + wallSize) * col;
    //                 y = (cellSize + wallSize) * row;
    //                 int longSide = cellSize + 2 * wallSize;
    //
    //                 // Top and left
    //                 gc.fillRect(x, y, longSide, wallSize);
    //                 gc.fillRect(x, y, wallSize, longSide);
    //
    //                 // Right
    //                 int x1 = x + cellSize + wallSize;
    //                 gc.fillRect(x1, y, wallSize, longSide);
    //
    //                 // Bottom
    //                 int y1 = y + cellSize + wallSize;
    //                 gc.fillRect(x, y1, longSide, wallSize);
    //             }
    //         }
    //     }
    // }
    
    /* * * * * * * * * * * * * * * * * * * * *
    Helper Methods
    * * * * * * * * * * * * * * * * * * * * */
    
    /**
     * If an {@link OCell} doesn't have a wall on a side, fill it with a rectangle
     * the same color as the {@link OCell}
     *
     * @param x     the x coordinate of the {@link OCell}
     * @param y     the y coordinate of the {@link OCell}
     * @param walls use the {@link OCell#getWalls()} method
     */
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
