package imericxu.zhiheng.mazegen.maze_types.orthogonal.path_finding;

import imericxu.zhiheng.mazegen.maze_types.Cell;
import imericxu.zhiheng.mazegen.maze_types.PCell;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.MazeOrthogonal;
import imericxu.zhiheng.mazegen.maze_types.orthogonal.OCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class AStarOrthogonal
{
    private final OCell[][] oldGrid;
    private final PCell[][] grid;
    private final HashSet<PCell> openList;
    private final HashSet<PCell> closedList;
    private final HashMap<PCell, PCell> cameFrom;
    private final PCell end;
    private ArrayList<OCell> path;
    
    public AStarOrthogonal(MazeOrthogonal maze)
    {
        oldGrid = maze.getGrid();
        grid = new PCell[oldGrid.length][oldGrid[0].length];
        openList = new HashSet<>();
        closedList = new HashSet<>();
        cameFrom = new HashMap<>();
        
        convertToPCell();
        var tempStart = maze.getStart();
        var tempEnd = maze.getEnd();
        PCell start = grid[tempStart.getRow()][tempStart.getCol()];
        end = grid[tempEnd.getRow()][tempEnd.getCol()];
        
        start.setG(0);
        start.setF(PCell.heuristic(start, end, PCell.Heuristic.MANHATTAN));
        openList.add(start);
    }
    
    public boolean step()
    {
        if (!openList.isEmpty())
        {
            PCell current = Collections.min(openList, (o1, o2) -> (int) ((o1.getF() - o2.getF()) * 10));
            oldGrid[current.getRow()][current.getCol()].setDisplay(Cell.Display.EXPLORE);
            
            path = reconstructPath(cameFrom, current);
            
            if (current == end)
            {
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
                        neighbor.setF(tempG + PCell.heuristic(neighbor, end, PCell.Heuristic.MANHATTAN));
                    }
                    
                    // HashSet automatically checks if it contains neighbor
                    openList.add(neighbor);
                    // oldGrid[neighbor.getRow()][neighbor.getCol()].setDisplay(Cell.Display.EXPLORE);
                }
            }
            
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public ArrayList<OCell> getPath()
    {
        return path;
    }
    
    private ArrayList<OCell> reconstructPath(HashMap<PCell, PCell> cameFrom, PCell current)
    {
        ArrayList<OCell> path = new ArrayList<>();
        path.add(oldGrid[current.getRow()][current.getCol()]);
        
        while (cameFrom.containsKey(current))
        {
            current = cameFrom.get(current);
            path.add(oldGrid[current.getRow()][current.getCol()]);
        }
        
        return path;
    }
    
    private ArrayList<PCell> getNeighbors(OCell current)
    {
        var neighbors = new ArrayList<PCell>();
        var walls = current.getWalls();
        int row = current.getRow();
        int col = current.getCol();
        
        if (!walls[OCell.TOP] && row > 0) neighbors.add(grid[row - 1][col]);
        if (!walls[OCell.RIGHT] && col < grid[0].length - 1) neighbors.add(grid[row][col + 1]);
        if (!walls[OCell.BOTTOM] && row < grid.length - 1) neighbors.add(grid[row + 1][col]);
        if (!walls[OCell.LEFT] && col > 0) neighbors.add(grid[row][col - 1]);
        
        return neighbors;
    }
    
    /**
     * Goes through the {@link MazeOrthogonal#getGrid() grid} and uses the copy constructor to
     * create new {@link PCell PCells}
     */
    private void convertToPCell()
    {
        for (int row = 0; row < grid.length; ++row)
        {
            for (int col = 0; col < grid[0].length; ++col)
            {
                OCell cell = oldGrid[row][col];
                grid[row][col] = new PCell(cell);
            }
        }
    }
}
