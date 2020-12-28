//package imericxu.zhiheng.mazegen.maze.solve_algos;
//
//import imericxu.zhiheng.mazegen.maze.Cell;
//import imericxu.zhiheng.mazegen.maze.MazeSquare;
//import imericxu.zhiheng.mazegen.maze.Pathfinder;
//
//import java.util.*;
//
//public class AStar extends Pathfinder
//{
//    private final HashSet<Node> openList;
//    private final HashSet<Node> closedList;
//    private final HashMap<Node, Node> cameFrom;
//    private Cell[][] cellGrid;
//    private Node[][] nodeGrid;
//    private Node endNode;
//
//    public AStar()
//    {
//        super();
//        openList = new HashSet<>();
//        closedList = new HashSet<>();
//        cameFrom = new HashMap<>();
//    }
//
//    @Override
//    public void setMaze(MazeSquare maze)
//    {
//        super.setMaze(maze);
//        cellGrid = maze.getGrid();
//        nodeGrid = new Node[cellGrid.length][cellGrid[0].length];
//
//        convertToNodes();
//        Node startNode = nodeGrid[start.getRow()][start.getCol()];
//        endNode = nodeGrid[end.getRow()][end.getCol()];
//
//        startNode.setG(0);
//        startNode.setF(Node.heuristic(startNode, endNode, Node.Heuristic.MANHATTAN));
//        openList.add(startNode);
//    }
//
//    @Override
//    public boolean step()
//    {
//        if (!openList.isEmpty())
//        {
//            Node current = Collections.min(openList, (o1, o2) -> (int) ((o1.getF() - o2.getF()) * 10));
//            var cellCurrent = cellGrid[current.getRow()][current.getCol()];
//            cellCurrent.setState(Cell.State.EXPLORE);
//            changeList.push(cellCurrent);
//
//            if (current == endNode)
//            {
//                path = reconstructPath(cameFrom, current);
//                return true;
//            }
//
//            openList.remove(current);
//            closedList.add(current);
//
//            var neighbors = getNeighbors(current);
//            for (var neighbor : neighbors)
//            {
//                if (!closedList.contains(neighbor))
//                {
//                    double tempG = current.getG() + 1;
//                    if (tempG < neighbor.getG())
//                    {
//                        cameFrom.put(neighbor, current);
//                        neighbor.setG(tempG);
//                        neighbor.setF(tempG + Node.heuristic(neighbor, endNode, Node.Heuristic.MANHATTAN));
//                    }
//
//                    // HashSet automatically checks if it contains neighbor
//                    openList.add(neighbor);
//                }
//            }
//
//            return false;
//        }
//        else
//        {
//            System.out.println("No path found");
//            return true;
//        }
//    }
//
//    private Stack<Cell> reconstructPath(HashMap<Node, Node> cameFrom, Node current)
//    {
//        var path = new Stack<Cell>();
//        path.push(cellGrid[current.getRow()][current.getCol()]);
//
//        while (cameFrom.containsKey(current))
//        {
//            current = cameFrom.get(current);
//            path.push(cellGrid[current.getRow()][current.getCol()]);
//        }
//
//        return path;
//    }
//
//    private ArrayList<Node> getNeighbors(Cell current)
//    {
//        var neighbors = new ArrayList<Node>();
//        var walls = current.getWalls();
//        int row = current.getRow();
//        int col = current.getCol();
//
//        if (!walls[Cell.TOP] && row > 0)
//        {
//            neighbors.add(nodeGrid[row - 1][col]);
//        }
//        if (!walls[Cell.RIGHT] && col < nodeGrid[0].length - 1)
//        {
//            neighbors.add(nodeGrid[row][col + 1]);
//        }
//        if (!walls[Cell.BOTTOM] && row < nodeGrid.length - 1)
//        {
//            neighbors.add(nodeGrid[row + 1][col]);
//        }
//        if (!walls[Cell.LEFT] && col > 0)
//        {
//            neighbors.add(nodeGrid[row][col - 1]);
//        }
//
//        return neighbors;
//    }
//
//    /**
//     * Goes through the {@link MazeSquare#getGrid() grid} and uses the copy constructor to
//     * create new {@link Node PCells}
//     */
//    private void convertToNodes()
//    {
//        for (int row = 0; row < nodeGrid.length; ++row)
//        {
//            for (int col = 0; col < nodeGrid[0].length; ++col)
//            {
//                Cell cell = cellGrid[row][col];
//                nodeGrid[row][col] = new Node(cell);
//            }
//        }
//    }
//
//    /**
//     * Path-finding {@link Cell} specialized for the algorithms
//     */
//    private static class Node extends Cell
//    {
//        /**
//         * Total estimated cost from beginning to end
//         */
//        private double f;
//        /**
//         * Cost from beginning to current node
//         */
//        private double g;
//
//        /**
//         * {@link #f} and {@link #g} default to infinity
//         */
//        public Node(Cell cell)
//        {
//            super(cell);
//            f = Double.POSITIVE_INFINITY;
//            g = Double.POSITIVE_INFINITY;
//        }
//
//        public static double heuristic(Cell c1, Cell c2, Heuristic h)
//        {
//            int row1 = c1.getRow();
//            int col1 = c1.getCol();
//            int row2 = c2.getRow();
//            int col2 = c2.getCol();
//
//            return switch (h)
//                    {
//                        case MANHATTAN -> Math.abs(row2 - row1) + Math.abs(col2 - col1);
//                        case EUCLIDEAN -> Math.hypot(row2 - row1, col2 - col1);
//                    };
//        }
//
//        public double getF()
//        {
//            return f;
//        }
//
//        public void setF(double f)
//        {
//            this.f = f;
//        }
//
//        public double getG()
//        {
//            return g;
//        }
//
//        public void setG(double g)
//        {
//            this.g = g;
//        }
//
//        public enum Heuristic
//        {
//            MANHATTAN, EUCLIDEAN
//        }
//    }
//}
