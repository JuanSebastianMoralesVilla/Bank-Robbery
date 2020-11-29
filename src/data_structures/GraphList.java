package data_structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class GraphList<T extends Comparable<T>> implements IGraph<T> {
	
	private Map<T,VertexADJ<T>> map;
	private ArrayList<T> all;
	private boolean bidirectional;
	//hacer que cuando se ingrese un nuevo nodo tambien se le entregue un numero que se guardara en un hashmap
	//hacer lo mismo para la implementacion con matriz de adyacencia
	
	public GraphList(boolean bidirectional) {
		map = new HashMap<>();
		all = new ArrayList<>();
		this.bidirectional = bidirectional;
	}
	
	public void addVertex(T element) {
		map.put(element, new VertexADJ<T>(element));
		all.add(element);
	}
	public void addEdge(T source,T destination) {
		if(!map.containsKey(source)) {
			addVertex(source);
			all.add(source);
		}
		if(!map.containsKey(destination)) {
			addVertex(destination);
			all.add(destination);
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
	public Map<T,T> dijkstra(T initialNode) {
		Map<T,Integer> distances = new HashMap<>();
		Map<T,T> previous = new HashMap<>();
		Map<T,Pair> pairs = new HashMap<>();
		distances.put(initialNode, 0);
		
		PriorityQueue<Pair> pQueue = new PriorityQueue<Pair>(); 
		
		for(Map.Entry<T,VertexADJ<T>> mapElement : map.entrySet()) {
			if( ((Comparable<T>) mapElement.getKey()).compareTo(initialNode)!=0) {
				
				distances.put((T)mapElement.getKey(),Integer.MAX_VALUE);
				T key = mapElement.getKey();
				Pair pair = new Pair(key,distances.get(key));
				pQueue.add(pair);
				pairs.put(key, pair);
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
					Pair currentPair = pairs.get(neighbor);
					distances.remove(neighbor);
					distances.put(neighbor, aux);
					
					previous.remove(neighbor);
					previous.put(neighbor,currentVertex);
					
					pQueue.remove(currentPair);
					currentPair.setWeight(aux);
					pQueue.add(currentPair);
				}
			}
		}
		return previous;
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
		int[][ ] dist = new int[map.size()][map.size()];
		Map<T,Integer> position = new HashMap<>();
		int aux = 0;
		for(Map.Entry<T,VertexADJ<T>> entry:map.entrySet()) {
			T currentElement = entry.getKey();
			position.put(currentElement, aux);
			aux++;
		}
		for(Map.Entry<T,VertexADJ<T>> entry:map.entrySet()) {
			T currentElement = entry.getKey();
			int i = position.get(currentElement);
			VertexADJ<T> vertex = entry.getValue();
			for(Map.Entry<T,Integer> edge : vertex.getEdgesWeight().entrySet()) {
				int j = position.get(edge.getKey());
				int value = edge.getValue();
				if(i==j) {
					dist[i][j] = 0;
				}else {
					dist[i][j] = value!=0?value:Integer.MAX_VALUE;
				}
			}
		}
		int length = position.size();
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
	
	
	@Override
	public GraphMatrix<T> prim( ){
		int NNodes = map.size();
		
		Map<T, Boolean>  reached = new HashMap<>();
		Map<T,T> predNode = new HashMap<>();
		//int infinite = Integer.MAX_VALUE;
		int aux = 0;
		//Map<T,VertexADJ<T>> linkCost = map;
		T first = null;
		for(Entry<T, VertexADJ<T>> entry:map.entrySet()) {
			if(aux==0) {
				first = entry.getKey();
				reached.put(entry.getKey(), true);
				predNode.put(entry.getKey(), entry.getKey());
			}else {
				reached.put(entry.getKey(), false);
			}
			aux++;
		}
		T x,y =null;
		for (int k = 1; k < NNodes; k++){
			x = y = first;
			for(Entry<T, VertexADJ<T>> entry:map.entrySet()) {
				Map<T,Integer> values = entry.getValue().getEdgesWeight();
				for(Entry<T,Integer> edge:values.entrySet()) {
					if(reached.get(entry.getKey()) && !reached.get(edge.getKey())
							&& map.get(x).getEdgesWeight().get(y)!=null && 
							(edge.getValue()< map.get(x).getEdgesWeight().get(y))){
						x = entry.getKey();
						y = edge.getKey();
					}
				}
			}
			predNode.put(y, x);
    	    reached.put(y, true);
		}
		Map<T,T> a = predNode;
		GraphMatrix<T> result = new GraphMatrix<>(bidirectional, NNodes);
		for(Entry<T, VertexADJ<T>> entry:map.entrySet()){
			result.addEdge(a.get(entry.getKey()),entry.getKey());
		}
		return result;
	}
	
	@Override
	public GraphMatrix<T> kruskal() {
		//Crear el arbol
		DisjoinSet<T> disjoinset = new DisjoinSet<>(map.size());
		for(Entry<T, VertexADJ<T>> entry:map.entrySet()) {
			disjoinset.make(entry.getKey());
		}
		Queue<SuperPair> edges = new PriorityQueue<>();
		for(Entry<T, VertexADJ<T>> entry:map.entrySet()) {
			Map<T,Integer> values = entry.getValue().getEdgesWeight();
			for(Entry<T,Integer> edge:values.entrySet()) {
				SuperPair pair = new SuperPair(entry.getKey(),edge.getKey(),edge.getValue());
				edges.add(pair);
			}
		}
		
		while(!edges.isEmpty()) {
			SuperPair pair = edges.poll();
			T u = pair.element;
			T v = pair.element2;
			disjoinset.union(u, v);
		}
		return null;
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
