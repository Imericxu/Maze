package imericxu.zhiheng.mazegen.maze;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MazeSquareTest
{
    @Test
    void generate2x2()
    {
        var nodes = MazeSquare.generate(2, 2);
        assertEquals(4, nodes.length);
        assertTrue(nodes[0].getConnections().isEmpty());
        
        var correct = Arrays.asList(nodes[1], nodes[2]);
        assertEquals(correct, nodes[0].getNeighbors());
        
        correct = Arrays.asList(nodes[0], nodes[3]);
        assertTrue(correct.containsAll(nodes[1].getNeighbors()));
    
        correct = Arrays.asList(nodes[0], nodes[3]);
        assertTrue(correct.containsAll(nodes[2].getNeighbors()));
        
        correct = Arrays.asList(nodes[1], nodes[2]);
        assertTrue(correct.containsAll(nodes[3].getNeighbors()));
    }
    
    @Test
    void generate4x4()
    {
        var nodes = MazeSquare.generate(4, 4);
        assertEquals(16, nodes.length);
        
        var correct = Arrays.asList(nodes[0], nodes[2], nodes[5]);
        assertTrue(correct.containsAll(nodes[1].getNeighbors()));
    
        correct = Arrays.asList(nodes[1], nodes[4], nodes[6], nodes[9]);
        assertTrue(correct.containsAll(nodes[5].getNeighbors()));
    }
}