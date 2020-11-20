package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Graph<T extends Comparable<T>> implements IGraph<T> {
	
	private Map<T,VertexADJ<T>> map;
	private boolean bidirectional;
	
	public Graph(boolean bidirectional) {
		map = new HashMap<>();
		this.bidirectional = bidirectional;
	}
	
	public void addVertex(T element) {
		map.put(element, new VertexADJ<T>(element));
	}
	public void addEdge(T source,T destination) {
		if(!map.containsKey(source)) {
			addVertex(source);
		}
		if(!map.containsKey(destination)) {
			addVertex(destination);
		}
		
		map.get(source).getEdges().add(destination);
		if(bidirectional) {
			map.get(destination).getEdges().add(source);
		}
		
	}
	public void addEdge(T source,T destination,Integer weight) {
		if(!map.containsKey(source)) {
			addVertex(source);
		}
		if(!map.containsKey(destination)) {
			addVertex(destination);
		}
		
		map.get(source).getEdges().add(destination);
		map.get(source).getEdgesWeight().put(destination, weight);
		if(bidirectional) {
			map.get(destination).getEdges().add(source);
			map.get(destination).getEdgesWeight().put(source, weight);
		}
		
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
				VertexADJ<T> cuurentVertex = map.get(current);
				ArrayList<T> edges = cuurentVertex.getEdges();
				for (int i = 0; i < edges.size(); i++) {
					T aux = edges.get(i);
					if(!visited.containsKey(aux)) {
						stack.add(aux);
					}
				}
			}
		}
		return array;
	}
	

	@Override
	public void dijkstra(T initialNode) {
		
	}

	@Override
	public void dijkstra(T initialNode, T finalNode) {
		// TODO Auto-generated method stub
		Map<T,Integer> distances = new HashMap<>();
		Map<T,T> previous = new HashMap<>();
		distances.put(initialNode, 0);
		
		PriorityQueue<Pair> pQueue = new PriorityQueue<Pair>(); 
		
		for(Map.Entry<T,VertexADJ<T>> mapElement : map.entrySet()) {
			if( ((Comparable<T>) mapElement.getKey()).compareTo(initialNode)!=0) {
				
				distances.put((T)mapElement.getKey(),Integer.MAX_VALUE);
				T key = mapElement.getKey();
				Pair pair = new Pair(key,distances.get(key));
				pQueue.add(pair);
			}
		}
		
		while(!pQueue.isEmpty()) {
			T currentVertex = pQueue.poll().getElement();
			ArrayList<T> currentNeighbors = map.get(currentVertex).getEdges();
			HashMap<T,Integer> currentWeights = (HashMap<T,Integer>) map.get(currentVertex).getEdgesWeight();
			
			for(int i=0;i<currentNeighbors.size();i++) {
				T neighbor = currentNeighbors.get(i);
				Integer aux = distances.get(currentVertex) + currentWeights.get(i);
				Integer currentDistance = distances.get(neighbor);

				
				if(aux < currentDistance) {
					distances.remove(neighbor);
					distances.put(neighbor, aux);
					
					previous.remove(neighbor);
					previous.put(neighbor,currentVertex);
					//pQueue.
				}
			}
			//F
		}
		
	}

	@Override
	public ArrayList<T> bfs(T initialNode) {
		Map<T,Boolean> visited = new HashMap<>();
		ArrayList<T> array = new ArrayList<>();
		Queue<T> stack = new LinkedList<>();
		stack.add(initialNode);
		while(!stack.isEmpty()) {
			T current = stack.poll();
			if(!visited.containsKey(current)) {
				visited.put(current, true);
				array.add(current);
				VertexADJ<T> cuurentVertex = map.get(current);
				ArrayList<T> edges = cuurentVertex.getEdges();
				for (int i = 0; i < edges.size(); i++) {
					T aux = edges.get(i);
					if(!visited.containsKey(aux)) {
						stack.add(aux);
					}
				}
			}
		}
		return array;
		
	}
	
	class Pair implements Comparable<Integer>{
		private T element;
		private Integer weight;
		Pair(T element, Integer weight){
			this.element = element;
			this.weight = weight;
			
		}
		
		@Override
		public int compareTo(Integer arg0) {
		
			return arg0-weight;
		}

		public T getElement() {
			return element;
		}

		public void setElement(T element) {
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
