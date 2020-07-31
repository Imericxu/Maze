package imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.Node;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Maze;

import java.util.*;

public class AStar extends Pathfinder
{
    private final OCell[][] oldGrid;
    private final Node[][] grid;
    private final HashSet<Node> openList;
    private final HashSet<Node> closedList;
    private final HashMap<Node, Node> cameFrom;
    private Node endNode;
    
    public AStar(Maze maze)
    {
        super(maze);
        oldGrid = maze.getGrid();
        grid = new Node[oldGrid.length][oldGrid[0].length];
        openList = new HashSet<>();
        closedList = new HashSet<>();
        cameFrom = new HashMap<>();
    }
    
    @Override
    public void init()
    {
        convertToNodes();
        Node startNode = grid[start.getRow()][start.getCol()];
        endNode = grid[end.getRow()][end.getCol()];
        
        startNode.setG(0);
        startNode.setF(Node.heuristic(startNode, endNode, Node.Heuristic.EUCLIDEAN));
        openList.add(startNode);
    }
    
    @Override
    public boolean step()
    {
        if (!openList.isEmpty())
        {
            Node current = Collections.min(openList, (o1, o2) -> (int) ((o1.getF() - o2.getF()) * 10));
            var cellCurrent = oldGrid[current.getRow()][current.getCol()];
            cellCurrent.setDisplay(Cell.Display.EXPLORE);
            changeList.push(cellCurrent);
            
            if (current == endNode)
            {
                path = reconstructPath(cameFrom, current);
                return false;
            }
            
            openList.remove(current);
            closedList.add(current);
            
            var neighbors = getNeighbors(current);
            for (var neighbor : neighbors)
            {
                if (!closedList.contains(neighbor))
                {
                    double tempG = current.getG() + 1;
                    if (tempG < neighbor.getG())
                    {
                        cameFrom.put(neighbor, current);
                        neighbor.setG(tempG);
                        neighbor.setF(tempG + Node.heuristic(neighbor, endNode, Node.Heuristic.EUCLIDEAN));
                    }
                    
                    // HashSet automatically checks if it contains neighbor
                    openList.add(neighbor);
                }
            }
            
            return true;
        }
        else
        {
            System.out.println("No path found");
            return false;
        }
    }
    
    private Stack<OCell> reconstructPath(HashMap<Node, Node> cameFrom, Node current)
    {
        var path = new Stack<OCell>();
        path.push(oldGrid[current.getRow()][current.getCol()]);
        
        while (cameFrom.containsKey(current))
        {
            current = cameFrom.get(current);
            path.push(oldGrid[current.getRow()][current.getCol()]);
        }
        
        return path;
    }
    
    private ArrayList<Node> getNeighbors(OCell current)
    {
        var neighbors = new ArrayList<Node>();
        var walls = current.getWalls();
        int row = current.getRow();
        int col = current.getCol();
    
        if (!walls[OCell.TOP] && row > 0)
        {
            neighbors.add(grid[row - 1][col]);
        }
        if (!walls[OCell.RIGHT] && col < grid[0].length - 1)
        {
            neighbors.add(grid[row][col + 1]);
        }
        if (!walls[OCell.BOTTOM] && row < grid.length - 1)
        {
            neighbors.add(grid[row + 1][col]);
        }
        if (!walls[OCell.LEFT] && col > 0)
        {
            neighbors.add(grid[row][col - 1]);
        }
    
        return neighbors;
    }
    
    /**
     * Goes through the {@link Maze#getGrid() grid} and uses the copy constructor to
     * create new {@link Node PCells}
     */
    private void convertToNodes()
    {
        for (int row = 0; row < grid.length; ++row)
        {
            for (int col = 0; col < grid[0].length; ++col)
            {
                OCell cell = oldGrid[row][col];
                grid[row][col] = new Node(cell);
            }
        }
    }
}
