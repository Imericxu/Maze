package imericxu.zhiheng.mazegen.maze;

import imericxu.zhiheng.mazegen.maze.maze_algos.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * {@link javafx.scene.canvas.Canvas} specifically designed to display {@link MazeSquare orthogonal} mazes
 */
public class GameCanvas extends Canvas
{
    private final GraphicsContext gc = getGraphicsContext2D();
    private final Cell[] cells;
    private final int rows;
    private final int cols;
    private final double cellSize;
    private final double wallSize;
    
    /**
     * Covered in black until algorithm generates paths
     *
     * @param maze any subtype of {@link MazeSquare}
     */
    public GameCanvas(double sceneWidth, double sceneHeight,
                      int rows, int cols, double cellWallRatio)
    {
        this.rows = rows;
        this.cols = cols;
        
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
        
        this.cells = new Cell[rows * cols];
        for (int row = 0, i = 0; row < rows; ++row)
        {
            for (int col = 0; col < cols; ++col, ++i)
            {
                final double x = (wallSize + cellSize) * (double) col + wallSize;
                final double y = (wallSize + cellSize) * (double) row + wallSize;
                cells[i] = new Cell(x, y);
            }
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
    public void draw(Queue<Node> changeList)
    {
        while (!changeList.isEmpty())
        {
            final Node node = changeList.poll();
            gc.setFill(getColor(node.state));
            final Cell cell = cells[node.id];
            gc.fillRect(cell.x, cell.y, cellSize, cellSize);
            
            for (final Node connection : node.getConnections())
            {
                if (connection.id == node.id - cols)
                    fillRect(cell.top);
                else if (connection.id == node.id + 1)
                    fillRect(cell.right);
                else if (connection.id == node.id + cols)
                    fillRect(cell.bottom);
                else
                    fillRect(cell.left);
            }
        }
    }
    
    public void draw(Node[] nodes)
    {
        Queue<Node> changeList = new LinkedList<>(Arrays.asList(nodes));
        draw(changeList);
    }
    
    private void fillRect(Cell.Rect rect)
    {
        gc.fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    /*
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
    }*/
    
    /* * * * * * * * * * * * * * * * * * * * *
    Helper Methods
    * * * * * * * * * * * * * * * * * * * * */
    
    private Color getColor(Node.State state)
    {
        return switch (state)
                {
                    case DEFAULT -> Colors.DEFAULT.color;
                    case EXPLORE -> Colors.EXPLORE.color;
                    case DONE -> Colors.DONE.color;
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
    
    private class Cell
    {
        public final double x;
        public final double y;
        public final Rect top;
        public final Rect right;
        public final Rect bottom;
        public final Rect left;
        
        Cell(double x, double y)
        {
            this.x = x;
            this.y = y;
            top = new Rect(x, y - wallSize, cellSize, wallSize);
            right = new Rect(x + cellSize, y, wallSize, cellSize);
            bottom = new Rect(x, y + cellSize, cellSize, wallSize);
            left = new Rect(x - wallSize, y, wallSize, cellSize);
        }
        
        class Rect
        {
            public final double x;
            public final double y;
            public final double width;
            public final double height;
            
            public Rect(double x, double y, double width, double height)
            {
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
            }
        }
    }
}
