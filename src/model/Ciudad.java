package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;

import listaAdyacencias.Arista;
import listaAdyacencias.Grafo;
import listaAdyacencias.Vertice;

public class Ciudad {
	
	// Atributos -------------------------------------------------------------------------
	
	/**
	 * Representa la estructura que contiene a los sitios de la ciudad.
	 */
	private Grafo<Sitio> sitios;
	
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
		sitios = new Grafo<>(false);
		formatoTexto = "";
		this.identificador = identificador;
		listadoCarreteras = new ArrayList<>();
	}
	
	// Métodos fundamentales --------------------------------------------------------------
	
	/**
	 * Método que se encarga de dar la estructura de sitios de la ciudad.
	 * @return La lista de sitios que hay en la ciudad.
	 */
	public Grafo<Sitio> darSitios(){
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
		Sitio sitio = new Sitio(ID);
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
		Sitio A = buscarSitio(sitioA);
		Sitio B = buscarSitio(sitioB);
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
	private Sitio buscarSitio(int ID){
		Sitio sitio = null;
		boolean encontre = false;
		HashMap<Sitio, Vertice<Sitio>> vertices = sitios.darVertices();
		Iterator<Sitio> iterador = vertices.keySet().iterator();
		while(iterador.hasNext() && !encontre) {
			Sitio actual = iterador.next();
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
		Sitio sitio = buscarSitio(ID);
		sitio.modificarTipo(tipo);
	}
	
	@Override
	public String toString() {
		return "Ciudad "+identificador;
	}
	
	/**
	 * @return
	 */
	public String darSolucion() {
		String respuesta = "";
		long distancias[] = new long[numeroSitios];
		Arrays.fill(distancias, Integer.MAX_VALUE);
		boolean visitados[] = new boolean[numeroSitios];

		Iterator<Vertice<Sitio>> iterador = sitios.darVertices().values().iterator();
		ArrayList<Integer> bancos = new ArrayList<>();
		Queue<Vertice<Sitio>> cola = new ArrayDeque<>();
		boolean agregados[] = new boolean[numeroSitios];

		while (iterador.hasNext()) {
			Vertice<Sitio> vertice = iterador.next();
			char tipoVertice = vertice.darContenido().darTipo();
			int IDVertice = vertice.darContenido().darID();
			if (tipoVertice == Sitio.BANCO) {
				bancos.add(IDVertice);
			}else if (tipoVertice == Sitio.POLICIA) {
				visitados[IDVertice] = true;
				distancias[IDVertice] = 0;
				cola.add(vertice);
				Queue<Sitio> adyacencias = vertice.darAdyacenciasOrdenAscendente();
				HashMap<Sitio, Arista<Sitio>> aristas = vertice.darAristas();
				while (!adyacencias.isEmpty()) {
					Sitio sitio = adyacencias.remove();
					int IDSitio = sitio.darID();
					int peso = aristas.get(sitio).darPeso();
					if (visitados[IDSitio] == false) {
						if (agregados[IDSitio] == false) {
							Vertice<Sitio> v = aristas.get(sitio).darVertice();
							distancias[IDSitio] = peso;
							cola.add(v);
							agregados[IDSitio] = true;
						} else if (distancias[IDSitio] > peso) {
							distancias[IDSitio] = peso;
						}						
					}
				}
			}
		}
		// el famoso dijstrijaa
		while (!cola.isEmpty()) {

			Vertice<Sitio> vertice = cola.remove();
			HashMap<Sitio, Arista<Sitio>> aristas = vertice.darAristas();
			Queue<Sitio> adyacencias = vertice.darAdyacenciasOrdenAscendente();

			while (!adyacencias.isEmpty()) {
				Sitio nodo = adyacencias.remove();
				if ((distancias[nodo.darID()] == Integer.MAX_VALUE && !visitados[nodo.darID()])
						|| distancias[vertice.darContenido().darID()]
								+ aristas.get(nodo).darPeso() < distancias[nodo.darID()]) {
					distancias[nodo.darID()] = distancias[vertice.darContenido().darID()] + aristas.get(nodo).darPeso();
					cola.add(aristas.get(nodo).darVertice());
				}
			}
			visitados[vertice.darContenido().darID()] = true;
		}

		boolean infinito = false;
		long mayor = -1;
		int cont = 0;
		String txt = "";
		for (int i = 0; i < bancos.size() && !infinito; i++) {
			if (distancias[bancos.get(i)] == Integer.MAX_VALUE) {
				cont = 1;
				txt = "" + bancos.get(i) + "";
				for (int j = i + 1; j < bancos.size(); j++) {
					if (distancias[bancos.get(j)] == Integer.MAX_VALUE) {
						cont++;
						
						txt += " " + bancos.get(j);
					}
				}
				infinito = true;
			} else if (distancias[bancos.get(i)] > mayor) {
				mayor = distancias[bancos.get(i)];
				txt = bancos.get(i) + "";
				cont = 1;
			} else if (distancias[bancos.get(i)] == mayor) {
				txt += " " + bancos.get(i);
				cont++;
			}
		}
		pintarBancosSolucion(txt);
		if (!infinito)
			respuesta = (cont + " " + mayor + "\n" + txt);
		else
			respuesta = (cont + " *\n" + txt);
		return respuesta;
	}
	
	private void pintarBancosSolucion(String txt) {
		String[] split = txt.split(" ");
		for (int i = 0; i < split.length; i++) {
			int ID = Integer.parseInt(split[i]);
			Sitio solucion = buscarSitio(ID);
			solucion.cambiarParticipacion(true);
		}
	}
}
