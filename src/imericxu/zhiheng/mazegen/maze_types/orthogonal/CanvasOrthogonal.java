package imericxu.zhiheng.mazegen.maze_types.orthogonal;

import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Maze;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static imericxu.zhiheng.mazegen.maze_types.Cell.Display;

/**
 * {@link Canvas} specifically designed to display {@link Maze orthogonal} mazes
 */
public class CanvasOrthogonal extends Canvas
{
    private final OCell[][] grid;
    private final GraphicsContext gc;
    private final double cellSize;
    private final double wallSize;
    
    /**
     * Covered in black until algorithm generates paths
     *
     * @param maze any subtype of {@link Maze}
     */
    public CanvasOrthogonal(double sceneWidth, double sceneHeight, Maze maze, double cellWallRatio)
    {
        grid = maze.getGrid();
        gc = getGraphicsContext2D();
        
        int rows = grid.length;
        int cols = grid[0].length;
        
        double screenRatio = sceneWidth / sceneHeight;
        double gridRatio = (double) cols / rows;
        if (screenRatio > gridRatio)
        {
            setHeight(sceneHeight);
            wallSize = sceneHeight / (rows * (cellWallRatio + 1) + 1);
            cellSize = wallSize * cellWallRatio;
            setWidth(cols * (cellSize + wallSize) + wallSize);
        }
        else
        {
            setWidth(sceneWidth);
            wallSize = sceneWidth / (cols * (cellWallRatio + 1) + 1);
            cellSize = wallSize * cellWallRatio;
            setHeight(rows * (cellSize + wallSize) + wallSize);
        }
    
        // drawGrid();
        gc.setFill(Display.HIDE.getColor());
        gc.fillRect(0, 0, getWidth(), getHeight());
    }
    
    /**
     * Draws sections of the maze as requests come in
     *
     * @param changeList {@link OCell cells} to draw
     */
    public void drawMaze(Stack<OCell> changeList)
    {
        if (changeList.isEmpty()) return;
        
        double x, y;
        var openCells = new ArrayList<OCell>();
        
        while (!changeList.isEmpty())
        {
            var cell = changeList.pop();
            
            if (cell.getDisplay() == Display.SHOW)
            {
                openCells.add(cell);
                continue;
            }
            
            gc.setFill(cell.getDisplay().getColor());
            fillCell(cell);
        }
        
        gc.setFill(Display.SHOW.getColor());
        openCells.forEach(this::fillCell);
    }
    
    /**
     * Draws the entire maze
     */
    public void drawMaze()
    {
        var changeList = new Stack<OCell>();
    
        for (var row : grid)
        {
            changeList.addAll(Arrays.asList(row));
        }
    
        drawMaze(changeList);
    }
    
    public void drawPath(Stack<OCell> pathList)
    {
        if (pathList.isEmpty()) return;
    
        var first = pathList.get(0);
        double x1 = (wallSize + cellSize) * first.getCol() + wallSize + cellSize / 2.0;
        double y1 = (wallSize + cellSize) * first.getRow() + wallSize + cellSize / 2.0;
        double x2, y2;
    
        gc.setStroke(Display.PATH.getColor());
        gc.setLineWidth(cellSize * 0.5);
    
        for (var cell : pathList.subList(1, pathList.size()))
        {
            x2 = (wallSize + cellSize) * cell.getCol() + wallSize + cellSize / 2.0;
            y2 = (wallSize + cellSize) * cell.getRow() + wallSize + cellSize / 2.0;
            gc.strokeLine(x1, y1, x2, y2);
            x1 = x2;
            y1 = y2;
        }
    }
    
    /**
     * Currently unused method that draws the actual grid
     */
    private void drawGrid()
    {
        double x, y;
        
        // Horizontal walls
        for (int row = 0; row < grid.length; ++row)
        {
            
            for (int col = 0; col < grid[0].length; ++col)
            {
                if (grid[row][col] != null)
                {
                    x = (cellSize + wallSize) * col;
                    y = (cellSize + wallSize) * row;
                    double longSide = cellSize + 2 * wallSize;
                    
                    // Top and left
                    gc.fillRect(x, y, longSide, wallSize);
                    gc.fillRect(x, y, wallSize, longSide);
                    
                    // Right
                    double x1 = x + cellSize + wallSize;
                    gc.fillRect(x1, y, wallSize, longSide);
                    
                    // Bottom
                    double y1 = y + cellSize + wallSize;
                    gc.fillRect(x, y1, longSide, wallSize);
                }
            }
        }
    }
    
    /* * * * * * * * * * * * * * * * * * * * *
    Helper Methods
    * * * * * * * * * * * * * * * * * * * * */
    
    /**
     * Colors the {@code cell} and erases any walls that need to be erased.
     *
     * @param cell the {@link OCell} to be filled
     */
    private void fillCell(OCell cell)
    {
        double x = (wallSize + cellSize) * cell.getCol() + wallSize;
        double y = (wallSize + cellSize) * cell.getRow() + wallSize;
        gc.fillRect(x, y, cellSize, cellSize);
        
        var walls = cell.getWalls();
        eraseWalls(x, y, walls);
    }
    
    /**
     * If an {@link OCell} doesn't have a wall on a side, fill it with a rectangle
     * the same color as the {@link OCell}
     *
     * @param cellX the x coordinate of the {@link OCell}
     * @param cellY the y coordinate of the {@link OCell}
     * @param walls use the {@link OCell#getWalls()} method
     */
    private void eraseWalls(double cellX, double cellY, boolean[] walls)
    {
        if (!walls[OCell.TOP])
        {
            gc.fillRect(cellX, cellY - wallSize, cellSize, wallSize);
        }
        if (!walls[OCell.RIGHT])
        {
            gc.fillRect(cellX + cellSize, cellY, wallSize, cellSize);
        }
        if (!walls[OCell.BOTTOM])
        {
            gc.fillRect(cellX, cellY + cellSize, cellSize, wallSize);
        }
        if (!walls[OCell.LEFT])
        {
            gc.fillRect(cellX - wallSize, cellY, wallSize, cellSize);
        }
    }
}
