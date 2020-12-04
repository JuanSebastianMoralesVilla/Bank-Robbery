package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import data_structures.GraphList;
import data_structures.Pair;
import data_structures.*;


public class Ciudad {
	
	// Atributos -------------------------------------------------------------------------
	
	/**
	 * Representa la estructura que contiene a los sitios de la ciudad.
	 */
	private Comp1<Point> sitios;
	
	/**
	 * Representa la cantidad de sitios.
	 */
	private int numeroSitios;
	
	/**
	 * Es la representación de la ciudad en forma de texto.
	 */
	private String formatoTexto;
	
	/**
	 * Representa el listado de carreteras de la ciudad.
	 */
	private ArrayList<String> listadoCarreteras;
	
	/**
	 * Representa el número que identifica la ciudad.
	 */
	private int identificador;
	
	// Constructor ------------------------------------------------------------------------
	
	/**
	 * Construye una ciudad sin sitios, con formato vacío y con el identificador
	 * dado.
	 * @param identificador - Representa el número que identifica a la ciudad.
	 */
	public Ciudad(int identificador) {
		sitios = new Comp1<>(false);
		formatoTexto = "";
		this.identificador = identificador;
		listadoCarreteras = new ArrayList<>();
	}
	
	// Métodos fundamentales --------------------------------------------------------------
	
	/**
	 * Método que se encarga de dar la estructura de sitios de la ciudad.
	 * @return La lista de sitios que hay en la ciudad.
	 */
	public Comp1<Point> darSitios(){
		return sitios;
	}
	
	/**
	 * Método que se encarga de dar la descripción de la ciudad en forma de
	 * texto.
	 * @return Una cadena que representa la forma en como está dispuesta la ciudad.
	 */
	public String darFormatoTexto() {
		return formatoTexto;
	}
	
	/**
	 * Método que se encarga de modificar el formato de texto de la ciudad.
	 * @param texto - Es la nueva descripción de la ciudad.
	 */
	public void modificarFormatoTexto(String texto) {
		formatoTexto = texto;
	}
	
	/**
	 * Método que se encarga de dar el número de sitios que hay en la ciudad.
	 * @return Un entero que representa el número de sitios.
	 */
	public int darNumeroSitios() {
		return numeroSitios;
	}
	
	/**
	 * Método que se encarga de dar el identificador de la ciudad.
	 * @return Un entero que representa el id de la ciudad.
	 */
	public int darIdentificador(){
		return identificador;
	}
	
	/**
	 * Método que se encarga de dar el listado de carreteras de la ciudad.
	 * @return Un arraylist que contiene las carreteras de la ciudad.
	 */
	public ArrayList<String> darListadoCarreteras(){
		return listadoCarreteras;
	}
	
	// Métodos y servicios -----------------------------------------------------------------
	
	/**
	 * Método que se encarga de agregar un sitio.
	 * @param ID - Es el número que va a identificar el sitio.
	 */
	public void agregarSitio(int ID){
		Point sitio = new Point(ID);
		sitios.agregarVertice(sitio);
		numeroSitios++;
	}
	
	/**
	 * Método que se encarga de crear una carretera entre los dos sitios indicados con
	 * la distancia indicada. Luego agrega esa carretera al listado para 
	 * poder dibujarlas en la interfaz.
	 * @param sitioA - Es el identificador de uno de los sitios a conectar.
	 * @param sitioB - Es el identificador de otro de los sitos a conectar.
	 * @param distancia - Es el tiempo que tomará de ir del sitio A al sitio B.
	 */
	public void agregarCarretera(int sitioA, int sitioB, int distancia){
		Point A = buscarSitio(sitioA);
		Point B = buscarSitio(sitioB);
		sitios.agregarArista(A, B, distancia);
		String carretera = sitioA+" "+sitioB+" "+distancia;
		listadoCarreteras.add(carretera);
	}
	
	/**
	 * Método que se encarga de buscar el sitio con el ID dado.
	 * @param ID - Es el número que representa el identificador del sitio que se quiere
	 * encontrar.
	 * @return Un sitio que es el representado por el ID dado.
	 */
	private Point buscarSitio(int ID){
		Point sitio = null;
		boolean encontre = false;
		HashMap<Point, Comp2<Point>> vertices = sitios.darVertices();
		Iterator<Point> iterador = vertices.keySet().iterator();
		while(iterador.hasNext() && !encontre) {
			Point actual = iterador.next();
			int IDActual = actual.darID();
			if(IDActual == ID) {
				sitio = actual;
				encontre = true;
			}
		}
		return sitio;
	}
	
	/**
	 * Método que se encarga de cambiar el tipo al sitio cuyo identificador es el
	 * dado por parámetro.
	 * @param ID - Es el identificador del sitio que se quiere cambiar de tipo.
	 * @param tipo - Es el caracter que determina su tipo. Ese caracter puede ser 
	 * B, P ó N.
	 */
	public void modificarTipoSitio(int ID, char tipo) {
		Point sitio = buscarSitio(ID);
		sitio.modificarTipo(tipo);
	}
	
	@Override
	public String toString() {
		return "Ciudad "+identificador;
	}
	
	/**
	 * @return
	 */
//	public String darSolucion() {
//		String respuesta = "";
//		long distancias[] = new long[numeroSitios];
//		Arrays.fill(distancias, Integer.MAX_VALUE);
//		boolean visitados[] = new boolean[numeroSitios];
//
//		Iterator<Comp2<Point>> iterador = sitios.darVertices().values().iterator();
//		ArrayList<Integer> bancos = new ArrayList<>();
//		Queue<Comp2<Point>> cola = new ArrayDeque<>();
//		boolean agregados[] = new boolean[numeroSitios];
//
//		while (iterador.hasNext()) {
//			Comp2<Point> vertice = iterador.next();
//			char tipoVertice = vertice.darContenido().darTipo();
//			int IDVertice = vertice.darContenido().darID();
//			if (tipoVertice == Point.BANCO) {
//				bancos.add(IDVertice);
//			}else if (tipoVertice == Point.POLICIA) {
//				visitados[IDVertice] = true;
//				distancias[IDVertice] = 0;
//				cola.add(vertice);
//				Queue<Point> adyacencias = vertice.darAdyacenciasOrdenAscendente();
//				HashMap<Point, Comp3<Point>> aristas = vertice.darAristas();
//				while (!adyacencias.isEmpty()) {
//					Point sitio = adyacencias.remove();
//					int IDSitio = sitio.darID();
//					int peso = aristas.get(sitio).darPeso();
//					if (visitados[IDSitio] == false) {
//						if (agregados[IDSitio] == false) {
//							Comp2<Point> v = aristas.get(sitio).darVertice();
//							distancias[IDSitio] = peso;
//							cola.add(v);
//							agregados[IDSitio] = true;
//						} else if (distancias[IDSitio] > peso) {
//							distancias[IDSitio] = peso;
//						}						
//					}
//				}
//			}
//		}
//		
//		// el famoso dijstrijaa
//		while (!cola.isEmpty()) {
//
//			Comp2<Point> vertice = cola.remove();
//			HashMap<Point, Comp3<Point>> aristas = vertice.darAristas();
//			Queue<Point> adyacencias = vertice.darAdyacenciasOrdenAscendente();
//
//			while (!adyacencias.isEmpty()) {
//				Point nodo = adyacencias.remove();
//				if ((distancias[nodo.darID()] == Integer.MAX_VALUE && !visitados[nodo.darID()])
//						|| distancias[vertice.darContenido().darID()]
//								+ aristas.get(nodo).darPeso() < distancias[nodo.darID()]) {
//					distancias[nodo.darID()] = distancias[vertice.darContenido().darID()] + aristas.get(nodo).darPeso();
//					cola.add(aristas.get(nodo).darVertice());
//				}
//			}
//			visitados[vertice.darContenido().darID()] = true;
//		}
//
//		boolean infinito = false;
//		long mayor = -1;
//		int cont = 0;
//		String txt = "";
//		for (int i = 0; i < bancos.size() && !infinito; i++) {
//			if (distancias[bancos.get(i)] == Integer.MAX_VALUE) {
//				cont = 1;
//				txt = "" + bancos.get(i) + "";
//				for (int j = i + 1; j < bancos.size(); j++) {
//					if (distancias[bancos.get(j)] == Integer.MAX_VALUE) {
//						cont++;
//						
//						txt += " " + bancos.get(j);
//					}
//				}
//				infinito = true;
//			} else if (distancias[bancos.get(i)] > mayor) {
//				mayor = distancias[bancos.get(i)];
//				txt = bancos.get(i) + "";
//				cont = 1;
//			} else if (distancias[bancos.get(i)] == mayor) {
//				txt += " " + bancos.get(i);
//				cont++;
//			}
//		}
//		pintarBancosSolucion(txt);
//		if (!infinito)
//			respuesta = (cont + " " + mayor + "\n" + txt);
//		else
//			respuesta = (cont + " *\n" + txt);
//		return respuesta;
//	}
	public String darSolucion() {
		String result = "";
		String[] arrayAux = formatoTexto.split("\n");
		int[] aux = Arrays.stream(arrayAux[0].split(" ")).mapToInt(Integer::parseInt).toArray();
		int n = aux[0];
		int m = aux[1];
		int b = aux[2];
		int p = aux[3];
		GraphList<Integer> graph = new GraphList<>(true);
		for (int i = 1; i <= m; i++) {
			aux = Arrays.stream(arrayAux[i].split(" ")).mapToInt(Integer::parseInt).toArray();
			graph.addEdge(aux[0], aux[1], aux[2]);
		}
		int[] banks = Arrays.stream(arrayAux[m+1].split(" ")).mapToInt(Integer::parseInt).toArray();
		for (int i = 0; i < banks.length; i++) {
			graph.addVertex(banks[i]);
		}
		
		Integer[] polices = new Integer[0];
		for (int i = 0; i < polices.length; i++) {
			graph.addVertex(polices[i]);
		}
		if(p!=0) {
			int[] Auxpolices = Arrays.stream(arrayAux[m+2].split(" ")).mapToInt(Integer::parseInt).toArray();
			polices = new Integer[Auxpolices.length];
			for (int i = 0; i < Auxpolices.length; i++) {
				polices[i] = Auxpolices[i];
			}
		}
		boolean[] bankStatus = new boolean[n];
		for (int i = 0; i < b; i++) {
			bankStatus[banks[i]] = true;
		}
		Map<Integer,Integer> dist = graph.dijkstraDistance(polices);
		int max = -1;
		
		for (int i = 0; i < banks.length; i++) {
			if(dist.get(banks[i])!=null && dist.get(banks[i])>max) {
				max = dist.get(banks[i]);
			}
		}
		ArrayList<Integer> ans = new ArrayList<Integer>();
		for (int i = 0; i < banks.length; i++) {
			if(dist.get(banks[i])!=null && dist.get(banks[i])==max) {
				ans.add(banks[i]);
			}
		}

		result+=(ans.size() + " " + (max == Integer.MAX_VALUE ? "* \n" : max + "\n"));
		for (int i = 0; i < ans.size(); i++) {
			result+=(ans.get(i)+" ");
		}
		return result;
	}
	public String darSolucionFloyd(){
		String result = "";
		String[] arrayAux = formatoTexto.split("\n");
		int[] aux = Arrays.stream(arrayAux[0].split(" ")).mapToInt(Integer::parseInt).toArray();
		int n = aux[0];
		int m = aux[1];
		int b = aux[2];
		int p = aux[3];
		GraphList<Integer> graph = new GraphList<>(true);
		for (int i = 1; i <= m; i++) {
			aux = Arrays.stream(arrayAux[i].split(" ")).mapToInt(Integer::parseInt).toArray();
			graph.addEdge(aux[0], aux[1], aux[2]);
		}
		int[] banks = Arrays.stream(arrayAux[m+1].split(" ")).mapToInt(Integer::parseInt).toArray();
		for (int i = 0; i < banks.length; i++) {
			graph.addVertex(banks[i]);
		}
		Integer[] polices = new Integer[0];
		for (int i = 0; i < polices.length; i++) {
			graph.addVertex(polices[i]);
		}
		if(p!=0) {
			int[] Auxpolices = Arrays.stream(arrayAux[m+2].split(" ")).mapToInt(Integer::parseInt).toArray();
			polices = new Integer[Auxpolices.length];
			for (int i = 0; i < Auxpolices.length; i++) {
				polices[i] = Auxpolices[i];
			}
		}
		boolean[] bankStatus = new boolean[n];
		for (int i = 0; i < b; i++) {
			bankStatus[banks[i]] = true;
		}
		int[][] dist = graph.floydWarshall();
		Map<Integer,Integer> positions = graph.getPosition();
		Queue<Pair<Integer>> queue = new PriorityQueue<>();
		int max = -1;
		for (int i = 0; i < banks.length; i++) {
			int min = Integer.MAX_VALUE;
			int bank = positions.get(banks[i]);
			for (int j = 0; j < polices.length; j++) {
				int police = positions.get(polices[i]);
				if(dist[police][bank]<min) {
					min =dist[police][bank];
				}
			}
			if(min>max) {
				max = min;
			}
			Pair<Integer> pair = new Pair<>(bank,min);
			queue.add(pair);
		}
		ArrayList<Integer> ans = new ArrayList<>();
		while(!queue.isEmpty()) {
			Pair<Integer> pair = queue.poll();
			if(pair.getWeight().equals(max)) {
				ans.add(pair.getElement());
			}else {
				break;
			}
		}
		result+=(ans.size() + " " + (max == Integer.MAX_VALUE ? "* \n" : max + "\n"));
		for (int i = 0; i < ans.size(); i++) {
			result+=(ans.get(i)+" ");
		}
		return result;
	}

}
