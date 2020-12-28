package imericxu.zhiheng.mazegen.maze.maze_algos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest
{
    private Node node1;
    private Node node2;
    private Node node3;
    
    @BeforeEach
    void setUp()
    {
        node1 = new Node(0);
        node2 = new Node(1);
        node3 = new Node(2);
        
        node1.init(Arrays.asList(node2, node3));
        node2.init(Arrays.asList(node1, node3));
        node3.init(Arrays.asList(node1, node2));
    }
    
    @Test
    void connect()
    {
        Node.connect(node1, node2);
        assertTrue(node1.getConnections().contains(node2));
        assertTrue(node2.getConnections().contains(node1));
        assertFalse(node1.getConnections().contains(node3));
    }
    
    @Test
    void disconnect()
    {
        Node.connect(node1, node2);
        Node.disconnect(node1, node2);
        assertFalse(node1.getConnections().contains(node2));
        
        Node.connect(node2, node3);
        Node.disconnect(node2, node3);
        assertFalse(node2.getConnections().contains(node3));
    }
    
    @Test
    void init()
    {
        assertEquals(node1.state, Node.State.DEFAULT);
        
        var correct = Arrays.asList(node2, node3);
        assertEquals(correct, node1.getNeighbors());
        
        correct = Arrays.asList(node1, node3);
        assertEquals(correct, node2.getNeighbors());
        
        correct = Arrays.asList(node1, node2);
        assertEquals(correct, node3.getNeighbors());
    }
    
    @Test
    void getConnections()
    {
        Node.connect(node1, node2);
        Node.connect(node1, node3);
        var correct = Arrays.asList(node2, node3);
        assertEquals(correct, node1.getConnections());
    }
    
    @Test
    void getNeighbors()
    {
        var correct = Arrays.asList(node2, node3);
        assertEquals(correct, node1.getNeighbors());
    }
}