package model;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.Map.Entry;

public class GraphMatrix<T extends Comparable<T>> implements IGraph<T>{
	Map<T,Integer> positionsVertex;
	Map<Integer,T> positionsIndex;
	private boolean bidirectional;
	private int[][] weightMatrix;
	
	public GraphMatrix(boolean bidirectional,int row,int colum) {
		positionsVertex = new HashMap<>();
		positionsIndex = new HashMap<>();
		this.bidirectional = bidirectional;
		weightMatrix = new int[row][colum];
	}
	
	public void addVertex(T element) {
		int i = positionsVertex.size();
		positionsVertex.put(element, i);
		positionsIndex.put(i,element);
	}
	
	public void addEdge(T source, T destination) {
		if(!positionsVertex.containsKey(source)) {
			addVertex(source);
		}
		if(!positionsVertex.containsKey(destination)) {
			addVertex(destination);
		}
		int i = positionsVertex.get(source);
		int j = positionsVertex.get(destination);
		weightMatrix[i][j] = 1;
		if(bidirectional) {
			weightMatrix[j][i]	= 1;
		}
		
	}
	public void addEdge(T source, T destination,int weight) {
		if(!positionsVertex.containsKey(source)) {
			addVertex(source);
		}
		if(!positionsVertex.containsKey(destination)) {
			addVertex(destination);
		}
		int i = positionsVertex.get(source);
		int j = positionsVertex.get(destination);
		weightMatrix[i][j] = weight;
		if(bidirectional) {
			weightMatrix[j][i]	= weight;
		}
	}
	
	
	@Override
	public ArrayList<T> bfs(T initialNode) {
		Map<T,Boolean> visited = new HashMap<>();
		ArrayList<T> array = new ArrayList<>();
		Queue<T> queue = new LinkedList<>();
		queue.add(initialNode);
		while(!queue.isEmpty()) {
			T current = queue.poll();
			if(!visited.containsKey(current)) {
				visited.put(current, true);
				array.add(current);
				int position = positionsVertex.get(current);
				for (int i = 0; i < weightMatrix[position].length; i++) {
					if(weightMatrix[position][i]!=0	) {
						T aux = positionsIndex.get(i);
						if(!visited.containsKey(aux)) {
							queue.add(aux);
						}
					}
					
				}
			}
		}
		return array;
	}

	@Override
	public ArrayList<T> dfs(T initialNode) {
		Map<T,Boolean> visited = new HashMap<>();
		ArrayList<T> array = new ArrayList<>();
		Stack<T> stack = new Stack<>();
		stack.add(initialNode);
		while(!stack.isEmpty()) {
			T current = stack.pop();
			if(!visited.containsKey(current)) {
				visited.put(current, true);
				array.add(current);
				int position = positionsVertex.get(current);
				for (int i = 0; i < weightMatrix[position].length; i++) {
					if(weightMatrix[position][i]!=0	) {
						T aux = positionsIndex.get(i);
						if(!visited.containsKey(aux)) {
							stack.add(aux);
						}
					}
				}
			}
		}
		return array;
	}

	@Override
	public Map<T, T> dijkstra(T initialNode) {
		int position = positionsVertex.get(initialNode);
		int lenght = weightMatrix[position].length;
		Map<T,T> prev = new HashMap<>();
		Map<Integer,Pair> pairs = new HashMap<>();
		int[] dist = new int[lenght];
		Queue<Pair> queue = new PriorityQueue<>();
		for (int i = 0; i < dist.length; i++) {
			if(i!=position) {
				dist[i] = Integer.MAX_VALUE;
			}
			Pair pair = new Pair(i,dist[i]);
			pairs.put(i, pair);
			queue.add(pair);
			
		}
		
		while(!queue.isEmpty()) {
			Pair current = queue.poll();
			position = current.getElement();
			for (int i = 0; i < weightMatrix[0].length; i++) {
				if(weightMatrix[position][i]!=0) {
					int alt = dist[position] + weightMatrix[position][i];
					if(alt<dist[i]) {
						dist[i] = alt;
						prev.put(positionsIndex.get(i),positionsIndex.get(position));
						queue.remove(pairs.get(i));
						queue.add(pairs.get(i));
					}
				}
			}
		}
		
		return prev;
	}

	@Override
	public List<T> dijkstra(T initialNode, T finalNode) {
		Map<T,T> previous = dijkstra(initialNode);
		boolean pathFounded = false;
		ArrayList<T> path = new ArrayList<>();
		path.add(finalNode);
		
		while(!pathFounded) {
			T currentVertex = previous.get(path.get(path.size()-1));
			path.add(currentVertex);
			
			if(currentVertex.compareTo(initialNode)==0) {
				pathFounded = true;
			}
		}
		
		ArrayList<T> finalPath = new ArrayList<T>();
		
		for(int i=path.size()-1;i>=0;i--) {
			finalPath.add(path.get(i));
		}
		
		return finalPath;
	}

	@Override
	public int[][] floydWarshall() {
		int length = weightMatrix.length;
		int[][] dist = new int[length][length];
		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist[i].length; j++) {
				int value = weightMatrix[i][j];
				if(i==j) {
					dist[i][j] = 0;
				}else {
					dist[i][j] = value!=0?value:Integer.MAX_VALUE;
				}
			}
		}
		for (int k = 0; k <length; k++) {
			for (int i = 0; i < length; i++) {
				for (int j = 0; j < length; j++) {
					if(dist[i][j]> dist[i][k]+ dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
					}
				}
			}
		}
		return dist;
	}
	

	
	
	public void kruskal() {
		//Crear el arbol
		DisjoinSet<T> disjoinset = new DisjoinSet<>(positionsIndex.size());
		for(Entry<T, Integer> entry:positionsVertex.entrySet()) {
			disjoinset.make(entry.getKey());
		}
		Queue<SuperPair> edges = new PriorityQueue<>();
		for (int i = 0; i < weightMatrix.length; i++) {
			for (int j = 0; j < weightMatrix[i].length; j++) {
				SuperPair pair = new SuperPair(positionsIndex.get(i),positionsIndex.get(j),weightMatrix[i][j]);
				edges.add(pair);
			}
		}
		while(!edges.isEmpty()) {
			SuperPair pair = edges.poll();
			T u = pair.element;
			T v = pair.element2;
			disjoinset.union(u, v);
		}
		
	}
	class SuperPair implements Comparable<SuperPair>{
		private T element;
		private T element2;
		private int weight;
		public SuperPair(T element, T element2,int weight) {
			this.element = element;
			this.element2 = element2;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(SuperPair pair) {
			return weight-pair.weight;
		}
		public T getElement() {
			return element;
		}
		public T getElement2() {
			return element2;
		}
		public int getWeight() {
			return weight;
		}
	}
	
	class Pair implements Comparable<Integer>{
		private  Integer element;
		private Integer weight;
		Pair(Integer element, Integer weight){
			this.element = element;
			this.weight = weight;
			
		}
		
		@Override
		public int compareTo(Integer arg0) {
			return arg0-weight;
		}

		public Integer getElement() {
			return element;
		}

		public void setElement(Integer element) {
			this.element = element;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}
		
	}


}
