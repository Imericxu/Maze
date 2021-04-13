//package imericxu.zhiheng.mazegen.maze.maze_algos;
//
//import imericxu.zhiheng.mazegen.maze.MazeSquare;
//import imericxu.zhiheng.mazegen.maze.Node;
//import imericxu.zhiheng.mazegen.maze.State;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class BacktrackerTest
//{
//    private Backtracker maze1;
//    private Backtracker maze2;
//
//    @BeforeEach
//    void setUp()
//    {
//        maze1 = new Backtracker(MazeSquare.generate(2, 2));
//        maze2 = new Backtracker(MazeSquare.generate(5, 5));
//    }
//
//    @Test
//    void constructor()
//    {
//        var nodes = maze1.nodes;
//        var correct = Collections.singletonList(nodes[0]);
//        assertEquals(correct, maze1.changeList);
//        assertEquals(State.EXPLORE, nodes[0].state);
//        assertEquals(State.DEFAULT, nodes[1].state);
//
//        maze1.changeList.clear();
//        maze1.loopOnce();
//        var node = maze1.changeList.poll();
//        assertTrue(node == nodes[1] || node == nodes[2]);
//    }
//}
