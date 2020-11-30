package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import data_structures.GraphList;

class GraphListTest {
	
	private GraphList<Integer> graph;
	
	public void setUp1(boolean bi) {
		graph = new GraphList<>(bi);
	}
	
	public void setUp2() {
		setUp1(false);
		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(1, 2);
		graph.addEdge(2, 3);
		graph.addEdge(3, 4);
		graph.addEdge(4, 1);
	}
	
	public void setUp3() {
		setUp1(false);
		graph.addEdge(3, 1);
	}
	public void setUp4() {
		setUp1(false);
		graph.addEdge(0, 1, 1);
		graph.addEdge(1, 2, 1);
		graph.addEdge(4, 1, 1);
		graph.addEdge(1, 3, 1);
		graph.addEdge(3, 4, 1);
	}
	
	public void setUp5() {
		setUp1(false);
		graph.addEdge(0, 1);
		graph.addEdge(1, 2);
		graph.addEdge(2, 5);
		graph.addEdge(3, 0);
		graph.addEdge(5, 3);
		graph.addEdge(3, 4);
		graph.addEdge(5, 4);
	}
	
	@Test
	void testAdd1() {
		setUp1(false);
		assertTrue(!graph.containsVertex(1));
		graph.addVertex(1);
		assertTrue(graph.containsVertex(1));
	}
	@Test
	void testAdd2() {
		setUp2();
		assertTrue(!graph.containsVertex(5));
		graph.addVertex(5);
		assertTrue(graph.containsVertex(5));
	}
	
	@Test
	void testAdd3() {
		setUp2();
		assertTrue(graph.containsVertex(2));
		assertTrue(!graph.addVertex(2));
	}
	
	@Test
	void testAdd4() {
		setUp1(true);
		graph.addEdge(3, 1);
		assertTrue(graph.containsVertex(3) && graph.containsVertex(1));
		assertTrue(graph.getMap().get(3).getEdges().get(0)==1);
		assertTrue(graph.getMap().get(1).getEdges().get(0)==3);
	}
	@Test
	void testContainsVertex() {
		setUp2();
		assertTrue(graph.containsVertex(1));
		assertTrue(!graph.containsVertex(10));
	}
	@Test
	void testDelateVertex1() {
		setUp4();
		assertTrue(graph.containsVertex(2));
		assertTrue(graph.delateVertex(2));
		assertTrue(!graph.containsVertex(2));
	}
	
	@Test
	void testDelateVertex2() {
		setUp2();
		assertTrue(!graph.delateVertex(48));
		assertTrue(graph.containsVertex(1) && graph.containsVertex(3));
		assertTrue(graph.delateVertex(1) && graph.delateVertex(3));
		assertTrue(!graph.containsVertex(1) && !graph.containsVertex(3));
	}
	
}
