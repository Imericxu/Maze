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
        cellSize = 20;
        wallSize = 5;
        this.maze = maze;
        this.grid = maze.getGrid();
        setWidth(1000);
        setHeight(800);
        gc = getGraphicsContext2D();
        start = maze.getStart();
        end = maze.getEnd();
    }
    
    public void drawMaze()
    {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, getWidth(), getHeight());
        
        int coWall, coCell, x, y, width, height;
        
        for (int row = 0; row < grid.length; ++row)
        {
            for (int col = 0; col < grid[row].length; ++col)
            {
                switch (grid[row][col].getState())
                {
                case SEEN, START, END -> gc.setFill(Color.WHITE);
                case FRONTIER -> gc.setFill(Color.PINK);
                default -> {
                    continue;
                }
                }
                
                coWall = (int) Math.ceil(col / 2.0);
                coCell = col - coWall;
                x = wallSize * coWall + cellSize * coCell;
                
                coWall = (int) Math.ceil(row / 2.0);
                coCell = row - coWall;
                y = wallSize * coWall + cellSize * coCell;
                
                width = col % 2 == 0 ? wallSize : cellSize;
                height = row % 2 == 0 ? wallSize : cellSize;
                
                gc.fillRect(x, y, width, height);
            }
        }
    }
}
