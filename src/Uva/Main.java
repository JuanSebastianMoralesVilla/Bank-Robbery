package Uva;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
	public static void main(String[] arg) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		String str = br.readLine();
		Main principal = new Main();
		while(str!= null && !str.isEmpty()) {
			
			int[] aux = Arrays.stream(str.split(" ")).mapToInt(Integer::parseInt).toArray();
			int n = aux[0];
			int m = aux[1];
			int b = aux[2];
			int p = aux[3];
			GraphList graph = principal.new GraphList(true,n);
			for (int i = 0; i < m; i++) {
				aux = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
				graph.addEdge(aux[0], aux[1], aux[2]);
			}
			int[] banks = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			int[] polices = new int[0];
			if(p!=0) {
				polices = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			}
			boolean[] bankStatus = new boolean[n];
			for (int i = 0; i < b; i++) {
				bankStatus[banks[i]] = true;
			}
			graph.dijkstra(polices);
			int max = -1;
			for (int i = 0; i < graph.dist.length; i++) {
				if (graph.dist[i] > max && bankStatus[i]) {
					max = graph.dist[i];
				}
			}

			ArrayList<Integer> ans = new ArrayList<Integer>();
			for (int i = 0; i < graph.dist.length; i++) {
				if (graph.dist[i] == max && bankStatus[i]) {
					ans.add(i);
				}
			}

			bw.write(ans.size() + " " + (max == Integer.MAX_VALUE ? "* \n" : max + "\n"));
			for (int i = 0; i < ans.size(); i++) {
				bw.write(ans.get(i)+" ");
			}
			bw.write("\n");
			str = br.readLine();
		}
		br.close();
		bw.close();
	}

	class GraphList{
		private Map<Integer,VertexADJ> map;
		private boolean bidirectional;
		private int[] dist;
		public GraphList(boolean bidirectional,int size) {
			map = new HashMap<>();
			this.bidirectional = bidirectional;
			dist = new int[size];
		}
		
		public boolean addVertex(Integer element) {
			if(!map.containsKey(element)) {
				map.put(element, new VertexADJ());
				return true;
			}
			return false;
		}
		
		public void addEdge(Integer source,Integer destination,int weight) {
			if(!map.containsKey(source)) {
				addVertex(source);
			}
			if(!map.containsKey(destination)) {
				addVertex(destination);
			}
			
			map.get(source).edgesWeight.put(destination, weight);
			if(bidirectional) {
				map.get(destination).edgesWeight.put(source, weight);
			}
			
		}
		public void dijkstra(int[] police) {
			dist = new int[dist.length];
			Arrays.fill(dist, Integer.MAX_VALUE);
			Queue<Pair> queue = new PriorityQueue<>(Collections.reverseOrder());
			for (int i = 0; i < police.length; i++) {
				dist[police[i]] = 0;
				Pair pair = new Pair(police[i],dist[police[i]]);
				queue.add(pair);
			}
			
			while(!queue.isEmpty()) {
				Pair current = queue.poll();
				int position = current.element;
				Map<Integer,Integer> edgesWeight = map.get(position).edgesWeight;
				for (Map.Entry<Integer,Integer> entry : edgesWeight.entrySet()) {
					int i = entry.getKey();
					int alt =dist[position]+entry.getValue();
					if(alt<dist[i]) {
						dist[i] = alt;
						queue.add(new Pair(i,dist[i]));
					}
				}
			}
		}
	}
	class VertexADJ {
		private Map<Integer,Integer> edgesWeight;
		public VertexADJ() {
			edgesWeight= new HashMap<>();
		}
	}
	class Pair  implements Comparable<Pair>{
		private Integer element;
		private Integer weight;
		Pair(Integer element, Integer weight){
			this.element = element;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Pair arg0) {
			return arg0.weight-weight;
		}
	}

}
