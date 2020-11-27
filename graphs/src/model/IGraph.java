package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IGraph <T> {

	public ArrayList<T> bfs(T initialNode);
	public ArrayList<T> dfs(T initialNode);
	public Map<T,T> dijkstra(T initialNode);
	public List<T> dijkstra(T initialNode, T finalNode);
	public int[][] floydWarshall(); 
	
}
