package model;

import java.util.ArrayList;

public interface IGraph <T> {

	public ArrayList<T> bfs(T initialNode);
	public ArrayList<T> dfs(T initialNode);
	public void dijkstra(T initialNode);
	public void dijkstra(T initialNode, T finalNode);
	
}
