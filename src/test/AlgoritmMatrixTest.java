package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import data_structures.GraphMatrix;

class AlgoritmMatrixTest {

private GraphMatrix<Integer> graph;
	
	public void setUp1() {
		graph = new GraphMatrix<>(false,5);
		graph.addEdge(0,1,7);
		graph.addEdge(0,2,3);
		graph.addEdge(3,0,1);
		graph.addEdge(3,2,4);
		graph.addEdge(1,3,5);
		graph.addEdge(1,5,3);
		graph.addEdge(5,3,1);
		graph.addEdge(5,2,2);
	}
	
	public void setUp2() {
		graph = new GraphMatrix<>(false,5);
		graph.addEdge(0,1,3);
		graph.addEdge(1,2,1);
		graph.addEdge(2,4,7);
		graph.addEdge(2,3,7);
		graph.addEdge(3,4,5);
	}
	
	public void setUp3() {
		graph = new GraphMatrix<>(false,7);
		graph.addEdge(1,2,5);
		graph.addEdge(1,3,11);
		graph.addEdge(1,5,3);
		graph.addEdge(2,4,3);
		graph.addEdge(2,6,3);
		graph.addEdge(3,7,4);
		graph.addEdge(6,5,7);
	}
	
	public void setUp4() {
		graph = new GraphMatrix<>(true,8);
		graph.addEdge(0,1,6);
		graph.addEdge(0,2,6);
		graph.addEdge(1,2,8);
		graph.addEdge(1,3,3);
		graph.addEdge(3,5,2);
		graph.addEdge(0,4,5);
		graph.addEdge(4,6,1);
		graph.addEdge(6,7,3);
	}
	
	public void setUp5() {
		graph = new GraphMatrix<>(true,8);
		graph.addEdge(0,2,8);
		graph.addEdge(2,1,3);
		graph.addEdge(2,5,2);
		graph.addEdge(2,4,9);
		graph.addEdge(1,5,8);
		graph.addEdge(5,6,7);
		graph.addEdge(4,6,5);
		graph.addEdge(5,7,8);
		graph.addEdge(5,7,8);
		graph.addEdge(5,3,6);
		graph.addEdge(3,7,3);
	}
	
	@Test
	void testDFS() {
		setUp3();
		ArrayList<Integer>array = graph.dfs(1);
		assertTrue(array.toString().equals("[1, 5, 3, 7, 2, 6, 4]"));
	}
	
	@Test
	void testBFS() {
		setUp2();
		ArrayList<Integer>array = graph.bfs(0);
		assertTrue(array.toString().equals("[0, 1, 2, 4, 3]"));
	}
	@Test
	void testDijkstra() {
		setUp1();
		ArrayList<Integer>array = graph.dijkstra(1, 3);
		assertTrue(array.toString().equals("[1, 5, 3]"));
	}
	@Test
	void testFloydWarshall() {
		setUp1();
		int[][] matrix = graph.floydWarshall();
		int x = graph.getPositionsVertex().get(1);
		int y = graph.getPositionsVertex().get(3);
		assertTrue(matrix[x][y]==4);
	}
	
	@Test
	void testPrim() {
		setUp5();
		GraphMatrix<Integer> tree = graph.prim();
		assertTrue(tree.bfs(4).toString().equals("[4, 6, 5, 2, 3, 0, 1, 7]"));
	}
	
	@Test
	void testKruskal() {
		setUp5();
		GraphMatrix<Integer> tree = graph.kruskal();
		System.out.println(tree.bfs(4).toString());
		assertTrue(tree.bfs(4).toString().equals("[4, 6, 5, 2, 3, 1, 0, 7]"));
	}
}
