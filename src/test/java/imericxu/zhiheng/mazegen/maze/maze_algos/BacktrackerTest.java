package imericxu.zhiheng.mazegen.maze.maze_algos;

import imericxu.zhiheng.mazegen.maze.MazeSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackerTest
{
    private Backtracker maze1;
    private Backtracker maze2;
    
    @BeforeEach
    void setUp()
    {
        maze1 = new Backtracker(MazeSquare.generate(2, 2));
        maze2 = new Backtracker(MazeSquare.generate(5, 5));
    }
    
    @Test
    void constructor()
    {
        var nodes = maze1.nodes;
        var correct = Arrays.asList(nodes[0], nodes[0]);
        assertEquals(correct, maze1.changeList);
        assertEquals(Node.State.EXPLORE, nodes[0].state);
        assertEquals(Node.State.DEFAULT, nodes[1].state);
        
        maze1.changeList.clear();
        maze1.step();
        var node = maze1.changeList.poll();
        assertTrue(node == nodes[1] || node == nodes[2]);
    }
}