package imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.Node;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.maze_algorithms.Maze;

import java.util.*;

public class AStar extends Pathfinder
{
    private final HashSet<Node> openList;
    private final HashSet<Node> closedList;
    private final HashMap<Node, Node> cameFrom;
    private OCell[][] cellGrid;
    private Node[][] nodeGrid;
    private Node endNode;
    
    public AStar()
    {
        super();
        openList = new HashSet<>();
        closedList = new HashSet<>();
        cameFrom = new HashMap<>();
    }
    
    @Override
    public void setMaze(Maze maze)
    {
        super.setMaze(maze);
        cellGrid = maze.getGrid();
        nodeGrid = new Node[cellGrid.length][cellGrid[0].length];
        
        convertToNodes();
        Node startNode = nodeGrid[start.getRow()][start.getCol()];
        endNode = nodeGrid[end.getRow()][end.getCol()];
        
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
            var cellCurrent = cellGrid[current.getRow()][current.getCol()];
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
        path.push(cellGrid[current.getRow()][current.getCol()]);
        
        while (cameFrom.containsKey(current))
        {
            current = cameFrom.get(current);
            path.push(cellGrid[current.getRow()][current.getCol()]);
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
            neighbors.add(nodeGrid[row - 1][col]);
        }
        if (!walls[OCell.RIGHT] && col < nodeGrid[0].length - 1)
        {
            neighbors.add(nodeGrid[row][col + 1]);
        }
        if (!walls[OCell.BOTTOM] && row < nodeGrid.length - 1)
        {
            neighbors.add(nodeGrid[row + 1][col]);
        }
        if (!walls[OCell.LEFT] && col > 0)
        {
            neighbors.add(nodeGrid[row][col - 1]);
        }
    
        return neighbors;
    }
    
    /**
     * Goes through the {@link Maze#getGrid() grid} and uses the copy constructor to
     * create new {@link Node PCells}
     */
    private void convertToNodes()
    {
        for (int row = 0; row < nodeGrid.length; ++row)
        {
            for (int col = 0; col < nodeGrid[0].length; ++col)
            {
                OCell cell = cellGrid[row][col];
                nodeGrid[row][col] = new Node(cell);
            }
        }
    }
}
