package data_structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IGraph <T extends Comparable<T>> {

	public ArrayList<T> bfs(T initialNode);
	public ArrayList<T> dfs(T initialNode);
	public Map<T,T> dijkstra(T initialNode);
	public List<T> dijkstra(T initialNode, T finalNode);
	public int[][] floydWarshall(); 
	public GraphMatrix<T> kruskal();
	public GraphMatrix<T> prim();
}
