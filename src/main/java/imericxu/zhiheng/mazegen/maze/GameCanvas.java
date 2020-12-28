package imericxu.zhiheng.mazegen.maze;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

import static imericxu.zhiheng.mazegen.maze.Cell.State;

/**
 * {@link javafx.scene.canvas.Canvas} specifically designed to display {@link Maze orthogonal} mazes
 */
public class GameCanvas extends Canvas
{
    private final Cell[][] grid;
    private final GraphicsContext gc = getGraphicsContext2D();
    private final double cellSize;
    private final double wallSize;
    
    /**
     * Covered in black until algorithm generates paths
     *
     * @param maze any subtype of {@link Maze}
     */
    public GameCanvas(double sceneWidth, double sceneHeight, Maze maze, double cellWallRatio)
    {
        grid = maze.getGrid();
        
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
        gc.setFill(Colors.DEFAULT.color);
        gc.fillRect(0, 0, getWidth(), getHeight());
    }
    
    /**
     * Draws sections of the maze as requests come in
     *
     * @param changeList {@link Cell cells} to draw
     */
    public void drawMaze(Queue<Cell> changeList)
    {
        if (changeList.isEmpty()) return;
        
        double x, y;
        var openCells = new ArrayList<Cell>();
        
        while (!changeList.isEmpty())
        {
            Cell cell = changeList.poll();
            State state = cell.state;
            
            if (state == State.DONE)
            {
                openCells.add(cell);
                continue;
            }
            else if (state == State.DEFAULT)
            {
                x = (wallSize + cellSize) * cell.getCol();
                y = (wallSize + cellSize) * cell.getRow();
                double size = cellSize + 2 * wallSize;
                gc.setFill(Colors.DEFAULT.color);
                gc.fillRect(x, y, size, size);
                continue;
            }
            
            gc.setFill(getColor(state));
            fillCell(cell);
        }
        
        gc.setFill(getColor(State.DONE));
        openCells.forEach(this::fillCell);
    }
    
    /**
     * Draws the entire maze
     */
    public void drawMaze()
    {
        var changeList = new LinkedList<Cell>();
        
        for (Cell[] row : grid)
        {
            changeList.addAll(Arrays.asList(row));
        }
        
        drawMaze(changeList);
    }
    
    public void drawPath(Stack<Cell> pathList)
    {
        if (pathList.isEmpty()) return;
        
        Cell first = pathList.get(0);
        double x1 = (wallSize + cellSize) * first.getCol() + wallSize + cellSize / 2.0;
        double y1 = (wallSize + cellSize) * first.getRow() + wallSize + cellSize / 2.0;
        double x2, y2;
        
        gc.setStroke(Colors.PATH.color);
        gc.setLineWidth(cellSize * 0.5);
        
        for (Cell cell : pathList.subList(1, pathList.size()))
        {
            x2 = (wallSize + cellSize) * cell.getCol() + wallSize + cellSize / 2.0;
            y2 = (wallSize + cellSize) * cell.getRow() + wallSize + cellSize / 2.0;
            gc.strokeLine(x1, y1, x2, y2);
            x1 = x2;
            y1 = y2;
        }
    }
    
    /* * * * * * * * * * * * * * * * * * * * *
    Helper Methods
    * * * * * * * * * * * * * * * * * * * * */
    
    /**
     * Colors the {@code cell} and erases any walls that need to be erased.
     *
     * @param cell the {@link Cell} to be filled
     */
    private void fillCell(Cell cell)
    {
        double x = (wallSize + cellSize) * cell.getCol() + wallSize;
        double y = (wallSize + cellSize) * cell.getRow() + wallSize;
        gc.fillRect(x, y, cellSize, cellSize);
        
        boolean[] walls = cell.getWalls();
        eraseWalls(x, y, walls);
    }
    
    /**
     * If an {@link Cell} doesn't have a wall on a side, fill it with a rectangle
     * the same color as the {@link Cell}
     *
     * @param cellX the x coordinate of the {@link Cell}
     * @param cellY the y coordinate of the {@link Cell}
     * @param walls use the {@link Cell#getWalls()} method
     */
    private void eraseWalls(double cellX, double cellY, boolean[] walls)
    {
        if (!walls[Cell.TOP])
        {
            gc.fillRect(cellX, cellY - wallSize, cellSize, wallSize);
        }
        if (!walls[Cell.RIGHT])
        {
            gc.fillRect(cellX + cellSize, cellY, wallSize, cellSize);
        }
        if (!walls[Cell.BOTTOM])
        {
            gc.fillRect(cellX, cellY + cellSize, cellSize, wallSize);
        }
        if (!walls[Cell.LEFT])
        {
            gc.fillRect(cellX - wallSize, cellY, wallSize, cellSize);
        }
    }
    
    private Color getColor(State state)
    {
        return switch (state)
                {
                    case DEFAULT -> Color.web("0x1C518B");
                    case EXPLORE -> Color.web("0xADD9FF");
                    case DONE -> Color.WHITE;
                };
    }
    
    enum Colors
    {
        DEFAULT(Color.web("0x1C5188")),
        EXPLORE(Color.web("0xADD9FF")),
        DONE(Color.WHITE),
        PATH(Color.web("0xAD360B"));
        
        public Color color;
        
        Colors(Color color)
        {
            this.color = color;
        }
    }
}
